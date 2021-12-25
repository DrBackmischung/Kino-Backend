package de.wi2020sebgroup1.cinema.services;

import org.springframework.mail.SimpleMailMessage;

public class EmailService {
	
	public final static String EMAIL = "wwi2020seb@gmail.com";
	
	public static void send(String to, String subject, String body) {

        final SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(to);
        email.setFrom(EMAIL);
        
	}
	
	public static void send(String to, String subject, String body, String attachedFilePath) {

		// handle attached filed
		
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(to);
        email.setFrom(EMAIL);
        
	}
	
}
