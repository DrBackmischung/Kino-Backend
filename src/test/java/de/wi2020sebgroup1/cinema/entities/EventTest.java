package de.wi2020sebgroup1.cinema.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.sql.Time;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EventTest {
	
	@Test
	@DisplayName("Test the constructor")
    public void testConstructor() {
		Event o = new Event(null, null, null, null, "Head", "Content", "localhost");
        assertEquals(o.getStartDate(), null);
        assertEquals(o.getEndDate(), null);
        assertEquals(o.getStartTime(), null);
        assertEquals(o.getEndTime(), null);
        assertEquals(o.getHeader(), "Head");
        assertEquals(o.getContent(), "Content");
        assertEquals(o.getPictureLink(), "localhost");
    }
	
	@Test
	@DisplayName("Test Getter/Setter")
    public void testGetterSetter() {
		Event o = new Event(null, null, null, null, "Head", "Content", "localhost");
		o.setStartDate(null);
        assertEquals(o.getStartDate(), null);
        o.setStartTime(null);
        assertEquals(o.getStartTime(), null);
		o.setEndDate(null);
        assertEquals(o.getEndDate(), null);
        o.setEndTime(null);
        assertEquals(o.getEndTime(), null);
        o.setHeader("!!!News!");
        assertEquals(o.getHeader(), "!!!News!");
        o.setContent("!!!Mathis stinkt");
        assertEquals(o.getContent(), "!!!Mathis stinkt");
        o.setPictureLink("128.0.0.1");
        assertEquals(o.getPictureLink(), "128.0.0.1");
    }
	
	@Test
	@DisplayName("Equals consistency")
    public void testCompare() {
		UUID u = new UUID(2,2);
		Date d = new Date(1);
		Time t = new Time(2);
		Event o = new Event(d, d, t, t, "Head", "Content", "localhost");
		o.setId(u);
		Event o2 = new Event(d, d, t, t, "Head", "Content", "localhost");
		o2.setId(u);
		assertEquals(o.hashCode(), o2.hashCode());
		assertEquals(o.equals(o2), true);
		assertEquals(o.equals(o), true);
		Event o3 = new Event(null, null, null, null, null, null, null);
		Event o4 = new Event(null, null, null, null, null, null, null);
		assertEquals(o3.hashCode(), o4.hashCode());
		assertEquals(o3.equals(o4), true);
    }
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	@DisplayName("Equals inconsistency fail")
    public void testCompareFail() {
		Date d = new Date(1);
		Time t = new Time(2);
		Event o = new Event(d, d, t, t, "Head", "Content", "localhost");
		Event o2 = new Event(null, d, t, t, "Head", "Content", "localhost");
		Event o3 = new Event(d, null, t, t, "Head", "Content", "localhost");
		Event o4 = new Event(d, d, null, t, "Head", "Content", "localhost");
		Event o5 = new Event(d, d, t, null, "Head", "Content", "localhost");
		Event o6 = new Event(d, d, t, t, null, "Content", "localhost");
		Event o7 = new Event(d, d, t, t, "Head", null, "localhost");
		Event o8 = new Event(d, d, t, t, "Head", "Content", null);
		Event o9 = null;
		String s = "Test";
		assertEquals(o.equals(o), true);
		assertEquals(o.equals(o2), false);
		assertEquals(o.equals(o3), false);
		assertEquals(o.equals(o4), false);
		assertEquals(o.equals(o5), false);
		assertEquals(o.equals(o6), false);
		assertEquals(o.equals(o7), false);
		assertEquals(o.equals(o8), false);
		assertEquals(o.equals(o9), false);
		assertEquals(o.equals(s), false);
		Event onull = new Event(d, d, t, t, "Head", "Content", "localhost");
		onull.setId(new UUID(2,2));
		assertEquals(o.equals(onull), false); 
    }
	
}
