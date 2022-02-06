package de.wi2020sebgroup1.cinema.configurationObject;

public class UserLoginObject {
	
	public String username;
	public String passwordHash;
	
	public UserLoginObject(String username, String passwordHash) {
		super();
		this.username = username;
		this.passwordHash = passwordHash;
	}
	
}
