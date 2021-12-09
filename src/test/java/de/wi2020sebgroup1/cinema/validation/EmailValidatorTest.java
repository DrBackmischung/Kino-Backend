package de.wi2020sebgroup1.cinema.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class EmailValidatorTest {
	
	EmailValidator e = new EmailValidator();
    
	@Test
	void testValidation() {
		assertEquals(e.validateEmail("mathis.neunzig@gmail.com"), true);
		assertEquals(e.validateEmail("mathis.neunzig"), false);
		assertEquals(e.validateEmail("a@b"), false);
	}

}
