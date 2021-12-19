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
	@DisplayName("Test Getter/Setter")
    public void testGetterSetter() {
		Role o = new Role("Admin", "admin");
		o.setDescription("Desc");
        assertEquals(o.getDescription(), "Desc");
        o.setAuthorization("Auth");
        assertEquals(o.getAutorization(), "Auth");
    }
	
	@Test
	@DisplayName("Equals consistency")
    public void testCompare() {
		Role o = new Role("Admin", "admin");
		Role o2 = new Role("Admin", "admin");
		assertEquals(o.hashCode(), o2.hashCode());
		assertEquals(o.equals(o2), true);
    }
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	@DisplayName("Equals inconsistency fail")
    public void testCompareFail() {
		Role o = new Role("Admin", "admin");
		Role o2 = new Role("Supp", "admin");
		Role o3 = new Role("Admin", "mathis");
		Role o4 = null;
		String s = "Test";
		assertEquals(o.equals(o2), false);
		assertEquals(o.equals(o3), false);
		assertEquals(o.equals(o4), false);
		assertEquals(o.equals(s), false);
    }
	
}
