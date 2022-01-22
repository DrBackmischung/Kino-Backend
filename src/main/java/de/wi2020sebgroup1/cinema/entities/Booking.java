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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
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
	private UUID id;
	
	@Column
	@NotNull
	private Date bookingDate;
	
	@Column
	@Lob
	private byte[] qrCode;
	
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
	
	@ManyToMany(mappedBy="booking")
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private List<Snack> snacks = new ArrayList<>();
	
	public Booking() {
		
	}
	
	public Booking(@NotNull UUID id,@NotNull Date bookingDate, @NotNull ArrayList<Ticket> tickets, @NotNull ArrayList<Snack> snacks, @NotNull Show show, 
			@NotNull User user, @NotNull BookingState state) {
		this.id = id;
		this.bookingDate = bookingDate;
		this.tickets = tickets;
		this.snacks = snacks;
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
	
	public byte[] getQrCode() {
		return qrCode;
	}
	
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
	
	public void setQrCode(byte[] qrCode) {
		this.qrCode = qrCode;
	}
	
	public void setState(BookingState state) {
		this.state = state;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public void setTickets(ArrayList<Ticket> tickets) {
		this.tickets = tickets;
	}

	public List<Snack> getSnacks() {
		return snacks;
	}

	public void setSnacks(List<Snack> snacks) {
		this.snacks = snacks;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookingDate == null) ? 0 : bookingDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((show == null) ? 0 : show.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((tickets == null) ? 0 : tickets.hashCode());
		result = prime * result + ((snacks == null) ? 0 : snacks.hashCode());
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
		Booking other = (Booking) obj;
		if (bookingDate != other.bookingDate)
			return false;
		if (id != other.id)
			return false;
		if (show != other.show)
			return false;
		if (state != other.state)
			return false;
		if (snacks != other.snacks)
			return false;
		if (tickets != other.tickets)
			return false;
		if (user != other.user)
			return false;
		return true;
	}

}
