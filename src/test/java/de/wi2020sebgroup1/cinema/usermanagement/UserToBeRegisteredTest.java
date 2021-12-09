package de.wi2020sebgroup1.cinema.usermanagement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserToBeRegisteredTest {
	
	@Test
	@DisplayName("Test Getter/Setter")
    public void testGetterSetter() {
		UserToBeRegistered u = new UserToBeRegistered();
		u.setFirstName("Mathis");
		assertEquals(u.getFirstName(), "Mathis");
		u.setLastName("Neunzig");
		assertEquals(u.getLastName(), "Neunzig");
		u.setEmail("mail");
		assertEquals(u.getEmail(), "mail");
		u.setPassword("pw");
		assertEquals(u.getPassword(), "pw");
		u.setMatchingPassword("pw");
		assertEquals(u.getMatchingPassword(), "pw");
    }

}
