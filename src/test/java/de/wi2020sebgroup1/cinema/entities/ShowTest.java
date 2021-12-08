package de.wi2020sebgroup1.cinema.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.sql.Time;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ShowTest {
	
	@Test
	@DisplayName("Test the constructor")
    public void testConstructor() {
		Movie m = new Movie(null, null, 0, null, null, null);
		Cinema c = new Cinema(null, null, null, 0, 0, null);
		CinemaRoom c2 = new CinemaRoom(0, false, c, null);
		Date d = new Date(0);
		Time t = new Time(0), t2 = new Time(0);
		Show o = new Show(d, t, t2, m, c, c2);
        assertEquals(o.getShowDate(), d);
        assertEquals(o.getStartTime(), t);
        assertEquals(o.getEndTime(), t2);
        assertEquals(o.getMovie(), m);
        assertEquals(o.getCinema(), c);
        assertEquals(o.getCinemaRoom(), c2);
    }
	
	@Test
	@DisplayName("Test Getter/Setter")
    public void testGetterSetter() {
		Movie m = new Movie(null, null, 0, null, null, null);
		Cinema c = new Cinema(null, null, null, 0, 0, null);
		CinemaRoom c2 = new CinemaRoom(0, false, c, null);
		Date d = new Date(0);
		Time t = new Time(0), t2 = new Time(0);
		Show o = new Show(d, t, t2, m, c, c2);
		o.setShowDate(null);
        assertEquals(o.getShowDate(), null);
        o.setStartTime(null);
        assertEquals(o.getStartTime(), null);
        o.setEndTime(null);
        assertEquals(o.getEndTime(), null);
        o.setMovie(null);
        assertEquals(o.getMovie(), null);
        o.setCinema(null);
        assertEquals(o.getCinema(), null);
        o.setCinemaRoom(null);
        assertEquals(o.getCinemaRoom(), null);
    }
	
	@Test
	@DisplayName("Equals consistency")
    public void testCompare() {
		Movie m = new Movie(null, null, 0, null, null, null);
		Cinema c = new Cinema(null, null, null, 0, 0, null);
		CinemaRoom c2 = new CinemaRoom(0, false, c, null);
		Date d = new Date(0);
		Time t = new Time(0), t2 = new Time(0);
		Show o = new Show(d, t, t2, m, c, c2);
		Show o2 = new Show(d, t, t2, m, c, c2);
		assertEquals(o.hashCode(), o2.hashCode());
		assertEquals(o.equals(o2), true);
    }
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	@DisplayName("Equals inconsistency fail")
    public void testCompareFail() {
		Movie m = new Movie(null, null, 0, null, null, null);
		Cinema c = new Cinema(null, null, null, 0, 0, null);
		CinemaRoom c2 = new CinemaRoom(0, false, c, null);
		Date d = new Date(0);
		Time t = new Time(0), t2 = new Time(0);
		Show o = new Show(d, t, t2, m, c, c2);
		Show o2 = new Show(null, t, t2, m, c, c2);
		Show o3 = new Show(d, null, t2, m, c, c2);
		Show o4 = new Show(d, t, null, m, c, c2);
		Show o5 = new Show(d, t, t2, null, c, c2);
		Show o6 = new Show(d, t, t2, m, null, c2);
		Show o7 = new Show(d, t, t2, m, c, null);
		Show o8 = null;
		String s = "Test";
		assertEquals(o.equals(o2), false);
		assertEquals(o.equals(o3), false);
		assertEquals(o.equals(o4), false);
		assertEquals(o.equals(o5), false);
		assertEquals(o.equals(o6), false);
		assertEquals(o.equals(o7), false);
		assertEquals(o.equals(o8), false);
		assertEquals(o.equals(s), false);
    }
	
}
