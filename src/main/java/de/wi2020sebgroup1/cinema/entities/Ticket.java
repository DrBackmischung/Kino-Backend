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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import de.wi2020sebgroup1.cinema.enums.TicketState;

@Entity
@Table(name="ticket")
public class Ticket {
	
	@Id
	@Column(columnDefinition= "VARBINARY(16)")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column
	@NotNull
	@Enumerated(EnumType.ORDINAL)
	private TicketState state;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "show_id", referencedColumnName = "id")
	private Show show;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "price_id", referencedColumnName = "id")
	private Price price;
	
	@OneToOne
	@JoinColumn(name="seat_id", referencedColumnName = "id")
	private Seat seat;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "booking_id", referencedColumnName = "id")
	private Booking booking;
	
	
	public Ticket() {
		
	}
	
	public Ticket(@NotNull TicketState state, User user, Show show, Price price, Seat seat) {
		super();
		this.state = state;
		this.user = user;
		this.show = show;
		this.price = price;	
		this.seat = seat;
	}
		
	public Price getPrice() {
		return price;
	}
	
	public UUID getId() {
		return id;
	}
	
	public Booking getBooking() {
		return booking;
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
	
	public TicketState getState() {
		return state;
	}
	
	public void setState(TicketState state) {
		this.state = state;
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
	
	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + state.hashCode();
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((seat == null) ? 0 : seat.hashCode());
		result = prime * result + ((show == null) ? 0 : show.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		Ticket other = (Ticket) obj;
		if (id != other.id)
			return false;
		if (state != other.state)
			return false;
		if (price != other.price)
			return false;
		if (seat != other.seat)
			return false;
		if (show != other.show)
			return false;
		if (user != other.user)
			return false;
		if (booking != other.booking)
			return false;
		return true;
	}
	
}
