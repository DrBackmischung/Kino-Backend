package de.wi2020sebgroup1.cinema.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MovieNotCreatableException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public MovieNotCreatableException() {
		super("Movie can not be created!");
	}

}
