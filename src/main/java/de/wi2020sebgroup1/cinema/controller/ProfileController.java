package de.wi2020sebgroup1.cinema.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.wi2020sebgroup1.cinema.entities.Booking;
import de.wi2020sebgroup1.cinema.entities.Ticket;
import de.wi2020sebgroup1.cinema.entities.User;
import de.wi2020sebgroup1.cinema.exceptions.UserNotFoundException;
import de.wi2020sebgroup1.cinema.repositories.BookingRepositroy;
import de.wi2020sebgroup1.cinema.repositories.NewsRepository;
import de.wi2020sebgroup1.cinema.repositories.ReviewRepository;
import de.wi2020sebgroup1.cinema.repositories.TicketRepository;
import de.wi2020sebgroup1.cinema.repositories.UserRepository;

@Controller
@RestController
@RequestMapping("/user")
public class ProfileController {
	
	@Autowired
	BookingRepositroy bookingRepositroy;
	
	@Autowired
	TicketRepository ticketRepository;

	@Autowired
	NewsRepository newsRepository;
	
	@Autowired
	ReviewRepository reviewRepository;
	
	@Autowired
	UserRepository userReporitory;
	
	@GetMapping("/{id}/tickets")
	public ResponseEntity<Object> getTickets(@PathVariable UUID id){
		Optional<User> u = userReporitory.findById(id);
		try {
			User user = u.get();
			return new ResponseEntity<Object>(ticketRepository.findAllByUser(user), HttpStatus.OK);
		} catch(NoSuchElementException e) {
			return new ResponseEntity<Object>(new UserNotFoundException(id).getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/{id}/bookings")
	public ResponseEntity<Object> getBookings(@PathVariable UUID id){
		Iterable<Ticket> list = ticketRepository.findAll();
		List<UUID> u = new ArrayList<>();
		for(Ticket t : list) {
			if(t.getUser().getId().equals(id) && !u.contains(t.getBooking().getId()))
				u.add(t.getBooking().getId());
		}
		List<Booking> b = new ArrayList<>();
		for(UUID uuid : u) {
			b.add(bookingRepositroy.findById(uuid).get());
		}
		return new ResponseEntity<Object>(b, HttpStatus.OK);
	}
	
	@GetMapping("/{id}/reviews")
	public ResponseEntity<Object> getReviews(@PathVariable UUID id){
		Optional<User> u = userReporitory.findById(id);
		try {
			User user = u.get();
			return new ResponseEntity<Object>(reviewRepository.findAllByUser(user), HttpStatus.OK);
		} catch(NoSuchElementException e) {
			return new ResponseEntity<Object>(new UserNotFoundException(id).getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/{id}/news")
	public ResponseEntity<Object> getNews(@PathVariable UUID id){
		Optional<User> u = userReporitory.findById(id);
		try {
			User user = u.get();
			return new ResponseEntity<Object>(newsRepository.findAllByUser(user), HttpStatus.OK);
		} catch(NoSuchElementException e) {
			return new ResponseEntity<Object>(new UserNotFoundException(id).getMessage(), HttpStatus.NOT_FOUND);
		}
	}

}
