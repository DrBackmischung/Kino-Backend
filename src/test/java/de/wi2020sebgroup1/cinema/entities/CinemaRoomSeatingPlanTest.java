package de.wi2020sebgroup1.cinema.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CinemaRoomSeatingPlanTest {
	
	@Test
	@DisplayName("Test the constructor")
    public void testConstructor() {
		CinemaRoomSeatingPlan o = new CinemaRoomSeatingPlan(50);
        assertEquals(o.getSeats(), 50);
    }
	
	@Test
	@DisplayName("Test Getter/Setter")
    public void testGetterSetter() {
		CinemaRoomSeatingPlan o = new CinemaRoomSeatingPlan(50);
		o.setSeats(10);
        assertEquals(o.getSeats(), 10);
    }
	
	@Test
	@DisplayName("Equals consistency")
    public void testCompare() {
		CinemaRoomSeatingPlan o = new CinemaRoomSeatingPlan(50);
		CinemaRoomSeatingPlan o2 = new CinemaRoomSeatingPlan(50);
		assertEquals(o.hashCode(), o2.hashCode());
		assertEquals(o.equals(o2), true);
    }
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	@DisplayName("Equals inconsistency fail")
    public void testCompareFail() {
		CinemaRoomSeatingPlan o = new CinemaRoomSeatingPlan(50);
		CinemaRoomSeatingPlan o2 = new CinemaRoomSeatingPlan(20);
		CinemaRoomSeatingPlan o3 = null;
		String s = "Test";
		assertEquals(o.equals(o2), false);
		assertEquals(o.equals(o3), false);
		assertEquals(o.equals(s), false);
    }
	
}
