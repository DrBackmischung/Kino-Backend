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

import de.wi2020sebgroup1.cinema.repositories.CinemaRepository;

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
	
	public CinemaRoom() {
		
	}
	
	public CinemaRoom(@NotNull int story, @NotNull boolean wheelchairAccessible) {
		super();
		this.story = story;
		this.wheelchairAccessible = wheelchairAccessible;
	}
	
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
	
	public boolean isWheelchairAccessible() {
		return wheelchairAccessible;
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
	
	public void setWheelchairAccessible(boolean wheelchairAccessible) {
		this.wheelchairAccessible = wheelchairAccessible;
	}

	public CinemaRoomSeatingPlan getCinemaRoomSeatingPlan() {
		return cinemaRoomSeatingPlan;
	}

	public void setCinemaRoomSeatingPlan(CinemaRoomSeatingPlan cinemaRoomSeatingPlan) {
		this.cinemaRoomSeatingPlan = cinemaRoomSeatingPlan;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cinema == null) ? 0 : cinema.hashCode());
		result = prime * result + ((cinemaRoomSeatingPlan == null) ? 0 : cinemaRoomSeatingPlan.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + story;
		result = prime * result + (wheelchairAccessible ? 1231 : 1237);
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
		if (cinema == null) {
			if (other.cinema != null)
				return false;
		} else if (!cinema.equals(other.cinema))
			return false;
		if (cinemaRoomSeatingPlan == null) {
			if (other.cinemaRoomSeatingPlan != null)
				return false;
		} else if (!cinemaRoomSeatingPlan.equals(other.cinemaRoomSeatingPlan))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (story != other.story)
			return false;
		if (wheelchairAccessible != other.wheelchairAccessible)
			return false;
		return true;
	}
}
