package de.wi2020sebgroup1.cinema.configurationObject;

import java.util.UUID;

import javax.validation.constraints.NotNull;

public class UserRegistrationObject {
	
	public String username;
	public String firstName;
	public String name;
	public String email;
	public String passwordHash;
	public String passwordConfirmHash;
	
	public String street;
	public String number;
	public UUID cityID;
	public UserRegistrationObject(@NotNull String username, @NotNull String firstName, @NotNull String name, @NotNull String email, @NotNull String passwordHash,
			@NotNull String passwordConfirmHash, @NotNull String street, @NotNull String number, @NotNull UUID cityID) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.name = name;
		this.email = email;
		this.passwordHash = passwordHash;
		this.passwordConfirmHash = passwordConfirmHash;
		this.street = street;
		this.number = number;
		this.cityID = cityID;
	}
	
	
	
}
