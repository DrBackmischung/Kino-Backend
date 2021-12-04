package de.wi2020sebgroup1.cinema.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SeatTest {
	
	@Test
	@DisplayName("Test the constructor")
    public void testConstructor() {
		CinemaRoomSeatingPlan c = new CinemaRoomSeatingPlan();
		Show s = new Show();
		Seat o = new Seat(1, 3, true, true, 1.1, c, s);
        assertEquals(o.getReihe(), 1);
        assertEquals(o.getPlace(), 3);
        assertEquals(o.isBlocked(), true);
        assertEquals(o.isCoupleSeat(), true);
        assertEquals(o.getPriceMultiplier(), 1.1);
        assertEquals(o.getCinemaRoomSeatingPlan(), c);
        assertEquals(o.getShow(), s);
    }
	
}
