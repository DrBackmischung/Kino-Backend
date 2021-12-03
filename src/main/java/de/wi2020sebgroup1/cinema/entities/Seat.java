package de.wi2020sebgroup1.cinema.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

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
	private boolean coupleSeat;
	
	@Column
	@NotNull
	private boolean blocked;
	
	@Column
	@NotNull
	private int priceMultiplier;
	
	@ManyToOne
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "cinemaRoomSeatingPlan", referencedColumnName = "id")
	private CinemaRoomSeatingPlan cinemaRoomSeatingPlan;
	
	@ManyToOne
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "show_id", referencedColumnName = "id")
	private Show show;
	
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
	
	public boolean isBlocked() {
		return blocked;
	}
	
	public boolean isCoupleSeat() {
		return coupleSeat;
	}
	
	public int getPriceMultiplier() {
		return priceMultiplier;
	}
	
	public void setPriceMultiplier(int priceMultiplier) {
		this.priceMultiplier = priceMultiplier;
	}
	
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	
	public void setCinemaRoomSeatingPlan(CinemaRoomSeatingPlan cinemaRoomSeatingPlan) {
		this.cinemaRoomSeatingPlan = cinemaRoomSeatingPlan;
	}
	
	public void setPlace(int column) {
		this.place = column;
	}
	
	public void setCoupleSeat(boolean coupleSeat) {
		this.coupleSeat = coupleSeat;
	}
	
	public void setReihe(int row) {
		this.reihe = row;
	}
	
	public void setShow(Show show) {
		this.show = show;
	}

}
