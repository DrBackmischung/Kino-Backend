package de.wi2020sebgroup1.cinema.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MovieTest {
	
	@Test
	@DisplayName("Test the constructor")
    public void testConstructor() {
		Movie o = new Movie("Shrek 3", "Shreku 3", "Japanisch", 1.4, "Janis Ruppel", "Kitty Blume, Flauuuschi", "Ich mag Züge!!11elf!", "Densha de go", "https://ich.bin.ein/link",  "noch 1 link", "comedy", 12);
        assertEquals(o.getTitle(), "Shrek 3");
        assertEquals(o.getOriginalTitle(), "Shreku 3");
        assertEquals(o.getLanguage(), "Japanisch");
        assertEquals(o.getDuration(), 1.4);
        assertEquals(o.getDirector(), "Janis Ruppel");
        assertEquals(o.getActors(), "Kitty Blume, Flauuuschi");
        assertEquals(o.getDescription(), "Ich mag Züge!!11elf!");
        assertEquals(o.getOriginalDescription(), "Densha de go");
        assertEquals(o.getPictureLink(), "https://ich.bin.ein/link");
        assertEquals(o.getTrailerLink(), "noch 1 link");
        assertEquals(o.getGenre(), "comedy");
        assertEquals(o.getFSK(), 12);
    }
	
	@Test
	@DisplayName("Test Getter/Setter")
    public void testGetterSetter() {
		Movie o = new Movie("Shrek 3", "Shreku 3", "Japanisch", 1.4, "Janis Ruppel", "Kitty Blume, Flauuuschi", "Ich mag Züge!!11elf!", "Densha de go", "https://ich.bin.ein/link",  "noch 1 link", "comedy", 12);
		o.setTitle("Arcane");
        assertEquals(o.getTitle(), "Arcane");
        o.setOriginalTitle("Arcane");
        assertEquals(o.getOriginalTitle(), "Arcane");
        o.setLanguage("Deutsch");
        assertEquals(o.getLanguage(), "Deutsch");
        o.setDuration(2);
        assertEquals(o.getDuration(), 2);
        o.setDirector("Eine Katze");
        assertEquals(o.getDirector(), "Eine Katze");
        o.setActors("Eine Katze, Ein Hund");
        assertEquals(o.getActors(), "Eine Katze, Ein Hund");
        o.setDescription("Nein");
        assertEquals(o.getDescription(), "Nein");
        o.setOriginalDescription("Nein");
        assertEquals(o.getOriginalDescription(), "Nein");
        o.setPictureLink("127.0.0.1");
        assertEquals(o.getPictureLink(), "127.0.0.1");
        o.setTrailerLink("127.0.0.1");
        assertEquals(o.getTrailerLink(), "127.0.0.1");
        o.setGenre("Nein");
        assertEquals(o.getGenre(), "Nein");
        o.setFSK(2);
        assertEquals(o.getFSK(), 2);
    }
	
	@Test
	@DisplayName("Equals consistency")
    public void testCompare() {
		UUID u = new UUID(2,2);
		Movie o = new Movie("Shrek 3", "Shreku 3", "Japanisch", 1.4, "Janis Ruppel", "Kitty Blume, Flauuuschi", "Ich mag Züge!!11elf!", "Densha de go", "https://ich.bin.ein/link",  "noch 1 link", "comedy", 12);
		o.setId(u);
		Movie o2 = new Movie("Shrek 3", "Shreku 3", "Japanisch", 1.4, "Janis Ruppel", "Kitty Blume, Flauuuschi", "Ich mag Züge!!11elf!", "Densha de go", "https://ich.bin.ein/link",  "noch 1 link", "comedy", 12);
		o2.setId(u);
		assertEquals(o.hashCode(), o2.hashCode());
		assertEquals(o.equals(o2), true);
		Movie o3 = new Movie("Shrek 3", "Shreku 3", "Japanisch", 1.4, "Janis Ruppel", "Kitty Blume, Flauuuschi", "Ich mag Züge!!11elf!", "Densha de go", "https://ich.bin.ein/link",  "noch 1 link", "comedy", 12);
		Movie o4 = new Movie("Shrek 3", "Shreku 3", "Japanisch", 1.4, "Janis Ruppel", "Kitty Blume, Flauuuschi", "Ich mag Züge!!11elf!", "Densha de go", "https://ich.bin.ein/link",  "noch 1 link", "comedy", 12);
		assertEquals(o3.hashCode(), o4.hashCode());
		assertEquals(o3.equals(o4), true);
    }
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	@DisplayName("Equals inconsistency fail")
    public void testCompareFail() {
		Movie o = new Movie("Shrek 3", "Shreku 3", "Japanisch", 1.4, "Janis Ruppel", "Kitty Blume, Flauuuschi", "Ich mag Züge!!11elf!", "Densha de go", "https://ich.bin.ein/link",  "noch 1 link", "comedy", 12);
		Movie o2 = new Movie("Shrek 2", "Shreku 3", "Japanisch", 1.4, "Janis Ruppel", "Kitty Blume, Flauuuschi", "Ich mag Züge!!11elf!", "Densha de go", "https://ich.bin.ein/link",  "noch 1 link", "comedy", 12);
		Movie o3 = new Movie("Shrek 3", "Shreku 2", "Japanisch", 1.4, "Janis Ruppel", "Kitty Blume, Flauuuschi", "Ich mag Züge!!11elf!", "Densha de go", "https://ich.bin.ein/link",  "noch 1 link", "comedy", 12);
		Movie o4 = new Movie("Shrek 3", "Shreku 3", "Deutsch", 1.4, "Janis Ruppel", "Kitty Blume, Flauuuschi", "Ich mag Züge!!11elf!", "Densha de go", "https://ich.bin.ein/link",  "noch 1 link", "comedy", 12);
		Movie o5 = new Movie("Shrek 3", "Shreku 3", "Japanisch", 2, "Janis Ruppel", "Kitty Blume, Flauuuschi", "Ich mag Züge!!11elf!", "Densha de go", "https://ich.bin.ein/link",  "noch 1 link", "comedy", 12);
		Movie o6 = new Movie("Shrek 3", "Shreku 3", "Japanisch", 1.4, "Janis Kruppel", "Kitty Blume, Flauuuschi", "Ich mag Züge!!11elf!", "Densha de go", "https://ich.bin.ein/link",  "noch 1 link", "comedy", 12);
		Movie o7 = new Movie("Shrek 3", "Shreku 3", "Japanisch", 1.4, "Janis Ruppel", "Kitty Blüte, Flauuuschi", "Ich mag Züge!!11elf!", "Densha de go", "https://ich.bin.ein/link",  "noch 1 link", "comedy", 12);
		Movie o8 = new Movie("Shrek 3", "Shreku 3", "Japanisch", 1.4, "Janis Ruppel", "Kitty Blume, Flauuuschi", "Ich mag keine Züge!!11elf!", "Densha de go", "https://ich.bin.ein/link",  "noch 1 link", "comedy", 12);
		Movie o9 = new Movie("Shrek 3", "Shreku 3", "Japanisch", 1.4, "Janis Ruppel", "Kitty Blume, Flauuuschi", "Ich mag Züge!!11elf!", "Densha de go!!!!!", "https://ich.bin.ein/link",  "noch 1 link", "comedy", 12);
		Movie o10 = new Movie("Shrek 3", "Shreku 3", "Japanisch", 1.4, "Janis Ruppel", "Kitty Blume, Flauuuschi", "Ich mag Züge!!11elf!", "Densha de go", "https://ich.bin.kein/link",  "noch 1 link", "comedy", 12);
		Movie o11 = new Movie("Shrek 3", "Shreku 3", "Japanisch", 1.4, "Janis Ruppel", "Kitty Blume, Flauuuschi", "Ich mag Züge!!11elf!", "Densha de go", "https://ich.bin.ein/link",  "noch 2 link", "comedy", 12);
		Movie o12 = new Movie("Shrek 3", "Shreku 3", "Japanisch", 1.4, "Janis Ruppel", "Kitty Blume, Flauuuschi", "Ich mag Züge!!11elf!", "Densha de go", "https://ich.bin.ein/link",  "noch 1 link", "dumb", 12);
		Movie o13 = new Movie("Shrek 3", "Shreku 3", "Japanisch", 1.4, "Janis Ruppel", "Kitty Blume, Flauuuschi", "Ich mag Züge!!11elf!", "Densha de go", "https://ich.bin.ein/link",  "noch 1 link", "comedy", 0);
		Movie o14 = null;
		String s = "Test";
		assertEquals(o.equals(o2), false);
		assertEquals(o.equals(o3), false);
		assertEquals(o.equals(o4), false);
		assertEquals(o.equals(o5), false);
		assertEquals(o.equals(o6), false);
		assertEquals(o.equals(o7), false);
		assertEquals(o.equals(o8), false);
		assertEquals(o.equals(o9), false);
		assertEquals(o.equals(o10), false);
		assertEquals(o.equals(o11), false);
		assertEquals(o.equals(o12), false);
		assertEquals(o.equals(o13), false);
		assertEquals(o.equals(o14), false);
		assertEquals(o.equals(s), false);
		Movie onull = new Movie("Shrek 3", "Shreku 3", "Japanisch", 1.4, "Janis Ruppel", "Kitty Blume, Flauuuschi", "Ich mag Züge!!11elf!", "Densha de go", "https://ich.bin.ein/link",  "noch 1 link", "comedy", 12);
		onull.setId(new UUID(2,2));
		assertEquals(o.equals(onull), false);
    }
	
}
