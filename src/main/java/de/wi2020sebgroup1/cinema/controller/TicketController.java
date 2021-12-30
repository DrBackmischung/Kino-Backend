package de.wi2020sebgroup1.cinema.controller;

import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.Semaphore;

import javax.transaction.Transactional;

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

import de.wi2020sebgroup1.cinema.configurationObject.TicketConfigurationObject;
import de.wi2020sebgroup1.cinema.entities.Seat;
import de.wi2020sebgroup1.cinema.entities.Ticket;
import de.wi2020sebgroup1.cinema.enums.SeatState;
import de.wi2020sebgroup1.cinema.enums.TicketState;
import de.wi2020sebgroup1.cinema.exceptions.PriceNotFoundException;
import de.wi2020sebgroup1.cinema.exceptions.SeatAlreadyBookedException;
import de.wi2020sebgroup1.cinema.exceptions.SeatNotFoundException;
import de.wi2020sebgroup1.cinema.exceptions.ShowNotFoundException;
import de.wi2020sebgroup1.cinema.exceptions.TicketNotFoundException;
import de.wi2020sebgroup1.cinema.exceptions.UserNotFoundException;
import de.wi2020sebgroup1.cinema.helper.SemaphoreVault;
import de.wi2020sebgroup1.cinema.repositories.PriceRepository;
import de.wi2020sebgroup1.cinema.repositories.SeatRepository;
import de.wi2020sebgroup1.cinema.repositories.ShowRepository;
import de.wi2020sebgroup1.cinema.repositories.TicketRepository;
import de.wi2020sebgroup1.cinema.repositories.UserRepository;

@Controller
@RestController
@RequestMapping("/ticket")
public class TicketController {
	
	public static Semaphore checker = new Semaphore(1, true);
	
	@Autowired
	TicketRepository ticketRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	SeatRepository seatRepository;
	
	@Autowired
	PriceRepository priceRepository;
	
	@Autowired
	ShowRepository showRepository;
	
	@Autowired
	SemaphoreVault semaphoreVault;
	
	@PutMapping("/add")
	@Transactional
	public ResponseEntity<Object> addTicket(@RequestBody TicketConfigurationObject ticketConfigurationObject){
		UUID seatID = ticketConfigurationObject.seatID;
		UUID showID = ticketConfigurationObject.showID;
		Seat toBook = new Seat();
		try {
			SemaphoreVault.getSemaphore(showID).acquire();;
			toBook = seatRepository.findById(seatID).get();
			SeatState booked = toBook.getState();
			if(booked == SeatState.PAID || booked == SeatState.RESERVED) {
				return new ResponseEntity<Object>(new SeatAlreadyBookedException(seatID).getMessage(),
						HttpStatus.NOT_ACCEPTABLE);
			}
			toBook.setState(SeatState.RESERVED);
			seatRepository.save(toBook);
			
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		finally
		{
			SemaphoreVault.getSemaphore(showID).release();
		}
		
		Ticket toAdd = new Ticket();
		toAdd.setSeat(toBook);
		
		try {
			toAdd.setShow(showRepository.findById(ticketConfigurationObject.showID).get());
		}catch(IllegalArgumentException e) {
			return new ResponseEntity<Object>(new ShowNotFoundException(ticketConfigurationObject.showID).getMessage(),
					HttpStatus.NOT_FOUND);
		}
		
		try {
			toAdd.setPrice(priceRepository.findById(ticketConfigurationObject.priceID).get());
		}catch(IllegalArgumentException e) {
			return new ResponseEntity<Object>(new PriceNotFoundException(ticketConfigurationObject.priceID).getMessage(),
					HttpStatus.NOT_FOUND);
		}
		
		try {
			toAdd.setUser(userRepository.findById(ticketConfigurationObject.userID).get());
		}catch(IllegalArgumentException e) {
			return new ResponseEntity<Object>(new UserNotFoundException(ticketConfigurationObject.userID).getMessage(),
					HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Object>(ticketRepository.save(toAdd), HttpStatus.CREATED);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<Object> getAll(){
		return new ResponseEntity<Object>(ticketRepository.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getSpecific(@PathVariable UUID id){
		try {
			return new ResponseEntity<Object>(ticketRepository.findById(id).get(), HttpStatus.OK);
		}
		catch(NoSuchElementException e) {
			return new ResponseEntity<Object>(new TicketNotFoundException(id).getMessage(),
					HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/cancel/{id}")
	public ResponseEntity<Object> cancelTicket(@PathVariable UUID id){
		
		try {
			Ticket ticket = ticketRepository.findById(id).get();
			try {
				Seat seat = seatRepository.findById(ticket.getSeat().getId()).get();
				seat.setState(SeatState.FREE);
				seatRepository.save(seat);
			}
			catch(NoSuchElementException e) {
				return new ResponseEntity<Object>(new SeatNotFoundException(id).getMessage(),
						HttpStatus.NOT_FOUND);
			}
			ticket.setState(TicketState.CANCELLED);
			ticket.setSeat(null);
			return new ResponseEntity<Object>(ticketRepository.save(ticket), HttpStatus.OK);
			
		}
		catch(NoSuchElementException e) {
			return new ResponseEntity<Object>(new TicketNotFoundException(id).getMessage(),
					HttpStatus.NOT_FOUND);
		}
		
	}

}
