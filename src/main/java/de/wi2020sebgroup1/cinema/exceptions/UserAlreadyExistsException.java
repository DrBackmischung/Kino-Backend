package de.wi2020sebgroup1.cinema.exceptions;

import java.util.UUID;

public class UserAlreadyExistsException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public UserAlreadyExistsException(String email, String username) {
		super("A user with that Email or Username already exists!");
	}

}
