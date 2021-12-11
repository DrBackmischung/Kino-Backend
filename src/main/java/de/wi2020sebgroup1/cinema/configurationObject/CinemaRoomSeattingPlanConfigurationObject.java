package de.wi2020sebgroup1.cinema.configurationObject;

import java.util.UUID;

import javax.validation.constraints.NotNull;

public class CinemaRoomSeattingPlanConfigurationObject {
	
	public int seats;
	public UUID cinemaRoomID;
	
	public CinemaRoomSeattingPlanConfigurationObject(@NotNull int seats, UUID cinemaRoomID) {
		this.seats = seats;
		this.cinemaRoomID = cinemaRoomID != null ? cinemaRoomID : null;
	}

}
