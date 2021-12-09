package de.wi2020sebgroup1.cinema.exceptions;

public final class UserAlreadyExistsException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UserAlreadyExistsException(final String email) {
        super("There already exists an account with that email address: " + email);
    }
}