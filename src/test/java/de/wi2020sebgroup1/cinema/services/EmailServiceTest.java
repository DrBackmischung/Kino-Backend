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

import de.wi2020sebgroup1.cinema.configurationObject.EmailVariablesObject;

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
            public void execute() throws Exception {
        	    EmailVariablesObject e = new EmailVariablesObject("DrBackmischung", "Mathis", "Neunzig", "", "", "", "", "", "", "", "");
            	emailService.prepareMessage(session, "wwi2020seb@gmail.com", "mathis.neunzig@gmail.com", "Registration completed!", e, "Registration.html");          
            }
        });
    	
	}
	
	@Test
	void testPrepareMessageException() {
		
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
        	    EmailVariablesObject e = new EmailVariablesObject("DrBackmischung", "Mathis", "Neunzig", "", "", "", "", "", "", "", "");
            	emailService.prepareMessage(session, "wwi2020seb@gmail.com", "mathisSeineMail", "Registration completed!", e, "Registration.html");          
            }
        });
    	
	}
	
	@Test
	void testSendMessage() {
		assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Exception {
        	    EmailVariablesObject e = new EmailVariablesObject("DrBackmischung", "Mathis", "Neunzig", "", "", "", "", "", "", "", "");
            	emailService.sendMail("wwi2020seb@gmail.com", "Test :3", e, "Registration.html");
            }
        });
		
	}
	
	@Test
	void testSendMessageException() {
		assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Exception {
        	    EmailVariablesObject e = new EmailVariablesObject("DrBackmischung", "Mathis", "Neunzig", "", "", "", "", "", "", "", "");
            	emailService.sendMail("wwi2020seb@yes", "Test :3", e, "Registration.html");
            }
        });
		
	}
	
}
