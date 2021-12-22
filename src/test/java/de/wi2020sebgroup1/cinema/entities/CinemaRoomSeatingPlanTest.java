package de.wi2020sebgroup1.cinema.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CinemaRoomSeatingPlanTest {
	
	@Test
	@DisplayName("Test the constructor")
    public void testConstructor() {
		CinemaRoomSeatingPlan o = new CinemaRoomSeatingPlan(50);
		CinemaRoom c = o.getCinemaRoom();
		o.setCinemaRoom(c);
		assertEquals(c, o.getCinemaRoom());
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
		UUID u = new UUID(2,2);
		CinemaRoomSeatingPlan o = new CinemaRoomSeatingPlan(50);
		o.setId(u);
		CinemaRoomSeatingPlan o2 = new CinemaRoomSeatingPlan(50);
		o2.setId(u);
		assertEquals(o.hashCode(), o2.hashCode());
		assertEquals(o.equals(o2), true);
		CinemaRoomSeatingPlan o3 = new CinemaRoomSeatingPlan(50);
		CinemaRoomSeatingPlan o4 = new CinemaRoomSeatingPlan(50);
		assertEquals(o3.hashCode(), o4.hashCode());
		assertEquals(o3.equals(o4), true);
    }
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	@DisplayName("Equals inconsistency fail")
    public void testCompareFail() {
		CinemaRoomSeatingPlan o = new CinemaRoomSeatingPlan(50);
		CinemaRoomSeatingPlan o2 = new CinemaRoomSeatingPlan(20);
		CinemaRoomSeatingPlan o3 = new CinemaRoomSeatingPlan(20);
		o3.setCinemaRoom(new CinemaRoom(0, false, null, null));
		CinemaRoomSeatingPlan o4 = null;
		String s = "Test";
		assertEquals(o.equals(o2), false);
		assertEquals(o.equals(o3), false);
		o.setCinemaRoom(new CinemaRoom(0, true, null, null));
		assertEquals(o.equals(o3), false);
		assertEquals(o.equals(o4), false);
		assertEquals(o.equals(s), false);
    }
	
}
