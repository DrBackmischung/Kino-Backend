package de.wi2020sebgroup1.cinema.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CinemaRoomTest {
	
	@Test
	@DisplayName("Test the constructor")
    public void testConstructor() {
		Cinema c = new Cinema(null, null, null, 0, 0, null);
		CinemaRoomSeatingPlan c2 = new CinemaRoomSeatingPlan(0);
		CinemaRoom o = new CinemaRoom(1, true, c, c2);
		assertEquals(o.getStory(), 1);
		assertEquals(o.isWheelchairAccessible(), true);
		assertEquals(o.getCinema(), c);
		assertEquals(o.getCinemaRoomSeatingPlan(), c2);
    }
	
	@Test
	@DisplayName("Equals consistency")
    public void testCompare() {
		Cinema c = new Cinema(null, null, null, 0, 0, null);
		CinemaRoomSeatingPlan c2 = new CinemaRoomSeatingPlan(0);
		CinemaRoom o = new CinemaRoom(1, true, c, c2);
		CinemaRoom o2 = new CinemaRoom(1, true, c, c2);
		assertEquals(o.hashCode(), o2.hashCode());
		assertEquals(o.equals(o2), true);
    }

}
