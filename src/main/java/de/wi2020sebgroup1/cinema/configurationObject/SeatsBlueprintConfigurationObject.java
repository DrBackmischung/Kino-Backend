package de.wi2020sebgroup1.cinema.configurationObject;

import java.util.UUID;

import de.wi2020sebgroup1.cinema.enums.SeatType;

public class SeatsBlueprintConfigurationObject {
	
	public int line;
	public int place;
	public SeatType type;
	public UUID cinemaRoomID;
	
	public SeatsBlueprintConfigurationObject(int line, int place, SeatType type, UUID cinemaRoomID) {
		this.line = line;
		this.place = place;
		this.type = type;
		this.cinemaRoomID = cinemaRoomID;
	}

}
