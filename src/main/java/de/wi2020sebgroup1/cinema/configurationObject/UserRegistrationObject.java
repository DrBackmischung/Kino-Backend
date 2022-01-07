package de.wi2020sebgroup1.cinema.configurationObject;

public class UserRegistrationObject {
	
	public String username;
	public String firstName;
	public String name;
	public String email;
	public String passwordHash;
	public String passwordConfirmHash;
	
	public String street;
	public String number;
	public int plz;
	public String city;
	
	public UserRegistrationObject(String username, String firstName, String name, String email, String passwordHash,
			String passwordConfirmHash, String street, String number, int plz, String city) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.name = name;
		this.email = email;
		this.passwordHash = passwordHash;
		this.passwordConfirmHash = passwordConfirmHash;
		this.street = street;
		this.number = number;
		this.plz = plz;
		this.city = city;
	}
	
}
