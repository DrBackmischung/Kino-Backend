package de.wi2020sebgroup1.cinema.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class HTMLServiceTest {
	
	@MockBean
	HTMLService htmlService;
	
	@Test
	void testRead() {
		when(htmlService.read("Registration.html", "DrBackmischung")).thenReturn("Test");
		assertEquals("Test", htmlService.read("Registration.html", "DrBackmischung"));
	}

}
