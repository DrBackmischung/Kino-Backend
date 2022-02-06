package de.wi2020sebgroup1.cinema.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.sql.Time;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReviewTest {
	
	@Test
	@DisplayName("Test the constructor")
    public void testConstructor() {
		Review o = new Review(null, null, "Review!", "Mathis stinkt", null, null, 5);
        assertEquals(o.getDate(), null);
        assertEquals(o.getTime(), null);
        assertEquals(o.getHeader(), "Review!");
        assertEquals(o.getContent(), "Mathis stinkt");
        assertEquals(o.getUser(), null);
        assertEquals(o.getMovie(), null);
    }
	
	@Test
	@DisplayName("Test Getter/Setter")
    public void testGetterSetter() {
		Review o = new Review(null, null, "Review!", "Mathis stinkt", null, null, 5);
		o.setDate(null);
        assertEquals(o.getDate(), null);
        o.setTime(null);
        assertEquals(o.getTime(), null);
        o.setHeader("!!!News!");
        assertEquals(o.getHeader(), "!!!News!");
        o.setContent("!!!Mathis stinkt");
        assertEquals(o.getContent(), "!!!Mathis stinkt");
        o.setUser(null);
        assertEquals(o.getUser(), null);
        o.setMovie(null);
        assertEquals(o.getMovie(), null);
    }
	
	@Test
	@DisplayName("Equals consistency")
    public void testCompare() {
		UUID u = new UUID(2,2);
		Review o = new Review(null, null, "Review!", "Mathis stinkt", null, null, 5);
		o.setId(u);
		Review o2 = new Review(null, null, "Review!", "Mathis stinkt", null, null, 5);
		o2.setId(u);
		assertEquals(o.hashCode(), o2.hashCode());
		assertEquals(o.equals(o), true);
		assertEquals(o.equals(o2), true);
		Review o3 = new Review(null, null, "Review!", "Mathis stinkt", null, null, 5);
		Review o4 = new Review(null, null, "Review!", "Mathis stinkt", null, null, 5);
		assertEquals(o3.hashCode(), o4.hashCode());
		assertEquals(o3.equals(o4), true);
    }
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	@DisplayName("Equals inconsistency fail")
    public void testCompareFail() {
		Review o = new Review(null, null, "Review!", "Mathis stinkt", null, null, 5);
		Review o2 = new Review(new Date(2), null, "Review!", "Mathis stinkt", null, null, 5);
		Review o3 = new Review(null, new Time(2), "Review!", "Mathis stinkt", null, null, 5);
		Review o4 = new Review(null, null, "NoReview!", "Mathis stinkt", null, null, 5);
		Review o5 = new Review(null, null, "Review!", "Mathis stinkt nicht", null, null, 5);
		Review o6 = new Review(null, null, "Review!", "Mathis stinkt", new Movie(), null, 5);
		Review o7 = new Review(null, null, "Review!", "Mathis stinkt", null, new User(), 5);
		Review o8 = new Review(null, null, "Review!", "Mathis stinkt", null, null, 3);
		Review o9 = null;
		String s = "Test";
		assertEquals(o.equals(o2), false);
		assertEquals(o.equals(o3), false);
		assertEquals(o.equals(o4), false);
		assertEquals(o.equals(o5), false);
		assertEquals(o.equals(o6), false);
		assertEquals(o.equals(o7), false);
		assertEquals(o.equals(o8), false);
		assertEquals(o.equals(o9), false);
		assertEquals(o.equals(s), false);
		Review onull = new Review(null, null, "Review!", "Mathis stinkt", null, null, 5);
		onull.setId(new UUID(2,2));
		assertEquals(o.equals(onull), false);
    }
	
}
