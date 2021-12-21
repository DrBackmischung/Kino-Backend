package de.wi2020sebgroup1.cinema.exceptions;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class SeatAlreadyBookedException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	public SeatAlreadyBookedException(UUID id) {
		super("Seat with it \"" + id + "\" already booked!");
	}

}
