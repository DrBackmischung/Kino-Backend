package de.wi2020sebgroup1.cinema.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.wi2020sebgroup1.cinema.enums.SeatState;
import de.wi2020sebgroup1.cinema.enums.SeatType;
import de.wi2020sebgroup1.cinema.enums.TicketState;

public class TicketTest {
	
	@Test
	@DisplayName("Test the constructor")
    public void testConstructor() {
		User u = new User(null, null, null, null, null, null, null, null, null, null, null);
		Show s = new Show(null, null, null, null, null, null);
		Price p = new Price(0, SeatType.PARQUET);
		Seat s2 = new Seat(0, 0, SeatType.PARQUET, SeatState.RESERVED, 0, null, s);
		Ticket o = new Ticket(TicketState.RESERVED, u, s, p, s2, null);
        assertEquals(o.getState(), TicketState.RESERVED);
        assertEquals(o.getUser(), u);
        assertEquals(o.getShow(), s);
        assertEquals(o.getPrice(), p);
        assertEquals(o.getSeat(), s2);
    }
	
	@Test
	@DisplayName("Test Getter/Setter")
    public void testGetterSetter() {
		User u = new User(null, null, null, null, null, null, null, null, null, null, null);
		Show s = new Show(null, null, null, null, null, null);
		Price p = new Price(0, SeatType.PARQUET);
		Seat s2 = new Seat(0, 0, SeatType.PARQUET, SeatState.RESERVED, 0, null, s);
		Ticket o = new Ticket(TicketState.RESERVED, u, s, p, s2, null);
		o.setState(TicketState.PAID);
        assertEquals(o.getState(), TicketState.PAID);
        o.setUser(null);
        assertEquals(o.getUser(), null);
        o.setShow(null);
        assertEquals(o.getShow(), null);
        o.setPrice(null);
        assertEquals(o.getPrice(), null);
        o.setSeat(null);
        assertEquals(o.getSeat(), null);
        UUID b = new UUID(2, 2);
        o.setBookingID(b);
        assertEquals(o.getBookingID(), b);
    }
	
	@Test
	@DisplayName("Equals consistency")
    public void testCompare() {
		UUID id = new UUID(2,2);
		User u = new User(null, null, null, null, null, null, null, null, null, null, null);
		Show s = new Show(null, null, null, null, null, null);
        UUID b = new UUID(2, 2);
		Price p = new Price(0, SeatType.PARQUET);
		Seat s2 = new Seat(0, 0, SeatType.PARQUET, SeatState.RESERVED, 0, null, s);
		Ticket o = new Ticket(TicketState.RESERVED, u, s, p, s2, null);
		o.setId(id);
		o.setBookingID(b);
		Ticket o2 = new Ticket(TicketState.RESERVED, u, s, p, s2, null);
		o2.setId(id);
		o2.setBookingID(b);
		assertEquals(o.hashCode(), o2.hashCode());
		assertEquals(o.equals(o), true);
		assertEquals(o.equals(o2), true);
		Ticket o3 = new Ticket(TicketState.CANCELLED, null, null, null, null, null);
		Ticket o4 = new Ticket(TicketState.CANCELLED, null, null, null, null, null);
		assertEquals(o3.hashCode(), o4.hashCode());
		assertEquals(o3.equals(o4), true);
    }
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	@DisplayName("Equals inconsistency fail")
    public void testCompareFail() {
		User u = new User(null, null, null, null, null, null, null, null, null, null, null);
		Show s = new Show(null, null, null, null, null, null);
		Price p = new Price(0, SeatType.PARQUET);
		Seat s2 = new Seat(0, 0, SeatType.PARQUET, SeatState.RESERVED, 0, null, s);
		Ticket o = new Ticket(TicketState.RESERVED, u, s, p, s2, null);
		Ticket o2 = new Ticket(TicketState.CANCELLED, u, s, p, s2, null);
		Ticket o3 = new Ticket(TicketState.RESERVED, null, s, p, s2, null);
		Ticket o4 = new Ticket(TicketState.RESERVED, u, null, p, s2, null);
		Ticket o5 = new Ticket(TicketState.RESERVED, u, s, null, s2, null);
		Ticket o6 = new Ticket(TicketState.RESERVED, u, s, p, null, null);
		Ticket o7 = new Ticket(TicketState.RESERVED, u, s, p, s2, new UUID(3,3));
		Ticket o8 = null;
		String st = "Test";
		assertEquals(o.equals(o2), false);
		assertEquals(o.equals(o3), false);
		assertEquals(o.equals(o4), false);
		assertEquals(o.equals(o5), false);
		assertEquals(o.equals(o6), false);
		assertEquals(o.equals(o7), false);
		assertEquals(o.equals(o8), false);
		assertEquals(o.equals(st), false);
		Ticket onull = new Ticket(TicketState.CANCELLED, u, s, p, s2, null);
		onull.setId(new UUID(2,2));
		assertEquals(o.equals(onull), false);
    }
	
}
