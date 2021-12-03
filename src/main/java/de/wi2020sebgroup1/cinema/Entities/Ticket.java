package de.wi2020sebgroup1.cinema.Entities;

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
@Table(name="ticket")
public class Ticket {
	
	@Id
	@Column(columnDefinition= "VARBINARY(16)")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column
	@NotNull
	private boolean paid;
	
	@ManyToOne(cascade= CascadeType.ALL ,fetch=FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	
	@ManyToOne(cascade= CascadeType.ALL ,fetch=FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "show_id", referencedColumnName = "id")
	private Show show;
	
	@ManyToOne(cascade= CascadeType.ALL ,fetch=FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "price_id", referencedColumnName = "id")
	private Price price;
	
	@OneToOne
	@JoinColumn(name="seat_id", referencedColumnName = "id")
	private Seat seat;
	
	public Price getPrice() {
		return price;
	}
	
	public UUID getId() {
		return id;
	}
	
	public Seat getSeat() {
		return seat;
	}
	
	public Show getShow() {
		return show;
	}
	
	public User getUser() {
		return user;
	}
	
	public boolean isPaid() {
		return paid;
	}
	
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	
	public void setPrice(Price price) {
		this.price = price;
	}
	
	public void setSeat(Seat seat) {
		this.seat = seat;
	}
	
	public void setShow(Show show) {
		this.show = show;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
}
