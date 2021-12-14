package de.wi2020sebgroup1.cinema.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MovieTest {
	
	@Test
	@DisplayName("Test the constructor")
    public void testConstructor() {
		Movie o = new Movie("Shrek 3", "Japanisch", 1.4, "Janis Ruppel", "Ich mag Züge!!11elf!", "https://ich.bin.ein/link", 12);
        assertEquals(o.getTitel(), "Shrek 3");
        assertEquals(o.getLanguage(), "Japanisch");
        assertEquals(o.getDuration(), 1.4);
        assertEquals(o.getDirector(), "Janis Ruppel");
        assertEquals(o.getDescription(), "Ich mag Züge!!11elf!");
        assertEquals(o.getPictureLink(), "https://ich.bin.ein/link");
    }
	
	@Test
	@DisplayName("Test Getter/Setter")
    public void testGetterSetter() {
		Movie o = new Movie("Shrek 3", "Japanisch", 1.4, "Janis Ruppel", "Ich mag Züge!!11elf!", "https://ich.bin.ein/link", 12);
		o.setTitel("Arcane");
        assertEquals(o.getTitel(), "Arcane");
        o.setLanguage("Deutsch");
        assertEquals(o.getLanguage(), "Deutsch");
        o.setDuration(2);
        assertEquals(o.getDuration(), 2);
        o.setDirector("Eine Katze");
        assertEquals(o.getDirector(), "Eine Katze");
        o.setDescription("Nein");
        assertEquals(o.getDescription(), "Nein");
        o.setPictureLink("127.0.0.1");
        assertEquals(o.getPictureLink(), "127.0.0.1");
    }
	
	@Test
	@DisplayName("Equals consistency")
    public void testCompare() {
		Movie o = new Movie("Shrek 3", "Japanisch", 1.4, "Janis Ruppel", "Ich mag Züge!!11elf!", "https://ich.bin.ein/link", 12);
		Movie o2 = new Movie("Shrek 3", "Japanisch", 1.4, "Janis Ruppel", "Ich mag Züge!!11elf!", "https://ich.bin.ein/link", 12);
		assertEquals(o.hashCode(), o2.hashCode());
		assertEquals(o.equals(o2), true);
    }
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	@DisplayName("Equals inconsistency fail")
    public void testCompareFail() {
		Movie o = new Movie("Shrek 3", "Japanisch", 1.4, "Janis Ruppel", "Ich mag Züge!!11elf!", "https://ich.bin.ein/link", 12);
		Movie o2 = new Movie("Shrek 2", "Japanisch", 1.4, "Janis Ruppel", "Ich mag Züge!!11elf!", "https://ich.bin.ein/link", 12);
		Movie o3 = new Movie("Shrek 3", "Chinesisch", 1.4, "Janis Ruppel", "Ich mag Züge!!11elf!", "https://ich.bin.ein/link", 12);
		Movie o4= new Movie("Shrek 3", "Japanisch", 1.3, "Janis Ruppel", "Ich mag Züge!!11elf!", "https://ich.bin.ein/link", 12);
		Movie o5 = new Movie("Shrek 3", "Japanisch", 1.4, "Janis Kruppel", "Ich mag Züge!!11elf!", "https://ich.bin.ein/link", 12);
		Movie o6 = new Movie("Shrek 3", "Japanisch", 1.4, "Janis Ruppel", "Ich mag Busse!!11elf!", "https://ich.bin.ein/link", 12);
		Movie o7 = new Movie("Shrek 3", "Japanisch", 1.4, "Janis Ruppel", "Ich mag Züge!!11elf!", "https://ich.bin.kein/link", 12);
		Movie o8 = null;
		String s = "Test";
		assertEquals(o.equals(o2), false);
		assertEquals(o.equals(o3), false);
		assertEquals(o.equals(o4), false);
		assertEquals(o.equals(o5), false);
		assertEquals(o.equals(o6), false);
		assertEquals(o.equals(o7), false);
		assertEquals(o.equals(o8), false);
		assertEquals(o.equals(s), false);
    }
	
}
