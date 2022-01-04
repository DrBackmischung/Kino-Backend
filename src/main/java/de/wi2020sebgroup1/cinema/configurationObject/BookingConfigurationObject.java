package de.wi2020sebgroup1.cinema.configurationObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.UUID;

import de.wi2020sebgroup1.cinema.enums.BookingState;

public class BookingConfigurationObject {
	
	public Date bookingDate;
	public UUID showID;
	public UUID userID;
	public ArrayList<UUID> seatIDs;
	public BookingState state;
	
	public BookingConfigurationObject(Date bookDate, UUID userID, UUID showID, ArrayList<UUID> seatIDs, BookingState state) {
		this.seatIDs = seatIDs;
		this.showID = showID;
		this.userID = userID;
		this.bookingDate = bookDate;
		this.state = state;
	}

}
