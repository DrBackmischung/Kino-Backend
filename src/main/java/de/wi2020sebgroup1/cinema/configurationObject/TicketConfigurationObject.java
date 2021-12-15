package de.wi2020sebgroup1.cinema.configurationObject;

import java.util.UUID;

import javax.validation.constraints.NotNull;

public class TicketConfigurationObject {
	
	public UUID userID;
	public UUID seatID;
	public UUID priceID;
	public UUID showID;
	
	public TicketConfigurationObject(@NotNull UUID userID, @NotNull UUID seatID, @NotNull UUID priceID, @NotNull UUID showID) {
		this.userID = userID;
		this.seatID = seatID;
		this.priceID = priceID;
		this.showID = showID;
	}
	
}
