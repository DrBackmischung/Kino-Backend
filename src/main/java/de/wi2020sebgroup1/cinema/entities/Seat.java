package de.wi2020sebgroup1.cinema.entities;

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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import de.wi2020sebgroup1.cinema.enums.SeatState;
import de.wi2020sebgroup1.cinema.enums.SeatType;

@Entity
@Table(name="seat")
public class Seat {
	
	@Id
	@Column(columnDefinition= "VARBINARY(16)")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column
	@NotNull
	private int reihe;
	
	@Column
	@NotNull
	private int place;
	
	@Column
	@NotNull
	private SeatType type;
	
	@Column
	@NotNull
	@Enumerated(EnumType.ORDINAL)
	private SeatState state;
	
	@Column
	@NotNull
	private int surcharge;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "cinemaRoomSeatingPlan", referencedColumnName = "id")
	private CinemaRoomSeatingPlan cinemaRoomSeatingPlan;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "show_id", referencedColumnName = "id")
	private Show show;
	
	public Seat() {
		
	}
	
	public Seat(@NotNull int reihe, @NotNull int place, @NotNull SeatType type, @NotNull SeatState state,
			@NotNull int surcharge, CinemaRoomSeatingPlan cinemaRoomSeatingPlan, Show show) {
		super();
		this.reihe = reihe;
		this.place = place;
		this.type = type;
		this.state = state;
		this.surcharge = surcharge;
		this.cinemaRoomSeatingPlan = cinemaRoomSeatingPlan;
		this.show = show;
	}

	public CinemaRoomSeatingPlan getCinemaRoomSeatingPlan() {
		return cinemaRoomSeatingPlan;
	}
	
	public int getPlace() {
		return place;
	}
	
	public UUID getId() {
		return id;
	}
	
	public int getReihe() {
		return reihe;
	}
	
	public Show getShow() {
		return show;
	}
	
	public SeatState getState() {
		return state;
	}
	
	public SeatType getType() {
		return type;
	}
	
	public double getSurcharge() {
		return surcharge;
	}
	
	public void setSurcharge(int surcharge) {
		this.surcharge = surcharge;
	}
	
	public void setState(SeatState state) {
		this.state = state;
	}
	
	public void setCinemaRoomSeatingPlan(CinemaRoomSeatingPlan cinemaRoomSeatingPlan) {
		this.cinemaRoomSeatingPlan = cinemaRoomSeatingPlan;
	}
	
	public void setPlace(int column) {
		this.place = column;
	}
	
	public void setType(SeatType type) {
		this.type = type;
	}
	
	public void setReihe(int row) {
		this.reihe = row;
	}
	
	public void setShow(Show show) {
		this.show = show;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + state.hashCode();
		result = prime * result + ((cinemaRoomSeatingPlan == null) ? 0 : cinemaRoomSeatingPlan.hashCode());
		result = prime * result + type.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + place;
		long temp;
		temp = Double.doubleToLongBits(surcharge);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + reihe;
		result = prime * result + ((show == null) ? 0 : show.hashCode());
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
		Seat other = (Seat) obj;
		if (state != other.state)
			return false;
		if (cinemaRoomSeatingPlan != other.cinemaRoomSeatingPlan)
			return false;
		if (type != other.type)
			return false;
		if (id != other.id)
			return false;
		if (place != other.place)
			return false;
		if (surcharge != other.surcharge)
			return false;
		if (reihe != other.reihe)
			return false;
		if (show != other.show)
			return false;
		return true;
	}

}
