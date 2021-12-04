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
	private double priceMultiplier;
	
	@ManyToOne
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "cinemaRoomSeatingPlan", referencedColumnName = "id")
	private CinemaRoomSeatingPlan cinemaRoomSeatingPlan;
	
	@ManyToOne
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "show_id", referencedColumnName = "id")
	private Show show;
	
	public Seat() {}
	
	public Seat(@NotNull int reihe, @NotNull int place, @NotNull boolean coupleSeat, @NotNull boolean blocked,
			@NotNull double priceMultiplier, CinemaRoomSeatingPlan cinemaRoomSeatingPlan, Show show) {
		super();
		this.reihe = reihe;
		this.place = place;
		this.coupleSeat = coupleSeat;
		this.blocked = blocked;
		this.priceMultiplier = priceMultiplier;
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
	
	public boolean isBlocked() {
		return blocked;
	}
	
	public boolean isCoupleSeat() {
		return coupleSeat;
	}
	
	public double getPriceMultiplier() {
		return priceMultiplier;
	}
	
	public void setPriceMultiplier(double priceMultiplier) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (blocked ? 1231 : 1237);
		result = prime * result + ((cinemaRoomSeatingPlan == null) ? 0 : cinemaRoomSeatingPlan.hashCode());
		result = prime * result + (coupleSeat ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + place;
		long temp;
		temp = Double.doubleToLongBits(priceMultiplier);
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
		if (blocked != other.blocked)
			return false;
		if (cinemaRoomSeatingPlan == null) {
			if (other.cinemaRoomSeatingPlan != null)
				return false;
		} else if (!cinemaRoomSeatingPlan.equals(other.cinemaRoomSeatingPlan))
			return false;
		if (coupleSeat != other.coupleSeat)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (place != other.place)
			return false;
		if (Double.doubleToLongBits(priceMultiplier) != Double.doubleToLongBits(other.priceMultiplier))
			return false;
		if (reihe != other.reihe)
			return false;
		if (show == null) {
			if (other.show != null)
				return false;
		} else if (!show.equals(other.show))
			return false;
		return true;
	}

}
