package de.wi2020sebgroup1.cinema.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SnackTest {
	
	@Test
	@DisplayName("Test the constructor")
    public void testConstructor() {
		Snack o = new Snack("big", "taco", "localhost");
        assertEquals(o.getSize(), "big");
        assertEquals(o.getProduct(), "taco");
        assertEquals(o.getPictureLink(), "localhost");
    }
	
	@Test
	@DisplayName("Test Getter/Setter")
    public void testGetterSetter() {
		Snack o = new Snack("big", "taco", "localhost");
		o.setSize(null);
        assertEquals(o.getSize(), null);
        o.setProduct(null);
        assertEquals(o.getProduct(), null);
        o.setPictureLink("128.0.0.1");
        assertEquals(o.getPictureLink(), "128.0.0.1");
    }
	
	@Test
	@DisplayName("Equals consistency")
    public void testCompare() {
		UUID u = new UUID(2,2);
		Snack o = new Snack("big", "taco", "localhost");
		o.setId(u);
		Snack o2 = new Snack("big", "taco", "localhost");
		o2.setId(u);
		assertEquals(o.hashCode(), o2.hashCode());
		assertEquals(o.equals(o2), true);
		assertEquals(o.equals(o), true);
		Snack o3 = new Snack(null, null, null);
		Snack o4 = new Snack(null, null, null);
		assertEquals(o3.hashCode(), o4.hashCode());
		assertEquals(o3.equals(o4), true);
    }
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	@DisplayName("Equals inconsistency fail")
    public void testCompareFail() {
		Snack o = new Snack("big", "taco", "localhost");
		Snack o2 = new Snack(null, "taco", "localhost");
		Snack o3 = new Snack("big", null, "localhost");
		Snack o4 = new Snack("big", "taco", null);
		Event o5 = null;
		String s = "Test";
		assertEquals(o.equals(o), true);
		assertEquals(o.equals(o2), false);
		assertEquals(o.equals(o3), false);
		assertEquals(o.equals(o4), false);
		assertEquals(o.equals(o5), false);
		assertEquals(o.equals(s), false);
		Snack onull = new Snack("big", "taco", "localhost");
		onull.setId(new UUID(2,2));
		assertEquals(o.equals(onull), false); 
    }
	
}
