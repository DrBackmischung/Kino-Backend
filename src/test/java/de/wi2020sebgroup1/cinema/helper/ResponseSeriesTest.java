package de.wi2020sebgroup1.cinema.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class ResponseSeriesTest {
	
	@Test
	void testValue() {
		assertEquals(1, ResponseSeries.INFORMATIONAL.value());
		assertEquals(2, ResponseSeries.SUCCESSFUL.value());
		assertEquals(3, ResponseSeries.REDIRECTION.value());
		assertEquals(4, ResponseSeries.CLIENT_ERROR.value());
		assertEquals(5, ResponseSeries.SERVER_ERROR.value());
		assertEquals(9, ResponseSeries.CUSTOM_ERROR.value());
	}
	
	@Test
	void testValueOf() {
		assertEquals(ResponseSeries.INFORMATIONAL, ResponseSeries.valueOf(101));
		assertThrows(IllegalArgumentException.class, new Executable() {
			
			@Override
			public void execute() throws Throwable {
				ResponseSeries.valueOf(7);
			}
		});
	}
	
}
