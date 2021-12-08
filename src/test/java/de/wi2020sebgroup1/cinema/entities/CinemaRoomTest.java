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
	@DisplayName("Test Getter/Setter")
    public void testGetterSetter() {
		Cinema c = new Cinema(null, null, null, 0, 0, null);
		CinemaRoomSeatingPlan c2 = new CinemaRoomSeatingPlan(0);
		CinemaRoom o = new CinemaRoom(1, true, c, c2);
		o.setStory(2);
		assertEquals(o.getStory(), 2);
		o.setWheelchairAccessible(false);
		assertEquals(o.isWheelchairAccessible(), false);
		o.setCinema(null);
		assertEquals(o.getCinema(), null);
		o.setCinemaRoomSeatingPlan(null);
		assertEquals(o.getCinemaRoomSeatingPlan(), null);
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
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	@DisplayName("Equals inconsistency fail")
    public void testCompareFail() {
		Cinema c = new Cinema(null, null, null, 0, 0, null);
		CinemaRoomSeatingPlan c2 = new CinemaRoomSeatingPlan(0);
		CinemaRoom o = new CinemaRoom(1, true, c, c2);
		CinemaRoom o2 = new CinemaRoom(2, true, c, c2);
		CinemaRoom o3 = new CinemaRoom(1, false, c, c2);
		CinemaRoom o4 = new CinemaRoom(1, true, null, c2);
		CinemaRoom o5 = new CinemaRoom(1, true, c, null);
		CinemaRoom o6 = null;
		String s = "Test";
		assertEquals(o.equals(o2), false);
		assertEquals(o.equals(o3), false);
		assertEquals(o.equals(o4), false);
		assertEquals(o.equals(o5), false);
		assertEquals(o.equals(o6), false);
		assertEquals(o.equals(s), false);
    }

}
