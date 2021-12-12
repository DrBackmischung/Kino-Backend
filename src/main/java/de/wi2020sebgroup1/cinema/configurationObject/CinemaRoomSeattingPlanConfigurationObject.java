package de.wi2020sebgroup1.cinema.configurationObject;

import java.util.UUID;

import javax.validation.constraints.NotNull;

public class CinemaRoomSeattingPlanConfigurationObject {
	
	public int seats;
	public int reihen;
	public UUID cinemaRoomID;
	
	public CinemaRoomSeattingPlanConfigurationObject(@NotNull int seats, @NotNull int reihen, UUID cinemaRoomID) {
		this.seats = seats;
		this.reihen = reihen;
		this.cinemaRoomID = cinemaRoomID != null ? cinemaRoomID : null;
	}

}
