package de.wi2020sebgroup1.cinema.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CinemaTest {
	
	@Test
	@DisplayName("Test the constructor")
    public void testConstructor() {
		City c = new City(0, null);
		Cinema o = new Cinema("Kino Mannheim", "Q6", "14", 5, 2, c);
        assertEquals(o.getName(), "Kino Mannheim");
        assertEquals(o.getStreet(), "Q6");
        assertEquals(o.getNumber(), "14");
        assertEquals(o.getCinemaRooms(), 5);
        assertEquals(o.getStories(), 2);
        assertEquals(o.getCity(), c);
    }
	
	@Test
	@DisplayName("Test Getter/Setter")
    public void testGetterSetter() {
		City c = new City(0, null);
		Cinema o = new Cinema("Kino Mannheim", "Q6", "14", 5, 2, c);
		o.setName("Kinooo");
        assertEquals(o.getName(), "Kinooo");
        o.setStreet("Q7");
        assertEquals(o.getStreet(), "Q7");
        o.setNumber("33D");
        assertEquals(o.getNumber(), "33D");
        o.setCinemaRooms(3);
        assertEquals(o.getCinemaRooms(), 3);
        o.setStories(1);
        assertEquals(o.getStories(), 1);
        o.setCity(null);;
        assertEquals(o.getCity(), null);
    }
	
	@Test
	@DisplayName("Equals consistency")
    public void testCompare() {
		City c = new City(0, null);
		UUID u = new UUID(2,2);
		Cinema o = new Cinema("Kino Mannheim", "Q6", "14", 5, 2, c);
		o.setId(u);
		Cinema o2 = new Cinema("Kino Mannheim", "Q6", "14", 5, 2, c);
		o2.setId(u);
		Cinema o3 = new Cinema(null, null, null, 5, 2, null);
		o3.setId(null);
		Cinema o4 = new Cinema(null, null, null, 5, 2, null);
		o4.setId(null);
		
		assertEquals(o.hashCode(), o2.hashCode());
		assertEquals(o.equals(o2), true);
		assertEquals(o3.hashCode(), o4.hashCode());
		assertEquals(o3.equals(o4), true);
    }
	
	@SuppressWarnings({ "unlikely-arg-type" })
	@Test
	@DisplayName("Equals inconsistency fail")
    public void testCompareFail() {
		City c = new City(0, null);
		UUID u = new UUID(2,2);
		Cinema o = new Cinema("Kino Mannheim", "Q6", "14", 5, 2, c);
		o.setId(u);
		assertEquals(o.equals(o), true);
		Cinema o0 = new Cinema(null, null, null, 5, 2, null);
		assertEquals(o0.equals(o0), true);
		Cinema n = null;
		assertEquals(o.equals(n), false);
		String s = "Test";
		assertEquals(o.equals(s), false);
		Cinema o2 = new Cinema("Kino Mannheim", "Q6", "14", 4, 2, c);
		o2.setId(u);
		assertEquals(o.equals(o2), false);
		Cinema o3 = new Cinema("Kino Mannheim", "Q6", "14", 5, 2, null);
		o3.setId(u);
		Cinema o03 = new Cinema("Kino Mannheim", "Q6", "14", 5, 2, c);
		o03.setId(u);
		assertEquals(o.equals(o3), false);
		assertEquals(o0.equals(o03), false);
		Cinema o4 = new Cinema("Kino Mannheim", "Q6", "14", 5, 2, c);
		Cinema o04 = new Cinema("Kino Mannheim", "Q6", "14", 5, 2, c);
		o4.setId(null);
		o04.setId(u);
		assertEquals(o.equals(o4), false);
		assertEquals(o0.equals(o04), false);
		Cinema o5 = new Cinema(null, "Q6", "14", 5, 2, c);
		o5.setId(u);
		Cinema o05 = new Cinema("Kino Mannheim", "Q6", "14", 5, 2, c);
		o05.setId(u);
		assertEquals(o.equals(o5), false);
		assertEquals(o0.equals(o05), false);
		Cinema o6 = new Cinema("Kino Mannheim", "Q6", null, 5, 2, c);
		o6.setId(u);
		Cinema o06 = new Cinema("Kino Mannheim", "Q6", "14", 5, 2, c);
		o06.setId(u);
		assertEquals(o.equals(o6), false);
		assertEquals(o0.equals(o06), false);
		Cinema o7 = new Cinema("Kino Mannheim", "Q6", "14", 5, 1, c);
		o7.setId(u);
		assertEquals(o.equals(o7), false);
		Cinema o8 = new Cinema("Kino Mannheim", null, "14", 5, 2, c);
		o8.setId(u);
		Cinema o08 = new Cinema("Kino Mannheim", "Q6", "14", 5, 2, c);
		o08.setId(u);
		assertEquals(o.equals(o8), false);
		assertEquals(o0.equals(o08), false);
    }
	
}
