package de.wi2020sebgroup1.cinema.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
		Cinema o = new Cinema("Kino Mannheim", "Q6", "14", 5, 2, c);
		Cinema o2 = new Cinema("Kino Mannheim", "Q6", "14", 5, 2, c);
		assertEquals(o.hashCode(), o2.hashCode());
		assertEquals(o.equals(o2), true);
    }
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	@DisplayName("Equals inconsistency fail")
    public void testCompareFail() {
		City c = new City(0, null);
		Cinema o = new Cinema("Kino Mannheim", "Q6", "14", 5, 2, c);
		Cinema o2 = new Cinema("Kino Mannheim2", "Q6", "14", 5, 2, c);
		Cinema o3 = new Cinema("Kino Mannheim", "Q7", "14", 5, 2, c);
		Cinema o4 = new Cinema("Kino Mannheim", "Q6", "14A", 5, 2, c);
		Cinema o5 = new Cinema("Kino Mannheim", "Q6", "14", 6, 2, c);
		Cinema o6 = new Cinema("Kino Mannheim", "Q6", "14", 5, 1, c);
		Cinema o7 = new Cinema("Kino Mannheim", "Q6", "14", 5, 1, null);
		Cinema o8 = null;
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
