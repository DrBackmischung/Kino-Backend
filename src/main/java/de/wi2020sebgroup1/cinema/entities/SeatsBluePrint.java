package de.wi2020sebgroup1.cinema.entities;

import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.wi2020sebgroup1.cinema.enums.SeatType;

@Entity
@Table(name="cinemaRoomSeatsBlueprint")
@JsonIgnoreProperties("seatingPlan")
public class SeatsBluePrint {
	
	@Id
	@Column(columnDefinition= "VARBINARY(16)")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column
	@NotNull
	private int line;
	
	@Column
	@NotNull
	private int place;
	
	@Column
	@NotNull
	private SeatType type;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "price_id", referencedColumnName = "id")
	private Price price;

	@ManyToOne
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "cinemaRoom_id", referencedColumnName = "id")
	private CinemaRoom cinemaRoom;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "seatingPlan_id", referencedColumnName = "id")
	private CinemaRoomSeatingPlan seatingPlan;
	
	public SeatsBluePrint() {
		
	}
	
	public SeatsBluePrint(@NotNull int line, @NotNull int place, @NotNull SeatType type, @NotNull Price price, 
			@NotNull CinemaRoom cinemaRoom, @NotNull CinemaRoomSeatingPlan seatingPlan) {
		this.line = line;
		this.place = place;
		this.price = price;
		this.type = type;
		this.cinemaRoom = cinemaRoom;
		this.seatingPlan = seatingPlan;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
	
	public CinemaRoomSeatingPlan getSeatingPlan() {
		return seatingPlan;
	}
	
	public SeatType getType() {
		return type;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public int getPlace() {
		return place;
	}

	public void setPlace(int place) {
		this.place = place;
	}
	
	public void setSeatingPlan(CinemaRoomSeatingPlan seatingPlan) {
		this.seatingPlan = seatingPlan;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}
	
	public void setType(SeatType type) {
		this.type = type;
	}

	public CinemaRoom getCinemaRoom() {
		return cinemaRoom;
	}

	public void setCinemaRoom(CinemaRoom cinemaRoom) {
		this.cinemaRoom = cinemaRoom;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cinemaRoom, id, line, place, price);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SeatsBluePrint other = (SeatsBluePrint) obj;
		if (id != other.id)
			return false;
		if (line != other.line)
			return false;
		if (place != other.place)
			return false;
		if (seatingPlan != other.seatingPlan)
			return false;
		if (cinemaRoom != other.cinemaRoom)
			return false;
		if (type != other.type)
			return false;
		if (price != other.price)
			return false;
		return true;
	}

}
