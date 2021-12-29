package de.wi2020sebgroup1.cinema.entities;

import java.util.Objects;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="cinemaRoomSeatsBlueprint")
@JsonIgnoreProperties("cinemaRoom")
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
	
	//TODO Add SeatType
	
	@ManyToOne(cascade= CascadeType.ALL ,fetch=FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "price_Id", referencedColumnName = "id")
	private Price price;
	
	//TODO map seatsblueprint to cinemaRoom instead of cinemaRoomSeatingPlan
	@OneToOne(mappedBy = "cinemaRoomSeatingPlan")
	private CinemaRoom cinemaRoom;
	
	public SeatsBluePrint(@NotNull int line, @NotNull int place, @NotNull Price price, @NotNull CinemaRoom cinemaRoom) {
		this.line = line;
		this.place = place;
		this.price = price;
		this.cinemaRoom = cinemaRoom;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
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
		return Objects.equals(cinemaRoom, other.cinemaRoom) && Objects.equals(id, other.id) && line == other.line
				&& place == other.place && Objects.equals(price, other.price);
	}
	
	

}
