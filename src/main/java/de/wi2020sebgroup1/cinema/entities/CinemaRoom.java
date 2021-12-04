package de.wi2020sebgroup1.cinema.entities;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name="cinemaRoom")
public class CinemaRoom {
	
	@Id
	@Column(columnDefinition= "VARBINARY(16)")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column
	@NotNull
	private int story;
	
	@Column
	@NotNull
	private boolean wheelchairAccessible;
	
	@ManyToOne(cascade= CascadeType.ALL ,fetch=FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "cinema", referencedColumnName = "id")
	private Cinema cinema;
	

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "cinemaRoomSeatingPlan_id", referencedColumnName = "id")
	private CinemaRoomSeatingPlan cinemaRoomSeatingPlan;
	
	public CinemaRoom() {}
	
	public CinemaRoom(@NotNull int story, @NotNull boolean wheelchairAccessible, Cinema cinema,
			CinemaRoomSeatingPlan cinemaRoomSeatingPlan) {
		super();
		this.story = story;
		this.wheelchairAccessible = wheelchairAccessible;
		this.cinema = cinema;
		this.cinemaRoomSeatingPlan = cinemaRoomSeatingPlan;
	}

	public Cinema getCinema() {
		return cinema;
	}
	
	public UUID getId() {
		return id;
	}
	
	public int getStory() {
		return story;
	}
	
	public boolean getWheelchairAccessible() {
		return wheelchairAccessible;
	}
	
	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}
	
	public void setStory(int story) {
		this.story = story;
	}
	
	public void setWheelchairAccessible(boolean wheelchairAccessible) {
		this.wheelchairAccessible = wheelchairAccessible;
	}
}
