package de.wi2020sebgroup1.cinema.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.wi2020sebgroup1.cinema.configurationObject.BookingConfigurationObject;
import de.wi2020sebgroup1.cinema.entities.Booking;
import de.wi2020sebgroup1.cinema.entities.Seat;
import de.wi2020sebgroup1.cinema.entities.Show;
import de.wi2020sebgroup1.cinema.entities.Snack;
import de.wi2020sebgroup1.cinema.entities.Ticket;
import de.wi2020sebgroup1.cinema.entities.User;
import de.wi2020sebgroup1.cinema.enums.BookingState;
import de.wi2020sebgroup1.cinema.enums.TicketState;
import de.wi2020sebgroup1.cinema.exceptions.SeatNotFoundException;
import de.wi2020sebgroup1.cinema.exceptions.SnackNotFoundException;
import de.wi2020sebgroup1.cinema.repositories.BookingRepositroy;
import de.wi2020sebgroup1.cinema.repositories.SeatRepository;
import de.wi2020sebgroup1.cinema.repositories.ShowRepository;
import de.wi2020sebgroup1.cinema.repositories.SnackRepository;
import de.wi2020sebgroup1.cinema.repositories.TicketRepository;
import de.wi2020sebgroup1.cinema.repositories.UserRepository;
import de.wi2020sebgroup1.cinema.services.QRCodeGenerator;
import de.wi2020sebgroup1.cinema.services.SeatService;

@Controller
@RestController
@RequestMapping("/booking")
public class BookingController {
	
	@Autowired
	BookingRepositroy bookingRepositroy;
	
	@Autowired
	TicketRepository ticketRepository;
	
	@Autowired
	UserRepository userRepositroy;
	
	@Autowired
	ShowRepository showRepository;
	
	@Autowired
	SeatRepository seatRepository;
	
	@Autowired
	SnackRepository snackRepository;
	
	@Autowired
	SeatService seatService;
	
	@Autowired
	QRCodeGenerator qrCodeGenerator;
	
	@SuppressWarnings("static-access")
	@PutMapping("/add")
	@Transactional
	public ResponseEntity<Object> addBooking(@RequestBody BookingConfigurationObject bookingObject){

		ArrayList<Ticket> tickets = new ArrayList<>();
		ArrayList<Snack> snacks = new ArrayList<>();
		
		ArrayList<UUID> seatIDs = bookingObject.seatIDs;
		ArrayList<UUID> snackIDs = bookingObject.snackIDs;
		
		Show show = showRepository.findById(bookingObject.showID).get();
		if(seatService.reserveSeats(seatIDs, bookingObject.showID)) {
			try {
				User user = userRepositroy.findById(bookingObject.userID).get();
				
				for(UUID seat : seatIDs) {
					try {
						Seat seatObject = seatRepository.findById(seat).get();
						Ticket ticket = new Ticket(TicketState.RESERVED,user,show,null,seatObject);
						tickets.add(ticket);
					} catch(NoSuchElementException e) {
						return new ResponseEntity<Object>(new SeatNotFoundException(seat).getMessage(),HttpStatus.NOT_FOUND);
					}
				}
				
				for(UUID snack : snackIDs) {
					try {
						Snack snackObject = snackRepository.findById(snack).get();
						snacks.add(snackObject);
					} catch(NoSuchElementException e) {
						return new ResponseEntity<Object>(new SnackNotFoundException(snack).getMessage(),HttpStatus.NOT_FOUND);
					}
				}
				
				UUID bookingId = UUID.randomUUID();
				Booking booking = new Booking(bookingId, bookingObject.bookingDate, tickets, snacks, show, user , bookingObject.state);
				booking.setQrCode(qrCodeGenerator.generateQRCode(booking.getId()));
				
				ticketRepository.saveAll(tickets);
				
				return new ResponseEntity<Object>(bookingRepositroy.save(booking), HttpStatus.CREATED);
			} catch(Exception e) {
				seatService.freeSeats(seatIDs, bookingObject.showID);
				ticketRepository.deleteAll(tickets);
				
				return new ResponseEntity<Object>(e.getMessage(),HttpStatus.CONFLICT);
			}
			
			
		} else {
			return new ResponseEntity<Object>(HttpStatus.CONFLICT);
		}
		
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<Object> getAll(){
		return new ResponseEntity<Object>(bookingRepositroy.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getSpecific(@PathVariable UUID id){
		try {
			return new ResponseEntity<Object>(bookingRepositroy.findById(id).get(), HttpStatus.OK);
		}
		catch(NoSuchElementException e) {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/{id}/qrCode")
	public HttpEntity<byte[]> getBookingQrCode(@PathVariable UUID id){
		
		Booking booking = bookingRepositroy.findById(id).get();
		byte[] qrCode = booking.getQrCode();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
		headers.setContentLength(qrCode.length);
		
		return new HttpEntity<>(qrCode, headers);
				
	}
	
	@PutMapping("/{id}/changeStatus")
	public ResponseEntity<Object> changeToPaid(@RequestBody BookingConfigurationObject bookingObject, @PathVariable UUID id){
		
		ArrayList<UUID> seatsToChange = new ArrayList<>();
		try {
			Booking booking = bookingRepositroy.findById(id).get();
			if(booking.getState() != bookingObject.state) {
				if(bookingObject.state == BookingState.Canceled) {
					List<Ticket> bookings =  booking.getTickets();
					for(Ticket ticket:bookings) {
						Seat seat = ticket.getSeat();
						seatsToChange.add(seat.getId());
						
					}
					
					seatService.freeSeats(seatsToChange, bookingObject.showID);
				}
				booking.setState(bookingObject.state);
				return new ResponseEntity<Object>(bookingRepositroy.save(booking), HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(HttpStatus.NOT_MODIFIED);
			}
		} catch(NoSuchElementException e) {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		} 
		
	}

}
