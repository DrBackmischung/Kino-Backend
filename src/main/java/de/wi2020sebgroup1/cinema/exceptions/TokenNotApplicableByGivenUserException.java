package de.wi2020sebgroup1.cinema.exceptions;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TokenNotApplicableByGivenUserException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public TokenNotApplicableByGivenUserException(UUID id, UUID userID) {
		super("Token with id \"" + id + "\" does not belong to user with id !"+userID);
	}

}
