package de.wi2020sebgroup1.cinema.Entities;

import java.sql.Date;
import java.sql.Time;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name="vorstellung")
public class Show {
	
	@Id
	@Column(columnDefinition= "VARBINARY(16)")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column
	@NotNull
	private java.sql.Date showDate;
	
	@Column
	@NotNull
	private java.sql.Time startTime;
	
	@Column
	@NotNull
	private java.sql.Time endTime;
	
	@ManyToOne
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "movie_id", referencedColumnName = "id")
	private Movie movie;
	
	@ManyToOne(cascade= CascadeType.ALL ,fetch=FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "cinema_id", referencedColumnName = "id")
	private Cinema cinema;
	
	@ManyToOne(cascade= CascadeType.ALL ,fetch=FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "cinemaRoom_id", referencedColumnName = "id")
	private CinemaRoom cinemaRoom;
	
	public Cinema getCinema() {
		return cinema;
	}
	
	public CinemaRoom getCinemaRoom() {
		return cinemaRoom;
	}
	
	public Date getShowDate() {
		return showDate;
	}
	
	public Time getEndTime() {
		return endTime;
	}
	
	public UUID getId() {
		return id;
	}
	
	public Movie getMovie() {
		return movie;
	}
	
	public Time getStartTime() {
		return startTime;
	}
	
	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}
	
	public void setCinemaRoom(CinemaRoom cinemaRoom) {
		this.cinemaRoom = cinemaRoom;
	}
	
	public void setShowDate(Date date) {
		this.showDate = date;
	}
	
	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}
	
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	
	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}
}
