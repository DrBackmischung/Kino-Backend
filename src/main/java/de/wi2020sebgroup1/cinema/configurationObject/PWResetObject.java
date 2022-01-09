package de.wi2020sebgroup1.cinema.configurationObject;

import java.util.UUID;

public class PWResetObject {
	
	public String password;
	public UUID tokenID;
	public UUID userID;
	
	public PWResetObject(String password, UUID tokenID, UUID userID) {
		super();
		this.password = password;
		this.tokenID = tokenID;
		this.userID = userID;
	}
	
}
