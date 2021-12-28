package de.wi2020sebgroup1.cinema.configurationObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.UUID;

public class BookingConfigurationObject {
	
	public Date bookingDate;
	public UUID showID;
	public UUID userID;
	public ArrayList<UUID> seatIDs;
	
	public BookingConfigurationObject(Date bookDate, UUID userID, UUID showID, ArrayList<UUID> seatIDs) {
		this.seatIDs = seatIDs;
		this.showID = showID;
		this.userID = userID;
		this.bookingDate = bookDate;
	}

}
