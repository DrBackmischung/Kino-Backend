package de.wi2020sebgroup1.cinema.communicationObjects;

import java.util.UUID;

import javax.validation.constraints.NotNull;

public class CinemaRoomCommunicationObject { 
	
	
	public int story;
	public boolean wheelchairAccessible;
	public UUID cinemaID;
	public UUID cinemaRoomSeatingPlan;
	
	public CinemaRoomCommunicationObject(@NotNull int story, @NotNull boolean wheelchairAccessible,
			UUID cinemaID, UUID cinemaRoomSeatingPlan) {
		this.story = story;
		this.wheelchairAccessible = wheelchairAccessible;
		this.cinemaID = cinemaID != null ? cinemaID : null;
		this.cinemaRoomSeatingPlan = cinemaRoomSeatingPlan != null ? cinemaRoomSeatingPlan : null;
	}

}
