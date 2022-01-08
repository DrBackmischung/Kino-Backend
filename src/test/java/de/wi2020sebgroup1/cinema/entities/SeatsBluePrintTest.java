package de.wi2020sebgroup1.cinema.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.wi2020sebgroup1.cinema.enums.SeatType;

public class SeatsBluePrintTest {
	
	@Test
	@DisplayName("Test the constructor")
    public void testConstructor() {
		Price p = new Price(0, null);
		CinemaRoom c = new CinemaRoom(0, true, null);
		CinemaRoomSeatingPlan c2 = new CinemaRoomSeatingPlan(5);
		c2.setReihen(1);
		SeatsBluePrint o = new SeatsBluePrint(1, 1, SeatType.LODGE, p, c, c2);
        assertEquals(o.getLine(), 1);
        assertEquals(o.getPlace(), 1);
        assertEquals(o.getPrice(), p);
        assertEquals(o.getType(), SeatType.LODGE);
        assertEquals(o.getCinemaRoom(), c);
        assertEquals(o.getSeatingPlan(), c2);
    }
	
	@Test
	@DisplayName("Test Getter/Setter")
    public void testGetterSetter() {
		Price p = new Price(0, null);
		CinemaRoom c = new CinemaRoom(0, true, null);
		CinemaRoomSeatingPlan c2 = new CinemaRoomSeatingPlan(5);
		c2.setReihen(1);
		SeatsBluePrint o = new SeatsBluePrint(1, 1, SeatType.LODGE, p, c, c2);
		o.setLine(0);
        assertEquals(o.getLine(), 0);
        o.setPlace(0);
        assertEquals(o.getPlace(), 0);
        o.setPrice(null);
        assertEquals(o.getPrice(), null);
        o.setType(null);
        assertEquals(o.getType(), null);
        o.setCinemaRoom(null);
        assertEquals(o.getCinemaRoom(), null);
        o.setSeatingPlan(null);
        assertEquals(o.getSeatingPlan(), null);
    }
	
	@Test
	@DisplayName("Equals consistency")
    public void testCompare() {
		UUID uuid = new UUID(2,2);
		Price p = new Price(0, null);
		CinemaRoom c = new CinemaRoom(0, true, null);
		CinemaRoomSeatingPlan c2 = new CinemaRoomSeatingPlan(5);
		c2.setReihen(1);
		SeatsBluePrint o = new SeatsBluePrint(1, 1, SeatType.LODGE, p, c, c2);
		o.setId(uuid);
		SeatsBluePrint o2 = new SeatsBluePrint(1, 1, SeatType.LODGE, p, c, c2);
		o2.setId(uuid);
		assertEquals(o.hashCode(), o2.hashCode());
		assertEquals(o.equals(o2), true);
		assertEquals(o.equals(o), true);
		SeatsBluePrint o3 = new SeatsBluePrint(0, 0, null, null, null, null);
		SeatsBluePrint o4 = new SeatsBluePrint(0, 0, null, null, null, null);
		assertEquals(o3.hashCode(), o4.hashCode());
		assertEquals(o3.equals(o4), true);
    }
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	@DisplayName("Equals inconsistency fail")
    public void testCompareFail() {
		Price p = new Price(0, null);
		CinemaRoom c = new CinemaRoom(0, true, null);
		CinemaRoomSeatingPlan c2 = new CinemaRoomSeatingPlan(5);
		c2.setReihen(1);
		SeatsBluePrint o = new SeatsBluePrint(1, 1, SeatType.LODGE, p, c, c2);
		SeatsBluePrint o2 = new SeatsBluePrint(0, 1, SeatType.LODGE, p, c, c2);
		SeatsBluePrint o3 = new SeatsBluePrint(1, 0, SeatType.LODGE, p, c, c2);
		SeatsBluePrint o4 = new SeatsBluePrint(1, 1, null, p, c, c2);
		SeatsBluePrint o5 = new SeatsBluePrint(1, 1, SeatType.LODGE, null, c, c2);
		SeatsBluePrint o6 = new SeatsBluePrint(1, 1, SeatType.LODGE, p, null, c2);
		SeatsBluePrint o7 = new SeatsBluePrint(1, 1, SeatType.LODGE, p, c, null);
		Booking o8 = null;
		String str = "Test";
		assertEquals(o.equals(o2), false);
		assertEquals(o.equals(o3), false);
		assertEquals(o.equals(o4), false);
		assertEquals(o.equals(o5), false);
		assertEquals(o.equals(o6), false);
		assertEquals(o.equals(o7), false);
		assertEquals(o.equals(o8), false);
		assertEquals(o.equals(str), false);
		SeatsBluePrint onull = new SeatsBluePrint(1, 1, SeatType.LODGE, p, c, c2);
		onull.setId(new UUID(2,2));
		assertEquals(o.equals(onull), false);
    }
	
}
