package de.wi2020sebgroup1.cinema.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CinemaTest {
	
	@Test
	@DisplayName("Test the constructor")
    public void testConstructor() {
		City c = new City();
		Cinema o = new Cinema("Kino Mannheim", "Q6", "14", 5, 2, c);
        assertEquals(o.getName(), "Kino Mannheim");
        assertEquals(o.getStreet(), "Q6");
        assertEquals(o.getNumber(), "14");
        assertEquals(o.getCinemaRooms(), 5);
        assertEquals(o.getStories(), 2);
        assertEquals(o.getCity(), c);
    }
	
}
