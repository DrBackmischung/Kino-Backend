package de.wi2020sebgroup1.cinema.configurationObject;

import java.util.UUID;

public class SeatsBlueprintConfigurationObject {
	
	public int line;
	public int place;
	//ADD SeatType
	public UUID priceID;
	public UUID cinemaRoomID;
	
	public SeatsBlueprintConfigurationObject(int line, int place, UUID priceID, UUID cinemaRoomID) {
		this.line = line;
		this.place = place;
		this.priceID = priceID;
		this.cinemaRoomID = cinemaRoomID;
	}

}
