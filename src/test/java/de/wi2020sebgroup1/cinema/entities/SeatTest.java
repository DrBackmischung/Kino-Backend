package de.wi2020sebgroup1.cinema.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SeatTest {
	
	@Test
	@DisplayName("Test the constructor")
    public void testConstructor() {
		CinemaRoomSeatingPlan c = new CinemaRoomSeatingPlan(0);
		Show s = new Show(null, null, null, null, null, null);
		Seat o = new Seat(1, 3, true, true, 20, c, s);
        assertEquals(o.getReihe(), 1);
        assertEquals(o.getPlace(), 3);
        assertEquals(o.isBlocked(), true);
        assertEquals(o.isCoupleSeat(), true);
        assertEquals(o.getSurcharge(), 20);
        assertEquals(o.getCinemaRoomSeatingPlan(), c);
        assertEquals(o.getShow(), s);
    }
	
	@Test
	@DisplayName("Test Getter/Setter")
    public void testGetterSetter() {
		CinemaRoomSeatingPlan c = new CinemaRoomSeatingPlan(0);
		Show s = new Show(null, null, null, null, null, null);
		Seat o = new Seat(1, 3, true, true, 20, c, s);
		o.setReihe(0);
        assertEquals(o.getReihe(), 0);
        o.setPlace(0);
        assertEquals(o.getPlace(), 0);
        o.setBlocked(false);
        assertEquals(o.isBlocked(), false);
        o.setCoupleSeat(false);
        assertEquals(o.isCoupleSeat(), false);
        o.setSurcharge(10);
        assertEquals(o.getSurcharge(), 10);
        o.setCinemaRoomSeatingPlan(null);
        assertEquals(o.getCinemaRoomSeatingPlan(), null);
        o.setShow(null);
        assertEquals(o.getShow(), null);
    }
	
	@Test
	@DisplayName("Equals consistency")
    public void testCompare() {
		UUID u = new UUID(2,2);
		CinemaRoomSeatingPlan c = new CinemaRoomSeatingPlan(0);
		Show s = new Show(null, null, null, null, null, null);
		Seat o = new Seat(1, 3, true, true, 20, c, s);
		o.setId(u);
		Seat o2 = new Seat(1, 3, true, true, 20, c, s);
		o2.setId(u);
		assertEquals(o.hashCode(), o2.hashCode());
		assertEquals(o.equals(o2), true);
		Seat o3 = new Seat(1, 3, false, false, 20, null, null);
		Seat o4 = new Seat(1, 3, false, false, 20, null, null);
		assertEquals(o3.hashCode(), o4.hashCode());
		assertEquals(o3.equals(o4), true);
    }
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	@DisplayName("Equals inconsistency fail")
    public void testCompareFail() {
		CinemaRoomSeatingPlan c = new CinemaRoomSeatingPlan(0);
		Show s = new Show(null, null, null, null, null, null);
		Seat o = new Seat(1, 3, true, true, 20, c, s);
		Seat o2 = new Seat(1, 4, true, true, 20, c, s);
		Seat o3 = new Seat(1, 3, false, true, 20, c, s);
		Seat o4 = new Seat(1, 3, true, false, 20, c, s);
		Seat o5 = new Seat(1, 3, true, true, 30, c, s);
		Seat o6 = new Seat(1, 3, true, true, 20, null, s);
		Seat o7 = new Seat(1, 3, true, true, 20, c, null);
		Seat o8 = new Seat(2, 3, true, true, 20, c, s);
		Seat o9 = null;
		String st = "Test";
		assertEquals(o.equals(o2), false);
		assertEquals(o.equals(o3), false);
		assertEquals(o.equals(o4), false);
		assertEquals(o.equals(o5), false);
		assertEquals(o.equals(o6), false);
		assertEquals(o.equals(o7), false);
		assertEquals(o.equals(o8), false);
		assertEquals(o.equals(o9), false);
		assertEquals(o.equals(st), false);
		Seat onull = new Seat(1, 3, true, true, 20, c, s);
		onull.setId(new UUID(2,2));
		assertEquals(o.equals(onull), false);
    }
	
}
