package de.wi2020sebgroup1.cinema.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="cinemaRoomSeatingPlan")
public class CinemaRoomSeatingPlan {
	
	@Id
	@Column(columnDefinition= "VARBINARY(16)")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column
	@NotNull
	private int seats;
	
	@OneToOne
	@JoinColumn(name = "cinemaRoom_id", referencedColumnName = "id")
	private CinemaRoom cinemaRoom;

	public CinemaRoomSeatingPlan(@NotNull int seats) {
		super();
		this.seats = seats;
	}

	public UUID getId() {
		return id;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public CinemaRoom getCinemaRoom() {
		return cinemaRoom;
	}

	public void setCinemaRoom(CinemaRoom cinemaRoom) {
		this.cinemaRoom = cinemaRoom;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cinemaRoom == null) ? 0 : cinemaRoom.hashCode());
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
		if (cinemaRoom == null) {
			if (other.cinemaRoom != null)
				return false;
		} else if (!cinemaRoom.equals(other.cinemaRoom))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (seats != other.seats)
			return false;
		return true;
	}
	
	

}
