package de.wi2020sebgroup1.cinema.configurationObject;

import java.sql.Date;
import java.sql.Time;
import java.util.UUID;

import javax.validation.constraints.NotNull;

public class ShowConfigurationObject {
	
	public Date date;
	public Time start;
	public Time end;
	public UUID movieID;
	public UUID cinemaID;
	public UUID cinemaRoomID;
	
	public ShowConfigurationObject(@NotNull Date date, @NotNull Time start, @NotNull Time end, @NotNull UUID movieID, 
			@NotNull UUID cinemaID, @NotNull UUID cinemaRoomID) {
		this.date = date;
		this.start = start;
		this.end = end;
		this.movieID = movieID != null ? movieID : null;
		this.cinemaID = cinemaID != null ? cinemaID : null;
		this.cinemaRoomID = cinemaRoomID != null ? cinemaRoomID : null;
	}
	

}
