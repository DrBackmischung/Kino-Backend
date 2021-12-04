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
	
}
