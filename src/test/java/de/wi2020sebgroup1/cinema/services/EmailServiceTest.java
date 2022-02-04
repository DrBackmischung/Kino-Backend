package de.wi2020sebgroup1.cinema.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import de.wi2020sebgroup1.cinema.configurationObject.EmailVariablesObject;
import de.wi2020sebgroup1.cinema.entities.Price;
import de.wi2020sebgroup1.cinema.entities.Seat;
import de.wi2020sebgroup1.cinema.entities.Snack;
import de.wi2020sebgroup1.cinema.entities.Ticket;
import de.wi2020sebgroup1.cinema.enums.SeatState;
import de.wi2020sebgroup1.cinema.enums.SeatType;
import de.wi2020sebgroup1.cinema.enums.TicketState;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class EmailServiceTest {

	@Autowired
    EmailService emailService;

	@MockBean
	QRCodeGenerator qrCodeGenerator;
	
	private List<Ticket> getTickets() {
		List<Ticket> list = new ArrayList<>();
		list.add(new Ticket(TicketState.PAID, null, null, new Price(3, SeatType.PARQUET), new Seat(0, 0, SeatType.PARQUET, SeatState.PAID, 0, null, null), null));
		list.add(new Ticket(TicketState.PAID, null, null, new Price(3, SeatType.LODGE), new Seat(0, 0, SeatType.LODGE, SeatState.PAID, 0, null, null), null));
		list.add(new Ticket(TicketState.PAID, null, null, new Price(3, SeatType.WHEELCHAIR), new Seat(0, 0, SeatType.WHEELCHAIR, SeatState.PAID, 0, null, null), null));
		list.add(new Ticket(TicketState.PAID, null, null, new Price(3, SeatType.DOUBLESEAT), new Seat(0, 0, SeatType.DOUBLESEAT, SeatState.PAID, 0, null, null), null));
		list.add(new Ticket(TicketState.PAID, null, null, new Price(3, SeatType.PREMIUM), new Seat(0, 0, SeatType.PREMIUM, SeatState.PAID, 0, null, null), null));
		return list;
	}
	
	private List<Snack> getSnacks() {
		List<Snack> list = new ArrayList<>();
		list.add(new Snack("1", "Cola", "localhost", 3.5));
		list.add(new Snack("1", "Fanta", "localhost", 3.5));
		return list;
	}
	
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
	
//	@Test
//	void testPrepareMessageException() {
//		
//    	Properties properties = new Properties();
//	    properties.put("mail.smtp.auth",  "true");
//	    properties.put("mail.smtp.starttls.enable", "true");
//	    properties.put("mail.smtp.host", "smtp.gmail.com");
//	    properties.put("mail.smtp.port", "587");
//	    Session session = Session.getInstance(properties, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication("wwi2020seb@gmail.com", "Kino2020SEB");
//            }
//        });
//		assertDoesNotThrow(new Executable() {
//            @Override
//            public void execute() {
//        	    EmailVariablesObject e = new EmailVariablesObject("DrBackmischung", "Mathis", "Neunzig", "", "", "", "", "", "", "", "");
//            	emailService.prepareMessage(session, "wwi2020seb@gmail.com", "mathisSeineMail", "Registration completed!", e, "Registration.html");          
//            }
//        });
//    	
//	}
	
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
            	emailService.prepareMessage(session, "wwi2020seb@gmail.com", null, "Registration completed!", e, "Registration.html");          
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
	
//	@Test
//	void testSendMessageException() {
//		assertDoesNotThrow(new Executable() {
//            @Override
//            public void execute() throws Exception {
//        	    EmailVariablesObject e = new EmailVariablesObject("DrBackmischung", "Mathis", "Neunzig", "", "", "", "", "", "", "", "");
//            	emailService.sendMail("wwi2020seb@yes", "Test :3", e, "Registration.html");
//            }
//        });
//		
//	}
	
	@Test
	void testSendMessageException() {
		assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Exception {
        	    EmailVariablesObject e = new EmailVariablesObject("DrBackmischung", "Mathis", "Neunzig", "", "", "", "", "", "", "", "");
            	emailService.sendMail(null, "Test :3", e, "Registration.html");
            }
        });
		
	}
	
	@Test
	void testSendMessageBooking() {
		assertDoesNotThrow(new Executable() {
            @SuppressWarnings("static-access")
			@Override
            public void execute() throws Exception {
        	    EmailVariablesObject e = new EmailVariablesObject("DrBackmischung", "Mathis", "Neunzig", "", "", "", "", "", "", "", "");
            	emailService.sendMailBooking("wwi2020seb@gmail.com", "Test :3", e, "Registration.html", qrCodeGenerator.generateQRCode("Test"), getTickets(), getSnacks());
            }
        });
		
	}
	
	@Test
	void testSendMessageBookingNull() {
		assertDoesNotThrow(new Executable() {
            @SuppressWarnings("static-access")
			@Override
            public void execute() throws Exception {
        	    EmailVariablesObject e = new EmailVariablesObject("DrBackmischung", "Mathis", "Neunzig", "", "", "", "", "", "", "", "");
            	emailService.sendMailBooking("wwi2020seb@gmail.com", "Test :3", e, "Registration.html", qrCodeGenerator.generateQRCode("Test"), getTickets(), null);
            }
        });
		
	}
	
	@Test
	void testSendMessageBookingException() {
		assertDoesNotThrow(new Executable() {
            @SuppressWarnings("static-access")
			@Override
            public void execute() throws Exception {
        	    EmailVariablesObject e = new EmailVariablesObject("DrBackmischung", "Mathis", "Neunzig", "", "", "", "", "", "", "", "");
            	emailService.sendMailBooking(null, "Test :3", e, "Registration.html", qrCodeGenerator.generateQRCode("Test"), getTickets(), getSnacks());
            }
        });
		
	}
	
	@Test
	void testPrepareBookingMessageException() {
		
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
            @SuppressWarnings("static-access")
			@Override
            public void execute() throws Exception {
        	    EmailVariablesObject e = new EmailVariablesObject("DrBackmischung", "Mathis", "Neunzig", "", "", "", "", "", "", "", "");
            	emailService.prepareMessageWithAttachment(session, "wwi2020seb@gmail.com", null, "Registration completed!", e, "Registration.html", emailService.createDocument(e, qrCodeGenerator.generateQRCode("Test"), getTickets(), getSnacks()));         
            }
        });
    	
	}
	
}
