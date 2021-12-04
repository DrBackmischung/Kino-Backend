package de.wi2020sebgroup1.cinema.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTest {
	
	@Test
	@DisplayName("Test the constructor")
    public void testConstructor() {
		Role r = new Role();
		User o = new User("DrBackmischung", "Neunzig", "Mathis", "mathis.neunzig@gmail.com", "ichBinDumm", r);
		assertEquals(o.getUserName(), "DrBackmischung");
        assertEquals(o.getName(), "Neunzig");
        assertEquals(o.getFirstName(), "Mathis");
        assertEquals(o.getEmail(), "mathis.neunzig@gmail.com");
        assertEquals(o.getPassword(), "ichBinDumm");
        assertEquals(o.getRole(), r);
	}
	
	@Test
	@DisplayName("Equals consistency")
    public void testCompare() {
		Role r = new Role();
		User o = new User("DrBackmischung", "Neunzig", "Mathis", "mathis.neunzig@gmail.com", "ichBinDumm", r);
		User o2 = new User("DrBackmischung", "Neunzig", "Mathis", "mathis.neunzig@gmail.com", "ichBinDumm", r);
		assertEquals(o.hashCode(), o2.hashCode());
		assertEquals(o.equals(o2), true);
    }
	
}
