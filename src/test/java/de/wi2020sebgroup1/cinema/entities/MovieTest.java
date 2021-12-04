package de.wi2020sebgroup1.cinema.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MovieTest {
	
	@Test
	@DisplayName("Test the constructor")
    public void testConstructor() {
		Movie o = new Movie("Shrek 3", "Japanisch", 1.4, "Janis Ruppel", "Ich mag Züge!!11elf!", "https://ich.bin.ein/link");
        assertEquals(o.getTitel(), "Shrek 3");
        assertEquals(o.getLanguage(), "Japanisch");
        assertEquals(o.getDuration(), 1.4);
        assertEquals(o.getDirector(), "Janis Ruppel");
        assertEquals(o.getDescription(), "Ich mag Züge!!11elf!");
        assertEquals(o.getPictureLink(), "https://ich.bin.ein/link");
    }
	
}
