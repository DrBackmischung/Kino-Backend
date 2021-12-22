package de.wi2020sebgroup1.cinema.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

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
		UUID u = new UUID(2,2);
		Role o = new Role("Admin", "admin");
		o.setID(u);
		Role o2 = new Role("Admin", "admin");
		o2.setID(u);
		assertEquals(o.hashCode(), o2.hashCode());
		assertEquals(o.equals(o2), true);
		Role o3 = new Role(null, null);
		Role o4 = new Role(null, null);
		assertEquals(o3.hashCode(), o4.hashCode());
		assertEquals(o3.equals(o4), true);
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
		Role onull = new Role("Admin", "admin");
		onull.setID(new UUID(2,2));
		assertEquals(o.equals(onull), false);
    }
	
}
