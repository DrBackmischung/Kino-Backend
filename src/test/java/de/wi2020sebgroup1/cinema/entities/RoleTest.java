package de.wi2020sebgroup1.cinema.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RoleTest {
	
	@Test
	@DisplayName("Test the constructor")
    public void testConstructor() {
		Role o = new Role("Admin", "admin");
        assertEquals(o.getDescription(), "Admin");
        assertEquals(o.getAutorization(), "admin");
    }
	
	@Test
	@DisplayName("Equals consistency")
    public void testCompare() {
		Role o = new Role("Admin", "admin");
		Role o2 = new Role("Admin", "admin");
		assertEquals(o.hashCode(), o2.hashCode());
		assertEquals(o.equals(o2), true);
    }
	
}
