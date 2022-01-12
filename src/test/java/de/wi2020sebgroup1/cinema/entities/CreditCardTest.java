package de.wi2020sebgroup1.cinema.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CreditCardTest {
	
	@Test
	@DisplayName("Test the constructor")
    public void testConstructor() {
		CreditCard o = new CreditCard("1234", "Neunzig", "Mathis", 2026, 7);
        assertEquals(o.getCardNumber(), "1234");
        assertEquals(o.getName(), "Neunzig");
        assertEquals(o.getFirstName(), "Mathis");
        assertEquals(o.getExpiryYear(), 2026);
        assertEquals(o.getExpiryMonth(), 7);
    }
	
	@Test
	@DisplayName("Test Getter/Setter")
    public void testGetterSetter() {
		CreditCard o = new CreditCard("1234", "Neunzig", "Mathis", 2026, 7);
		o.setCardNumber("nr");
        assertEquals(o.getCardNumber(), "nr");
        o.setName("Neunzig2");
        assertEquals(o.getName(), "Neunzig2");
        o.setFirstName("Mathis2");
        assertEquals(o.getFirstName(), "Mathis2");
        o.setExpiryYear(2000);
        assertEquals(o.getExpiryYear(), 2000);
        o.setExpiryMonth(9);
        assertEquals(o.getExpiryMonth(), 9);
    }
	
	@Test
	@DisplayName("Equals consistency")
    public void testCompare() {
		UUID u = new UUID(2,2);
		CreditCard o = new CreditCard("1234", "Neunzig", "Mathis", 2026, 7);
		o.setId(u);
		CreditCard o2 = new CreditCard("1234", "Neunzig", "Mathis", 2026, 7);
		o2.setId(u);
		assertEquals(o.hashCode(), o2.hashCode());
		assertEquals(o.equals(o2), true);
		CreditCard o3 = new CreditCard("1234", "Neunzig", "Mathis", 2026, 7);
		CreditCard o4 = new CreditCard("1234", "Neunzig", "Mathis", 2026, 7);
		assertEquals(o3.hashCode(), o4.hashCode());
		assertEquals(o3.equals(o4), true);
    }
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	@DisplayName("Equals inconsistency fail")
    public void testCompareFail() {
		CreditCard o = new CreditCard("1234", "Neunzig", "Mathis", 2026, 7);
		CreditCard o2 = new CreditCard("12345", "Neunzig", "Mathis", 2026, 7);
		CreditCard o3 = new CreditCard("1234", "Neunzig2", "Mathis", 2026, 7);
		CreditCard o4 = new CreditCard("1234", "Neunzig", "Mathis2", 2026, 7);
		CreditCard o5 = new CreditCard("1234", "Neunzig", "Mathis", 20221, 7);
		CreditCard o6 = new CreditCard("1234", "Neunzig", "Mathis", 2026, 131);
		CreditCard o7 = null;
		String s = "Test";
		assertEquals(o.equals(o2), false);
		assertEquals(o.equals(o3), false);
		assertEquals(o.equals(o4), false);
		assertEquals(o.equals(o5), false);
		assertEquals(o.equals(o6), false);
		assertEquals(o.equals(o7), false);
		assertEquals(o.equals(s), false);
		CreditCard onull = new CreditCard("1234", "Neunzig", "Mathis", 2026, 7);
		onull.setId(new UUID(2,2));
		assertEquals(o.equals(onull), false);
    }
	
}
