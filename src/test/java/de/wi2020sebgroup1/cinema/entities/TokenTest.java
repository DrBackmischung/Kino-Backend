package de.wi2020sebgroup1.cinema.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TokenTest {
	
	@Test
	@DisplayName("Test the constructor")
    public void testConstructor() {
		User u = new User();
		Token o = new Token(true, u);
        assertEquals(o.isValid(), true);
        assertEquals(o.getUser(), u);
    }
	
	@Test
	@DisplayName("Test Getter/Setter")
    public void testGetterSetter() {
		User u = new User();
		Token o = new Token(true, u);
		o.setValid(false);
        assertEquals(o.isValid(), false);
        o.setUser(null);
        assertEquals(o.getUser(), null);
    }
	
	@Test
	@DisplayName("Equals consistency")
    public void testCompare() {
		UUID uuid = new UUID(2,2);
		User u = new User();
		Token o = new Token(true, u);
		o.setId(uuid);
		Token o2 = new Token(true, u);
		o2.setId(uuid);
		assertEquals(o.hashCode(), o2.hashCode());
		assertEquals(o.equals(o2), true);
		assertEquals(o.equals(o), true);
		Token o3 = new Token(false, null);
		Token o4 = new Token(false, null);
		assertEquals(o3.hashCode(), o4.hashCode());
		assertEquals(o3.equals(o4), true);
    }
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	@DisplayName("Equals inconsistency fail")
    public void testCompareFail() {
		User u = new User();
		Token o = new Token(true, u);
		Token o2 = new Token(false, u);
		Token o3 = new Token(true, null);
		Token o4 = null;
		String s = "Test";
		assertEquals(o.equals(o2), false);
		assertEquals(o.equals(o3), false);
		assertEquals(o.equals(o4), false);
		assertEquals(o.equals(s), false);
		Token onull = new Token(true, u);
		onull.setId(new UUID(2,2));
		assertEquals(o.equals(onull), false);
    }
	
}
