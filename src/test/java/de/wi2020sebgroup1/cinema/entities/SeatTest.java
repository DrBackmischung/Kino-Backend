package de.wi2020sebgroup1.cinema.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.wi2020sebgroup1.cinema.enums.SeatState;
import de.wi2020sebgroup1.cinema.enums.SeatType;

public class SeatTest {
	
	@Test
	@DisplayName("Test the constructor")
    public void testConstructor() {
		CinemaRoomSeatingPlan c = new CinemaRoomSeatingPlan(0);
		Show s = new Show(null, null, null, null, null, null);
		Seat o = new Seat(1, 3, SeatType.PARQUET, SeatState.RESERVED, 20, c, s);
        assertEquals(o.getReihe(), 1);
        assertEquals(o.getPlace(), 3);
        assertEquals(o.getState(), SeatState.RESERVED);
        assertEquals(o.getType(), SeatType.PARQUET);
        assertEquals(o.getSurcharge(), 20);
        assertEquals(o.getCinemaRoomSeatingPlan(), c);
        assertEquals(o.getShow(), s);
    }
	
	@Test
	@DisplayName("Test Getter/Setter")
    public void testGetterSetter() {
		CinemaRoomSeatingPlan c = new CinemaRoomSeatingPlan(0);
		Show s = new Show(null, null, null, null, null, null);
		Seat o = new Seat(1, 3, SeatType.PARQUET, SeatState.FREE, 20, c, s);
		o.setReihe(0);
        assertEquals(o.getReihe(), 0);
        o.setPlace(0);
        assertEquals(o.getPlace(), 0);
        o.setState(SeatState.RESERVED);
        assertEquals(o.getState(), SeatState.RESERVED);
        o.setType(SeatType.PREMIUM);
        assertEquals(o.getType(), SeatType.PREMIUM);
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
		CinemaRoomSeatingPlan c = new CinemaRoomSeatingPlan(0);
		Show s = new Show(null, null, null, null, null, null);
		Seat o = new Seat(1, 3, SeatType.PARQUET, SeatState.FREE, 20, c, s);
		Seat o2 = new Seat(1, 3, SeatType.PARQUET, SeatState.FREE, 20, c, s);
		assertEquals(o.hashCode(), o2.hashCode());
		assertEquals(o.equals(o2), true);
    }
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	@DisplayName("Equals inconsistency fail")
    public void testCompareFail() {
		CinemaRoomSeatingPlan c = new CinemaRoomSeatingPlan(0);
		Show s = new Show(null, null, null, null, null, null);
		Seat o = new Seat(1, 3, SeatType.PARQUET, SeatState.FREE, 20, c, s);
		Seat o2 = new Seat(1, 4, SeatType.PARQUET, SeatState.FREE, 20, c, s);
		Seat o3 = new Seat(1, 3, SeatType.PREMIUM, SeatState.FREE, 20, c, s);
		Seat o4 = new Seat(1, 3, SeatType.PARQUET, SeatState.RESERVED, 20, c, s);
		Seat o5 = new Seat(1, 3, SeatType.PARQUET, SeatState.FREE, 30, c, s);
		Seat o6 = new Seat(1, 3, SeatType.PARQUET, SeatState.FREE, 20, null, s);
		Seat o7 = new Seat(1, 3, SeatType.PARQUET, SeatState.FREE, 20, c, null);
		Seat o8 = new Seat(2, 3, SeatType.PARQUET, SeatState.FREE, 20, c, s);
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
    }
	
}
