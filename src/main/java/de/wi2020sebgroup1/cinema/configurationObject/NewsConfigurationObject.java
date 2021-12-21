package de.wi2020sebgroup1.cinema.configurationObject;

import java.sql.Date;
import java.sql.Time;
import java.util.UUID;

public class NewsConfigurationObject {
	
	public Date date;
	public Time time;
	public String header;
	public String content;
	public String pictureLink;
	public UUID userID;
	
	public NewsConfigurationObject(Date date, Time time, String header, String content, String pictureLink, UUID userID) {
		super();
		this.date = date;
		this.time = time;
		this.header = header;
		this.content = content;
		this.pictureLink = pictureLink;
		this.userID = userID;
	}
	
}
