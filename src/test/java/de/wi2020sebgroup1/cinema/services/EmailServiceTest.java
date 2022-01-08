package de.wi2020sebgroup1.cinema.services;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class EmailServiceTest {
	
	@MockBean
	HTMLService htmlService;

    @MockBean
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
    	when(emailService.prepareMessage(session, "wwi2020seb@gmail.com", "mathis.neunzig@gmail.com", "Registration completed!", "DrBackmischung", "Registration.html")).thenReturn(new MimeMessage(session));
    	emailService.prepareMessage(null, null, null, null, null, null);
    	
	}
	
	@Test
	void testSendMessage() {
		
		try {
			doNothing().when(emailService).sendMail(isA(String.class), isA(String.class), isA(String.class), isA(String.class));
			emailService.sendMail("mathis.neunzig@gmail.com", "Registration completed!", "DrBackmischung", "Registration.html");
			verify(emailService, times(1)).sendMail("mathis.neunzig@gmail.com", "Registration completed!", "DrBackmischung", "Registration.html");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
