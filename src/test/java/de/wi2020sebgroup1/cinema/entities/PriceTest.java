package de.wi2020sebgroup1.cinema.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PriceTest {
	
	@Test
	@DisplayName("Test the constructor")
    public void testConstructor() {
		Price o = new Price(5.5);
		assertEquals(o.getPrice(), 5.5);
    }
	
}
