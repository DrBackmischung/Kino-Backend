package de.wi2020sebgroup1.cinema.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CinemaRoomSeatingPlanTest {
	
	@Test
	@DisplayName("Test the constructor")
    public void testConstructor() {
		CinemaRoom c = new CinemaRoom(0, false, null, null);
		CinemaRoomSeatingPlan o = new CinemaRoomSeatingPlan(50, c);
        assertEquals(o.getSeats(), 50);
        assertEquals(o.getCinemaRoom(), c);
    }
	
	@Test
	@DisplayName("Equals consistency")
    public void testCompare() {
		CinemaRoom c = new CinemaRoom(0, false, null, null);
		CinemaRoomSeatingPlan o = new CinemaRoomSeatingPlan(50, c);
		CinemaRoomSeatingPlan o2 = new CinemaRoomSeatingPlan(50, c);
		assertEquals(o.hashCode(), o2.hashCode());
		assertEquals(o.equals(o2), true);
    }
	
}
