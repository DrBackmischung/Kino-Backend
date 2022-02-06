package de.wi2020sebgroup1.cinema.entities;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="cinemaRoomSeatingPlan")
@JsonIgnoreProperties("cinemaRoom")
public class CinemaRoomSeatingPlan {
	
	@Id
	@Column(columnDefinition= "VARBINARY(16)")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column
	@NotNull
	private int seats;
	
	@Column
	@NotNull
	private int reihen;
	
	@OneToOne(mappedBy = "cinemaRoomSeatingPlan", cascade = CascadeType.PERSIST)
	private CinemaRoom cinemaRoom;
	
	@OneToMany(mappedBy = "seatingPlan")
	private List<SeatsBluePrint> seatBluePrints;

	public CinemaRoomSeatingPlan() {
		
	}
	
	public CinemaRoomSeatingPlan(@NotNull int seats) {
		super();
		this.seats = seats;
	}
	
	public void setCinemaRoom(CinemaRoom cinemaRoom) {
		this.cinemaRoom = cinemaRoom;
	}
	
	public int getReihen() {
		return reihen;
	}

	public UUID getId() {
		return id;
	}
	
	public CinemaRoom getCinemaRoom() {
		return cinemaRoom;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public void setReihen(int reihen) {
		this.reihen = reihen;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + seats;
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
		CinemaRoomSeatingPlan other = (CinemaRoomSeatingPlan) obj;
		if (id != other.id)
			return false;
		if (seats != other.seats)
			return false;
		return true;
	}

}
