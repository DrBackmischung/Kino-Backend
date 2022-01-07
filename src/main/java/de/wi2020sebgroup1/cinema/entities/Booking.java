package de.wi2020sebgroup1.cinema.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import de.wi2020sebgroup1.cinema.enums.BookingState;

@Entity
@Table(name="booking")
public class Booking {
	
	@Id
	@Column(columnDefinition= "VARBINARY(16)")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column
	@NotNull
	private Date bookingDate;
	
	//@Column
	//@Lob
	//private byte[] qrCode;
	
	@Column
	@NotNull
	@Enumerated(EnumType.ORDINAL)
	private BookingState state;
	
	@ManyToOne(cascade= CascadeType.ALL ,fetch=FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "show_id", referencedColumnName = "id")
	private Show show;
	
	@ManyToOne(cascade= CascadeType.ALL ,fetch=FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	
	@OneToMany(mappedBy="booking")
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<Ticket> tickets = new ArrayList<>();
	
	public Booking() {
		
	}
	
	public Booking(@NotNull Date bookingDate, @NotNull ArrayList<Ticket> tickets, @NotNull Show show, 
			@NotNull User user, @NotNull BookingState state) {
		this.bookingDate = bookingDate;
		this.tickets = tickets;
		this.state = state;
		this.user = user;
		this.show = show;
	}

	public BookingState getState() {
		return state;
	}
	
	public User getUser() {
		return user;
	}
	
	public Date getBookingDate() {
		return bookingDate;
	}
	
	public Show getShow() {
		return show;
	}

	public UUID getId() {
		return id;
	}
	/*
	public byte[] getQrCode() {
		return qrCode;
	}
	*/
	public List<Ticket> getTickets() {
		return tickets;
	}
	
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public void setShow(Show show) {
		this.show = show;
	}
	/*
	public void setQrCode(byte[] qrCode) {
		this.qrCode = qrCode;
	}
	*/
	public void setState(BookingState state) {
		this.state = state;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public void setTickets(ArrayList<Ticket> tickets) {
		this.tickets = tickets;
	}
	

}
