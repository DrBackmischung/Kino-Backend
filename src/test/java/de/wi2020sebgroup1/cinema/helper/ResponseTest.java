package de.wi2020sebgroup1.cinema.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.http.HttpStatus;

public class ResponseTest {
	
	@Test
	void testValue() {
		assertEquals(404, Response.NOT_FOUND.value());
		assertEquals(403, Response.FORBIDDEN.value());
		assertEquals(418, Response.I_AM_A_TEAPOT.value());
	}
	
	@Test
	void testSeries() {
		assertEquals(ResponseSeries.CLIENT_ERROR, Response.NOT_FOUND.series());
		assertEquals(ResponseSeries.SERVER_ERROR, Response.INTERNAL_SERVER_ERROR.series());
		assertEquals(ResponseSeries.CUSTOM_ERROR, Response.LOGICAL_ERROR.series());
	}
	
	@Test
	void testResponseMessage() {
		assertEquals("I'm a teapot", Response.I_AM_A_TEAPOT.getReasonPhrase());
	}
	
	@Test
	void testCodeAffiliation() {
		assertEquals(true, Response.CONTINUE.is1xxInformational());
		assertEquals(true, Response.OK.is2xxSuccessful());
		assertEquals(true, Response.PERMANENT_REDIRECTED.is3xxRedirection());
		assertEquals(true, Response.CONFLICT.is4xxClientError());
		assertEquals(true, Response.SERVICE_UNAVAILABLE.is5xxServerError());
		assertEquals(true, Response.SYNCHRONIZATION_ERROR.is9xxCustomError());
		assertEquals(true, Response.NOT_FOUND.isError());
		assertEquals(true, Response.INTERNAL_SERVER_ERROR.isError());
		assertEquals(true, Response.LOGICAL_ERROR.isError());
	}
	
	@Test
	void testWrongCodeAffiliation() {
		assertEquals(false, Response.OK.is1xxInformational());
		assertEquals(false, Response.NOT_FOUND.is2xxSuccessful());
		assertEquals(false, Response.OK.is3xxRedirection());
		assertEquals(false, Response.INTERNAL_SERVER_ERROR.is4xxClientError());
		assertEquals(false, Response.CONTINUE.is5xxServerError());
		assertEquals(false, Response.CONTINUE.is9xxCustomError());
		assertEquals(false, Response.CONTINUE.isError());
		assertEquals(false, Response.OK.isError());
		assertEquals(false, Response.PERMANENT_REDIRECTED.isError());
	}
	
	@Test
	void testToString() {
		assertEquals("200 OK", Response.OK.toString());
	}
	
	@Test
	void testValueOf() {
		assertEquals(Response.NOT_FOUND, Response.valueOf(404));
		assertThrows(IllegalArgumentException.class, new Executable() {
			
			@Override
			public void execute() throws Throwable {
				Response.valueOf(708);
			}
		});
	}
	
	@Test
	void testStatus() {
		assertEquals(HttpStatus.NOT_FOUND, Response.NOT_FOUND.status());
		assertEquals(HttpStatus.OK, Response.OK.status());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, Response.LOGICAL_ERROR.status());
	}
	
}
