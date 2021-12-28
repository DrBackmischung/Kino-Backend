package de.wi2020sebgroup1.cinema.controller;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
			
			Booking booking = new Booking(bookingObject.bookingDate, tickets, show);
			
			ticketRepository.saveAll(tickets);
			return new ResponseEntity<Object>(bookingRepositroy.save(booking), HttpStatus.OK);
			
		}else {
			return new ResponseEntity<Object>(HttpStatus.NOT_ACCEPTABLE);
		}
		
	}

}
