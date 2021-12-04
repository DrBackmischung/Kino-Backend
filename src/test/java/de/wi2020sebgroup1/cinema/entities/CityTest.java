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
	@DisplayName("Equals consistency")
    public void testCompare() {
		City o = new City(68159, "Mannheim");
		City o2 = new City(68159, "Mannheim");
		assertEquals(o.hashCode(), o2.hashCode());
		assertEquals(o.equals(o2), true);
    }
	
}
