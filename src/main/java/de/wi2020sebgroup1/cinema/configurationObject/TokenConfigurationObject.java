package de.wi2020sebgroup1.cinema.configurationObject;

import java.util.UUID;

public class TokenConfigurationObject {
	
	public boolean valid;
	public UUID userID;
	
	public TokenConfigurationObject(boolean valid, UUID userID) {
		super();
		this.valid = valid;
		this.userID = userID;
	}

}
