package de.wi2020sebgroup1.cinema.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.sql.Time;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class NewsTest {
	
	@Test
	@DisplayName("Test the constructor")
    public void testConstructor() {
		News o = new News(null, null, "News!", "Mathis stinkt", "127.0.0.1", null);
        assertEquals(o.getDate(), null);
        assertEquals(o.getTime(), null);
        assertEquals(o.getHeader(), "News!");
        assertEquals(o.getContent(), "Mathis stinkt");
        assertEquals(o.getPictureLink(), "127.0.0.1");
        assertEquals(o.getUser(), null);
    }
	
	@Test
	@DisplayName("Test Getter/Setter")
    public void testGetterSetter() {
		News o = new News(null, null, "News!", "Mathis stinkt", "127.0.0.1", null);
		o.setDate(null);
        assertEquals(o.getDate(), null);
        o.setTime(null);
        assertEquals(o.getTime(), null);
        o.setHeader("!!!News!");
        assertEquals(o.getHeader(), "!!!News!");
        o.setContent("!!!Mathis stinkt");
        assertEquals(o.getContent(), "!!!Mathis stinkt");
        o.setPictureLink("128.0.0.1");
        assertEquals(o.getPictureLink(), "128.0.0.1");
        o.setUser(null);
        assertEquals(o.getUser(), null);
    }
	
	@Test
	@DisplayName("Equals consistency")
    public void testCompare() {
		UUID u = new UUID(2,2);
		Date d = new Date(1);
		Time t = new Time(2);
		User user = new User();
		News o = new News(d, t, "News!", "Mathis stinkt", "127.0.0.1", user);
		o.setId(u);
		News o2 = new News(d, t, "News!", "Mathis stinkt", "127.0.0.1", user);
		o2.setId(u);
		assertEquals(o.hashCode(), o2.hashCode());
		assertEquals(o.equals(o2), true);
		assertEquals(o.equals(o), true);
		News o3 = new News(null, null, null, null, null, null);
		News o4 = new News(null, null, null, null, null, null);
		assertEquals(o3.hashCode(), o4.hashCode());
		assertEquals(o3.equals(o4), true);
    }
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	@DisplayName("Equals inconsistency fail")
    public void testCompareFail() {
		Date d = new Date(1);
		Time t = new Time(2);
		User user = new User();
		News o = new News(d, t, "News!", "Mathis stinkt", "127.0.0.1", user);
		News o2 = new News(null, t, "News!", "Mathis stinkt", "127.0.0.1", user);
		News o3 = new News(d, null, "News!", "Mathis stinkt", "127.0.0.1", user);
		News o4 = new News(d, t, "News?", "Mathis stinkt", "127.0.0.1", user);
		News o5 = new News(d, t, "News!", "Mathis stinkt!", "127.0.0.1", user);
		News o6 = new News(d, t, "News!", "Mathis stinkt", "127.0.0.2", user);
		News o7 = new News(d, t, "News!", "Mathis stinkt", "127.0.0.1", null);
		News o8 = null;
		String s = "Test";
		assertEquals(o.equals(o2), false);
		assertEquals(o.equals(o3), false);
		assertEquals(o.equals(o4), false);
		assertEquals(o.equals(o5), false);
		assertEquals(o.equals(o6), false);
		assertEquals(o.equals(o7), false);
		assertEquals(o.equals(o8), false);
		assertEquals(o.equals(s), false);
		News onull = new News(new Date(2), null, "News!", "Mathis stinkt", "127.0.0.1", null);
		onull.setId(new UUID(2,2));
		assertEquals(o.equals(onull), false);
    }
	
}
