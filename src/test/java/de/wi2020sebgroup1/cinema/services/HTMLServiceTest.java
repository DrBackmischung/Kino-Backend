package de.wi2020sebgroup1.cinema.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class HTMLServiceTest {
	
	@Autowired
	HTMLService htmlService;
	
	@Test
	void testRead() {
		assertDoesNotThrow(new Executable() {
            @Override
            public void execute() {
            	htmlService.read("Registration.html", "DrBackmischung");          
            }
        });
	}

}
