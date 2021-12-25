package de.wi2020sebgroup1.cinema.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;

public class UserVerificatorTest {
	
	@Test
	public void testVerificator() {
		UUID u = new UUID(3,4);
		int i = u.hashCode()+UserVerificator.KEY_OFFSET;
		assertEquals(UserVerificator.verify(u.toString(), i), true);
	}
	
}
