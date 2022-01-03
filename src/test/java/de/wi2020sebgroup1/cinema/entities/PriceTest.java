package de.wi2020sebgroup1.cinema.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.wi2020sebgroup1.cinema.enums.SeatType;

public class PriceTest {
	
	@Test
	@DisplayName("Test the constructor")
    public void testConstructor() {
		Price o = new Price(5.5, SeatType.PARQUET);
		assertEquals(o.getPrice(), 5.5);
    }
	
	@Test
	@DisplayName("Test Getter/Setter")
    public void testGetterSetter() {
		Price o = new Price(5.5, SeatType.PARQUET);
		o.setPrice(2);
		assertEquals(o.getPrice(), 2);
    }
	
	@Test
	@DisplayName("Equals consistency")
    public void testCompare() {
		Price o = new Price(5.5, SeatType.PARQUET);
		Price o2 = new Price(5.5, SeatType.PARQUET);
		assertEquals(o.hashCode(), o2.hashCode());
		assertEquals(o.equals(o2), true);
		Price o3 = new Price(5.5, SeatType.PARQUET);
		Price o4 = new Price(5.5, SeatType.PARQUET);
		assertEquals(o3.hashCode(), o4.hashCode());
		assertEquals(o3.equals(o4), true);
    }
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	@DisplayName("Equals inconsistency fail")
    public void testCompareFail() {
		Price o = new Price(5.5, SeatType.PARQUET);
		Price o2 = new Price(2, SeatType.PARQUET);
		Price o3 = null;
		String s = "Test";
		assertEquals(o.equals(o2), false);
		assertEquals(o.equals(o3), false);
		assertEquals(o.equals(s), false);
		Price onull = new Price(5.5);
		onull.setId(new UUID(2,2));
		assertEquals(o.equals(onull), false);
    }
	
}
