package de.wi2020sebgroup1.cinema.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PriceTest {
	
	@Test
	@DisplayName("Test the constructor")
    public void testConstructor() {
		Price o = new Price(5.5);
		assertEquals(o.getPrice(), 5.5);
    }
	
	@Test
	@DisplayName("Test Getter/Setter")
    public void testGetterSetter() {
		Price o = new Price(5.5);
		o.setPrice(2);
		assertEquals(o.getPrice(), 2);
    }
	
	@Test
	@DisplayName("Equals consistency")
    public void testCompare() {
		Price o = new Price(5.5);
		Price o2 = new Price(5.5);
		assertEquals(o.hashCode(), o2.hashCode());
		assertEquals(o.equals(o2), true);
    }
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	@DisplayName("Equals inconsistency fail")
    public void testCompareFail() {
		Price o = new Price(5.5);
		Price o2 = new Price(2);
		Price o3 = null;
		String s = "Test";
		assertEquals(o.equals(o2), false);
		assertEquals(o.equals(o3), false);
		assertEquals(o.equals(s), false);
    }
	
}
