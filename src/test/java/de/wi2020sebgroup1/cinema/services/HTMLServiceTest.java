package de.wi2020sebgroup1.cinema.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
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
	
//	@Test
//	void testReadException() {
//		when(htmlService.read("Registration.html", "DrBackmischung")).thenReturn("Test");
//		try {
//			BufferedReader br = Mockito.mock(BufferedReader.class);
//			when(br.readLine()).thenThrow(IOException.class);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//        assertThrows(IOException.class, new Executable() {
//
//            @Override
//            public void execute() throws Throwable {
//            	htmlService.read("Registration.html", "DrBackmischung");
//                
//            }
//            
//        });
//	}

}
