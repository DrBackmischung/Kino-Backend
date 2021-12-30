package de.wi2020sebgroup1.cinema.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MovieTest {
	
	@Test
	@DisplayName("Test the constructor")
    public void testConstructor() {
		Movie o = new Movie("Shrek 3", null, "Japanisch", 1.4, "Janis Ruppel", null, "Ich mag Züge!!11elf!", null, "https://ich.bin.ein/link",  null, null, 12);
        assertEquals(o.getTitle(), "Shrek 3");
        assertEquals(o.getLanguage(), "Japanisch");
        assertEquals(o.getDuration(), 1.4);
        assertEquals(o.getDirector(), "Janis Ruppel");
        assertEquals(o.getDescription(), "Ich mag Züge!!11elf!");
        assertEquals(o.getPictureLink(), "https://ich.bin.ein/link");
    }
	
	@Test
	@DisplayName("Test Getter/Setter")
    public void testGetterSetter() {
		Movie o = new Movie("Shrek 3", null, "Japanisch", 1.4, "Janis Ruppel", null, "Ich mag Züge!!11elf!", null, "https://ich.bin.ein/link",  null, null, 12);
		o.setTitle("Arcane");
        assertEquals(o.getTitle(), "Arcane");
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
		UUID u = new UUID(2,2);
		Movie o = new Movie("Shrek 3", null, "Japanisch", 1.4, "Janis Ruppel", null, "Ich mag Züge!!11elf!", null, "https://ich.bin.ein/link",  null, null, 12);
		o.setId(u);
		Movie o2 = new Movie("Shrek 3", null, "Japanisch", 1.4, "Janis Ruppel", null, "Ich mag Züge!!11elf!", null, "https://ich.bin.ein/link",  null, null, 12);
		o2.setId(u);
		assertEquals(o.hashCode(), o2.hashCode());
		assertEquals(o.equals(o2), true);
		Movie o3 = new Movie(null, null, null, 1.4, null, null, null, null, null, null, null, 12);
		Movie o4 = new Movie(null, null, null, 1.4, null, null, null, null, null, null, null, 12);
		assertEquals(o3.hashCode(), o4.hashCode());
		assertEquals(o3.equals(o4), true);
    }
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	@DisplayName("Equals inconsistency fail")
    public void testCompareFail() {
		Movie o = new Movie("Shrek 3", null, "Japanisch", 1.4, "Janis Ruppel", null, "Ich mag Züge!!11elf!", null, "https://ich.bin.ein/link",  null, null, 12);
		Movie o2 = new Movie("Shrek 2", null, "Japanisch", 1.4, "Janis Ruppel", null, "Ich mag Züge!!11elf!", null, "https://ich.bin.ein/link",  null, null, 12);
		Movie o3 = new Movie("Shrek 3", null, "Chinesisch", 1.4, "Janis Ruppel", null, "Ich mag Züge!!11elf!", null, "https://ich.bin.ein/link",  null, null, 12);
		Movie o4 = new Movie("Shrek 3", null, "Japanisch", 1.3, "Janis Ruppel", null, "Ich mag Züge!!11elf!", null, "https://ich.bin.ein/link",  null, null, 12);
		Movie o5 = new Movie("Shrek 3", null, "Japanisch", 1.4, "Janis Kruppel", null, "Ich mag Züge!!11elf!", null, "https://ich.bin.ein/link",  null, null, 12);
		Movie o6 = new Movie("Shrek 3", null, "Japanisch", 1.4, "Janis Ruppel", null, "Ich mag Busse!!11elf!", null, "https://ich.bin.ein/link",  null, null, 12);
		Movie o7 = new Movie("Shrek 3", null, "Japanisch", 1.4, "Janis Ruppel", null, "Ich mag Züge!!11elf!", null, "https://ich.bin.kein/link",  null, null, 12);
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
		Movie onull = new Movie("Shrek 3", null, "Japanisch", 1.4, "Janis Ruppel", null, "Ich mag Züge!!11elf!", null, "https://ich.bin.ein/link",  null, null, 12);;
		onull.setId(new UUID(2,2));
		assertEquals(o.equals(onull), false);
    }
	
}
