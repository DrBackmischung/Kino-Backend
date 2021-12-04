package de.wi2020sebgroup1.cinema.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SeatTest {
	
	@Test
	@DisplayName("Test the constructor")
    public void testConstructor() {
		CinemaRoomSeatingPlan c = new CinemaRoomSeatingPlan(0, null);
		Show s = new Show(null, null, null, null, null, null);
		Seat o = new Seat(1, 3, true, true, 1.1, c, s);
        assertEquals(o.getReihe(), 1);
        assertEquals(o.getPlace(), 3);
        assertEquals(o.isBlocked(), true);
        assertEquals(o.isCoupleSeat(), true);
        assertEquals(o.getPriceMultiplier(), 1.1);
        assertEquals(o.getCinemaRoomSeatingPlan(), c);
        assertEquals(o.getShow(), s);
    }
	
	@Test
	@DisplayName("Equals consistency")
    public void testCompare() {
		CinemaRoomSeatingPlan c = new CinemaRoomSeatingPlan(0, null);
		Show s = new Show(null, null, null, null, null, null);
		Seat o = new Seat(1, 3, true, true, 1.1, c, s);
		Seat o2 = new Seat(1, 3, true, true, 1.1, c, s);
		assertEquals(o.hashCode(), o2.hashCode());
		assertEquals(o.equals(o2), true);
    }
	
}
