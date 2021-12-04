package de.wi2020sebgroup1.cinema.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CinemaRoomTest {
	
	@Test
	@DisplayName("Test the constructor")
    public void testConstructor() {
		Cinema c = new Cinema();
		CinemaRoomSeatingPlan c2 = new CinemaRoomSeatingPlan();
		CinemaRoom o = new CinemaRoom(1, true, c, c2);
		assertEquals(o.getStory(), 1);
		assertEquals(o.isWheelchairAccessible(), true);
		assertEquals(o.getCinema(), c);
		assertEquals(o.getCinemaRoomSeatingPlan(), c2);
    }

}
