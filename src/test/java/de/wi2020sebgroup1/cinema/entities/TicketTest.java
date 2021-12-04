package de.wi2020sebgroup1.cinema.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TicketTest {
	
	@Test
	@DisplayName("Test the constructor")
    public void testConstructor() {
		User u = new User();
		Show s = new Show();
		Price p = new Price();
		Seat s2 = new Seat();
		Ticket o = new Ticket(true, u, s, p, s2);
        assertEquals(o.isPaid(), true);
        assertEquals(o.getUser(), u);
        assertEquals(o.getShow(), s);
        assertEquals(o.getPrice(), p);
        assertEquals(o.getSeat(), s2);
    }
	
	@Test
	@DisplayName("Equals consistency")
    public void testCompare() {
		User u = new User();
		Show s = new Show();
		Price p = new Price();
		Seat s2 = new Seat();
		Ticket o = new Ticket(true, u, s, p, s2);
		Ticket o2 = new Ticket(true, u, s, p, s2);
		assertEquals(o.hashCode(), o2.hashCode());
		assertEquals(o.equals(o2), true);
    }
	
}
