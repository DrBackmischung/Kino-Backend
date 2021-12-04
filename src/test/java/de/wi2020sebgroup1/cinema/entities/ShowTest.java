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
		Movie m = new Movie();
		Cinema c = new Cinema();
		CinemaRoom c2 = new CinemaRoom();
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
	
}
