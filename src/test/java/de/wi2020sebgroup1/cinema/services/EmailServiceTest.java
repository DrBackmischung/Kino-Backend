package de.wi2020sebgroup1.cinema.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class EmailServiceTest {

	@Autowired
    EmailService emailService;
	
	@Test
	void testPrepareMessage() {
		
    	Properties properties = new Properties();
	    properties.put("mail.smtp.auth",  "true");
	    properties.put("mail.smtp.starttls.enable", "true");
	    properties.put("mail.smtp.host", "smtp.gmail.com");
	    properties.put("mail.smtp.port", "587");
	    Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("wwi2020seb@gmail.com", "Kino2020SEB");
            }
        });
		assertDoesNotThrow(new Executable() {
            @Override
            public void execute() {
            	emailService.prepareMessage(session, "wwi2020seb@gmail.com", "mathis.neunzig@gmail.com", "Registration completed!", "DrBackmischung", "Registration.html");          
            }
        });
    	
	}
	
	@Test
	void testSendMessage() {
		assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Exception {
            	emailService.sendMail("wwi2020seb@gmail.com", "Test :3", "DrBackmischung", "Registration.html");
            }
        });
		
	}
	
}
