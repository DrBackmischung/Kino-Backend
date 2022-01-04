package de.wi2020sebgroup1.cinema.configurationObject;

import java.util.UUID;

public class UserConfigurationObject {
	
	public String username;
	public String firstName;
	public String name;
	public String email;
	public String password;
	public UUID roleID;
	
	public String street;
	public String number;
	public int plz;
	public String city;
	
	public String payPalMail;
	public UUID creditCardID;
	
	public UserConfigurationObject(String username, String firstName, String name, String email, String password,
			UUID roleID, String street, String number, int plz, String city, String payPalMail, UUID creditCardID) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.name = name;
		this.email = email;
		this.password = password;
		this.roleID = roleID;
		this.street = street;
		this.number = number;
		this.plz = plz;
		this.city = city;
		this.payPalMail = payPalMail;
		this.creditCardID = creditCardID;
	}
	
}
