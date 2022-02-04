package de.wi2020sebgroup1.cinema.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SnackTest {
	
	@Test
	@DisplayName("Test the constructor")
    public void testConstructor() {
		Snack o = new Snack("big", "taco", "localhost", 0);
        assertEquals(o.getSize(), "big");
        assertEquals(o.getProduct(), "taco");
        assertEquals(o.getPictureLink(), "localhost");
        assertEquals(o.getPrice(), 0);
    }
	
	@Test
	@DisplayName("Test Getter/Setter")
    public void testGetterSetter() {
		Snack o = new Snack("big", "taco", "localhost", 0);
		o.setSize(null);
        assertEquals(o.getSize(), null);
        o.setProduct(null);
        assertEquals(o.getProduct(), null);
        o.setPictureLink("128.0.0.1");
        assertEquals(o.getPictureLink(), "128.0.0.1");
        o.setPrice(2);
        assertEquals(o.getPrice(), 2);
    }
	
	@Test
	@DisplayName("Equals consistency")
    public void testCompare() {
		UUID u = new UUID(2,2);
		Snack o = new Snack("big", "taco", "localhost", 2);
		o.setId(u);
		Snack o2 = new Snack("big", "taco", "localhost", 2);
		o2.setId(u);
		assertEquals(o.hashCode(), o2.hashCode());
		assertEquals(o.equals(o2), true);
		assertEquals(o.equals(o), true);
		Snack o3 = new Snack(null, null, null, 0);
		Snack o4 = new Snack(null, null, null, 0);
		assertEquals(o3.hashCode(), o4.hashCode());
		assertEquals(o3.equals(o4), true);
    }
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	@DisplayName("Equals inconsistency fail")
    public void testCompareFail() {
		Snack o = new Snack("big", "taco", "localhost", 0);
		Snack o2 = new Snack(null, "taco", "localhost", 0);
		Snack o3 = new Snack("big", null, "localhost", 0);
		Snack o4 = new Snack("big", "taco", null, 0);
		Event o5 = null;
		Snack o6 = new Snack("big", "taco", "localhost", 5);
		String s = "Test";
		assertEquals(o.equals(o), true);
		assertEquals(o.equals(o2), false);
		assertEquals(o.equals(o3), false);
		assertEquals(o.equals(o4), false);
		assertEquals(o.equals(o5), false);
		assertEquals(o.equals(o6), false);
		assertEquals(o.equals(s), false);
		Snack onull = new Snack("big", "taco", "localhost", 0);
		onull.setId(new UUID(2,2));
		assertEquals(o.equals(onull), false); 
    }
	
}
