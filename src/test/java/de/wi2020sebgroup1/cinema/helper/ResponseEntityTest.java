package de.wi2020sebgroup1.cinema.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ResponseEntityTest {
	
	@Test
	@DisplayName("Test the constructor")
    public void testConstructor() {
		ResponseEntity o = new ResponseEntity(404, "Not Found", "The data you were requesting does not exist.");
		assertEquals(o.getId(), 404);
		assertEquals(o.getName(), "Not Found");
		assertEquals(o.getDescription(), "The data you were requesting does not exist.");
    }
	
	@Test
	@DisplayName("Test Getter/Setter")
    public void testGetterSetter() {
		ResponseEntity o = new ResponseEntity(404, "Not Found", "The data you were requesting does not exist.");
		o.setId(200);
        assertEquals(o.getId(), 200);
        o.setName("Test");
        assertEquals(o.getName(), "Test");
        o.setDescription("Desc");
        assertEquals(o.getDescription(), "Desc");
    }
	
}
