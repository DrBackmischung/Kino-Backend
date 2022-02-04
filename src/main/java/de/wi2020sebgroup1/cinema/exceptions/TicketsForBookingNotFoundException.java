package de.wi2020sebgroup1.cinema.exceptions;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TicketsForBookingNotFoundException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public TicketsForBookingNotFoundException(UUID id) {
		super("No tickets for booking with id \"" + id + "\" found!");
	}

}
