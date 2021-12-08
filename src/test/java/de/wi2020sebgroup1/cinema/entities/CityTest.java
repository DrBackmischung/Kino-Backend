package de.wi2020sebgroup1.cinema.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CityTest {
	
	@Test
	@DisplayName("Test the constructor")
    public void testConstructor() {
		City o = new City(68159, "Mannheim");
        assertEquals(o.getPlz(), 68159);
        assertEquals(o.getCity(), "Mannheim");
    }
	
	@Test
	@DisplayName("Test Getter/Setter")
    public void testGetterSetter() {
		City o = new City(68159, "Mannheim");
		o.setPLZ(26127);
        assertEquals(o.getPlz(), 26127);
        o.setCity("Oldenburg");
        assertEquals(o.getCity(), "Oldenburg");
    }
	
	@Test
	@DisplayName("Equals consistency")
    public void testCompare() {
		City o = new City(68159, "Mannheim");
		City o2 = new City(68159, "Mannheim");
		assertEquals(o.hashCode(), o2.hashCode());
		assertEquals(o.equals(o2), true);
    }
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	@DisplayName("Equals inconsistency fail")
    public void testCompareFail() {
		City o = new City(68159, "Mannheim");
		City o2 = new City(26127, "Mannheim");
		City o3 = new City(68159, "Oldenburg");
		City o4 = null;
		String s = "Test";
		assertEquals(o.equals(o2), false);
		assertEquals(o.equals(o3), false);
		assertEquals(o.equals(o4), false);
		assertEquals(o.equals(s), false);
    }
	
}
