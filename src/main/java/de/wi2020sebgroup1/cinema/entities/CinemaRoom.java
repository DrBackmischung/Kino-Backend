package de.wi2020sebgroup1.cinema.entities;

import java.util.List;
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
	
	@Column
	@NotNull
	private String roomName;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "cinema", referencedColumnName = "id")
	private Cinema cinema;
	
	@OneToOne(cascade = CascadeType.REMOVE)
	private CinemaRoomSeatingPlan seatingPlan;
	
	@OneToMany(mappedBy = "cinemaRoom", cascade = CascadeType.REMOVE)
	private List<SeatsBluePrint> seatsBlueprint;
	
	@OneToOne
	@JoinColumn(name = "cinemaRoomSeatingPlan_id", referencedColumnName = "id")
	private CinemaRoomSeatingPlan cinemaRoomSeatingPlan;
	
	public CinemaRoom() {
		
	}
	
	public CinemaRoom(@NotNull int story, @NotNull boolean wheelchairAccessible, @NotNull String roomName) {
		super();
		this.story = story;
		this.wheelchairAccessible = wheelchairAccessible;
		this.roomName = roomName;
	}
	
	public CinemaRoom(@NotNull int story, @NotNull boolean wheelchairAccessible, Cinema cinema, @NotNull String roomName) {
		super();
		this.story = story;
		this.wheelchairAccessible = wheelchairAccessible;
		this.cinema = cinema;
		this.roomName = roomName;
	}

	public Cinema getCinema() {
		return cinema;
	}
	
	public String getRoomName() {
		return roomName;
	}
	
	public void setSeatingPlan(CinemaRoomSeatingPlan seatingPlan) {
		this.seatingPlan = seatingPlan;
	}
	
	public UUID getId() {
		return id;
	}
	
	public int getStory() {
		return story;
	}
	
	public boolean isWheelchairAccessible() {
		return wheelchairAccessible;
	}
	
	public CinemaRoomSeatingPlan getCinemaRoomSeatingPlan() {
		return cinemaRoomSeatingPlan;
	}
	
	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public void setStory(int story) {
		this.story = story;
	}
	
	public void setCinemaRoomSeatingPlan(CinemaRoomSeatingPlan cinemaRoomSeatingPlan) {
		this.cinemaRoomSeatingPlan = cinemaRoomSeatingPlan;
	}
	
	public CinemaRoomSeatingPlan getSeatingPlan() {
		return seatingPlan;
	}
	
	public void setWheelchairAccessible(boolean wheelchairAccessible) {
		this.wheelchairAccessible = wheelchairAccessible;
	}
	
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cinema == null) ? 0 : cinema.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + story;
		result = prime * result + (wheelchairAccessible ? 1231 : 1237);
		result = prime * result + ((roomName == null) ? 0 : roomName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CinemaRoom other = (CinemaRoom) obj;
		if (cinema != other.cinema)
			return false;
		if (roomName != other.roomName)
			return false;
		if (cinemaRoomSeatingPlan != other.cinemaRoomSeatingPlan)
			return false;
		if (id != other.id)
			return false;
		if (story != other.story)
			return false;
		if (wheelchairAccessible != other.wheelchairAccessible)
			return false;
		return true;
	}
}
