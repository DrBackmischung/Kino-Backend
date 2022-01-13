package de.wi2020sebgroup1.cinema.services;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.wi2020sebgroup1.cinema.configurationObject.EmailVariablesObject;

@Service
public class EmailService {
	
	@Autowired
	HTMLService htmlService;
	
	public final static String EMAIL = "wwi2020seb@gmail.com";
	
	public Message prepareMessage(Session session, String acc, String to, String subject, EmailVariablesObject evo, String file){
        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(acc));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            Multipart multipart = new MimeMultipart();
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("");
            multipart.addBodyPart(messageBodyPart);
            message.setContent(htmlService.read(file, evo), "text/html");

            return message;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void sendMail(String to, String subject, EmailVariablesObject evo, String file) throws Exception{

        Properties properties = new Properties();
        properties.put("mail.smtp.auth",  "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL, "Kino2020SEB");
            }
        });

        Message message = prepareMessage(session, EMAIL, to, subject, evo, file);
        Transport.send(message);
    }
	
}
