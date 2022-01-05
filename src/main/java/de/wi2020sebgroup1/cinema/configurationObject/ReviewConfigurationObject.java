package de.wi2020sebgroup1.cinema.configurationObject;

import java.sql.Date;
import java.sql.Time;
import java.util.UUID;

public class ReviewConfigurationObject {
	
	public Date date;
	public Time time;
	public String header;
	public String content;
	public UUID movieID;
	public UUID userID;
	
	public ReviewConfigurationObject(Date date, Time time, String header, String content, UUID movieID, UUID userID) {
		super();
		this.date = date;
		this.time = time;
		this.header = header;
		this.content = content;
		this.movieID = movieID;
		this.userID = userID;
	}
	
}
