package de.wi2020sebgroup1.cinema.controller;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import de.wi2020sebgroup1.cinema.entities.Ticket;
import de.wi2020sebgroup1.cinema.entities.User;
import de.wi2020sebgroup1.cinema.repositories.BookingRepositroy;
import de.wi2020sebgroup1.cinema.repositories.SeatRepository;
import de.wi2020sebgroup1.cinema.repositories.ShowRepository;
import de.wi2020sebgroup1.cinema.repositories.TicketRepository;
import de.wi2020sebgroup1.cinema.repositories.UserRepository;
import de.wi2020sebgroup1.cinema.service.SeatService;

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
	SeatService seatService;
	
	@PutMapping("/add")
	public ResponseEntity<Object> addBooking(@RequestBody BookingConfigurationObject bookingObject){
		
		ArrayList<Ticket> tickets = new ArrayList<>();
		
		ArrayList<UUID> seatIDs = bookingObject.seatIDs;
		
		Show show = showRepository.findById(bookingObject.showID).get();
		
		if(seatService.reserveSeats(seatIDs, bookingObject.showID)) {
			User user = userRepositroy.findById(bookingObject.userID).get();
			
			
			
			for(UUID seat : seatIDs) {
				Seat seatObject = seatRepository.findById(seat).get();
				Ticket ticket = new Ticket(false,user,show,seatObject);
				tickets.add(ticket);
			}
			
			Booking booking = new Booking(bookingObject.bookingDate, tickets, show, bookingObject.state);
			
			ticketRepository.saveAll(tickets);
			return new ResponseEntity<Object>(bookingRepositroy.save(booking), HttpStatus.OK);
			
		}else {
			//TODO CHANGE TO CUSTOM RESPONSE
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
			//TODO change to custom response
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/{id}/changeStatus")
	public ResponseEntity<Object> changeToPaid(@RequestBody BookingConfigurationObject bookingObject, @PathVariable UUID id){
		
		try {
			Booking booking = bookingRepositroy.findById(id).get();
			if(booking.getState() != bookingObject.state) {
				booking.setState(bookingObject.state);
				return new ResponseEntity<Object>(bookingRepositroy.save(booking), HttpStatus.OK);
			}
			else {
				//TODO change to no changes exception
				return new ResponseEntity<Object>(HttpStatus.NOT_MODIFIED);
			}
		}catch(NoSuchElementException e) {
			//TODO change to custom response
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		} 
		
	}

}
