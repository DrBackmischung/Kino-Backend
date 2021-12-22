package de.wi2020sebgroup1.cinema.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TicketTest {
	
	@Test
	@DisplayName("Test the constructor")
    public void testConstructor() {
		User u = new User(null, null, null, null, null, null);
		Show s = new Show(null, null, null, null, null, null);
		Price p = new Price(0);
		Seat s2 = new Seat(0, 0, false, false, 0, null, s);
		Ticket o = new Ticket(true, u, s, p, s2);
        assertEquals(o.isPaid(), true);
        assertEquals(o.getUser(), u);
        assertEquals(o.getShow(), s);
        assertEquals(o.getPrice(), p);
        assertEquals(o.getSeat(), s2);
    }
	
	@Test
	@DisplayName("Test Getter/Setter")
    public void testGetterSetter() {
		User u = new User(null, null, null, null, null, null);
		Show s = new Show(null, null, null, null, null, null);
		Price p = new Price(0);
		Seat s2 = new Seat(0, 0, false, false, 0, null, s);
		Ticket o = new Ticket(true, u, s, p, s2);
		o.setPaid(false);
        assertEquals(o.isPaid(), false);
        o.setUser(null);
        assertEquals(o.getUser(), null);
        o.setShow(null);
        assertEquals(o.getShow(), null);
        o.setPrice(null);
        assertEquals(o.getPrice(), null);
        o.setSeat(null);
        assertEquals(o.getSeat(), null);
    }
	
	@Test
	@DisplayName("Equals consistency")
    public void testCompare() {
		UUID id = new UUID(2,2);
		User u = new User(null, null, null, null, null, null);
		Show s = new Show(null, null, null, null, null, null);
		Price p = new Price(0);
		Seat s2 = new Seat(0, 0, false, false, 0, null, s);
		Ticket o = new Ticket(true, u, s, p, s2);
		o.setId(id);
		Ticket o2 = new Ticket(true, u, s, p, s2);
		o2.setId(id);
		assertEquals(o.equals(o), true);
		assertEquals(o.hashCode(), o2.hashCode());
		assertEquals(o.equals(o2), true);
		Ticket o3 = new Ticket(false, null, null, null, null);
		Ticket o4 = new Ticket(false, null, null, null, null);
		assertEquals(o3.hashCode(), o4.hashCode());
		assertEquals(o3.equals(o4), true);
    }
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	@DisplayName("Equals inconsistency fail")
    public void testCompareFail() {
		User u = new User(null, null, null, null, null, null);
		Show s = new Show(null, null, null, null, null, null);
		Price p = new Price(0);
		Seat s2 = new Seat(0, 0, false, false, 0, null, s);
		Ticket o = new Ticket(true, u, s, p, s2);
		Ticket o2 = new Ticket(false, u, s, p, s2);
		Ticket o3 = new Ticket(true, null, s, p, s2);
		Ticket o4 = new Ticket(true, u, null, p, s2);
		Ticket o5 = new Ticket(true, u, s, null, s2);
		Ticket o6 = new Ticket(true, u, s, p, null);
		Ticket o7 = null;
		String st = "Test";
		assertEquals(o.equals(o2), false);
		assertEquals(o.equals(o3), false);
		assertEquals(o.equals(o4), false);
		assertEquals(o.equals(o5), false);
		assertEquals(o.equals(o6), false);
		assertEquals(o.equals(o7), false);
		assertEquals(o.equals(st), false);
    }
	
}
