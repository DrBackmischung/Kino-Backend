package de.wi2020sebgroup1.cinema;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CinemaApplicationTests {

	@Test
	void contextLoads() {
		int i = 5;
		int j = 5;
		assertEquals(i,  j);
	}

}
