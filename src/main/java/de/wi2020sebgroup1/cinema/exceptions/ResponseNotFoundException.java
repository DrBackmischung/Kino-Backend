package de.wi2020sebgroup1.cinema.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResponseNotFoundException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public ResponseNotFoundException(int id) {
		super("Response with id \"" + id + "\" not found!");
	}

}
