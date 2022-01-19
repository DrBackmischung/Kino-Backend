package de.wi2020sebgroup1.cinema.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.util.ArrayList;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.wi2020sebgroup1.cinema.enums.BookingState;

public class BookingTest {
	
	@Test
	@DisplayName("Test the constructor")
    public void testConstructor() {
		Date d = new Date(0);
		UUID bId = UUID.randomUUID();
		ArrayList<Ticket> t = new ArrayList<>();
		Show s = new Show(null, null, null, null, null, null);
		User u = new User(null, null, null, null, null, null, null, null, null, null);
		Booking o = new Booking(bId, d, t, s, u, BookingState.Paid);
        assertEquals(o.getBookingDate(), d);
        assertEquals(o.getTickets(), t);
        assertEquals(o.getShow(), s);
        assertEquals(o.getUser(), u);
        assertEquals(o.getState(), BookingState.Paid);
    }
	
	@Test
	@DisplayName("Test Getter/Setter")
    public void testGetterSetter() {
		Date d = new Date(0);
		UUID bId = UUID.randomUUID();
		ArrayList<Ticket> t = new ArrayList<>();
		Show s = new Show(null, null, null, null, null, null);
		User u = new User(null, null, null, null, null, null, null, null, null, null);
		Booking o = new Booking(bId, d, t, s, u, BookingState.Paid);
		o.setBookingDate(null);
        assertEquals(o.getBookingDate(), null);
        o.setTickets(null);
        assertEquals(o.getTickets(), null);
        o.setShow(null);
        assertEquals(o.getShow(), null);
        o.setUser(null);
        assertEquals(o.getUser(), null);
        o.setState(null);
        assertEquals(o.getState(), null);
    }
	
	@Test
	@DisplayName("Equals consistency")
    public void testCompare() {
		UUID uuid = new UUID(2,2);
		UUID bookingID = UUID.randomUUID();
		Date d = new Date(0);
		ArrayList<Ticket> t = new ArrayList<>();
		Show s = new Show(null, null, null, null, null, null);
		User u = new User(null, null, null, null, null, null, null, null, null, null);
		Booking o = new Booking(bookingID, d, t, s, u, BookingState.Paid);
		o.setId(uuid);
		Booking o2 = new Booking(bookingID, d, t, s, u, BookingState.Paid);
		o2.setId(uuid);
		assertEquals(o.hashCode(), o2.hashCode());
		assertEquals(o.equals(o2), true);
		assertEquals(o.equals(o), true);
		Booking o3 = new Booking(bookingID, null, null, null, null, null);
		Booking o4 = new Booking(bookingID, null, null, null, null, null);
		assertEquals(o3.hashCode(), o4.hashCode());
		assertEquals(o3.equals(o4), true);
    }
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	@DisplayName("Equals inconsistency fail")
    public void testCompareFail() {
		Date d = new Date(0);
		UUID bId = UUID.randomUUID();
		ArrayList<Ticket> t = new ArrayList<>();
		Show s = new Show(null, null, null, null, null, null);
		User u = new User(null, null, null, null, null, null, null, null, null, null);
		Booking o = new Booking(bId, d, t, s, u, BookingState.Paid);
		Booking o2 = new Booking(bId, null, t, s, u, BookingState.Paid);
		Booking o3 = new Booking(bId, d, null, s, u, BookingState.Paid);
		Booking o4 = new Booking(bId, d, t, null, u, BookingState.Paid);
		Booking o5 = new Booking(bId, d, t, s, null, BookingState.Paid);
		Booking o6 = new Booking(bId, d, t, s, u, null);
		Booking o7 = null;
		String str = "Test";
		assertEquals(o.equals(o2), false);
		assertEquals(o.equals(o3), false);
		assertEquals(o.equals(o4), false);
		assertEquals(o.equals(o5), false);
		assertEquals(o.equals(o6), false);
		assertEquals(o.equals(o7), false);
		assertEquals(o.equals(str), false);
		Booking onull = new Booking(bId, d, t, s, u, BookingState.Paid);
		onull.setId(new UUID(2,2));
		assertEquals(o.equals(onull), false);
    }
	
}
