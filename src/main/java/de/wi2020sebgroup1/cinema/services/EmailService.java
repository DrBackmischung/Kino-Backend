package de.wi2020sebgroup1.cinema.services;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.imageio.ImageIO;
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
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.wi2020sebgroup1.cinema.configurationObject.EmailVariablesObject;
import de.wi2020sebgroup1.cinema.entities.Seat;
import de.wi2020sebgroup1.cinema.entities.Ticket;
import de.wi2020sebgroup1.cinema.enums.SeatType;
import rst.pdfbox.layout.elements.Document;
import rst.pdfbox.layout.elements.ImageElement;
import rst.pdfbox.layout.elements.Paragraph;
import rst.pdfbox.layout.elements.VerticalSpacer;
import rst.pdfbox.layout.elements.render.VerticalLayoutHint;
import rst.pdfbox.layout.text.Alignment;
import rst.pdfbox.layout.text.BaseFont;
import rst.pdfbox.layout.text.Position;

@Service
public class EmailService {
	
	@Autowired
	HTMLService htmlService;
	
	public final static String EMAIL = "wwi2020seb@gmail.com";
	
	public Message prepareMessage(Session session, String acc, String to, String subject, EmailVariablesObject evo, String file){
        try {
        	to.trim();
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
	
	public Message prepareMessageWithAttachment(Session session, String acc, String to, String subject, EmailVariablesObject evo, String file, ByteArrayInputStream stream){
        try {
        	to.trim();
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(acc));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            Multipart multipart = new MimeMultipart();
            MimeBodyPart msg = new MimeBodyPart();
            MimeBodyPart pdf = new MimeBodyPart();
            DataSource dataSource = new ByteArrayDataSource(stream, "application/pdf");
            pdf.setDataHandler(new DataHandler(dataSource));
            pdf.setFileName("buchung.pdf");
            msg.setContent(htmlService.read(file, evo), "text/html");
            multipart.addBodyPart(msg);
            multipart.addBodyPart(pdf);
            message.setContent(multipart);

            return message;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean sendMail(String to, String subject, EmailVariablesObject evo, String file) {
        try {

        	to.trim();
            Properties properties = new Properties();
            properties.put("mail.smtp.auth",  "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587"); 

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(EMAIL, "MarslStinktNachMaggi");
                }
            });

            Message message = prepareMessage(session, EMAIL, to, subject, evo, file);
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
        return true;
    }

    public boolean sendMailBooking(String to, String subject, EmailVariablesObject evo, String file, byte[] qrcode, List<Ticket> tickets) {
		try {

	    	to.trim();
	        Properties properties = new Properties();
	        properties.put("mail.smtp.auth",  "true");
	        properties.put("mail.smtp.starttls.enable", "true");
	        properties.put("mail.smtp.host", "smtp.gmail.com");
	        properties.put("mail.smtp.port", "587");

	        Session session = Session.getInstance(properties, new Authenticator() {
	            @Override
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(EMAIL, "MarslStinktNachMaggi");
	            }
	        });

	        Message message;
			message = prepareMessageWithAttachment(session, EMAIL, to, subject, evo, file, createDocument(evo, qrcode, tickets));
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
    }
    
    public ByteArrayInputStream createDocument(EmailVariablesObject evo, byte[] qrcode, List<Ticket> tickets) throws IOException {
    	
    	float hMargin = 40;
    	float vMargin = 50;
    	Document document = new Document(hMargin, hMargin, vMargin, vMargin);
    	
    	ImageElement image;
    	
    	File f = new File("qr.jpg");
		ByteArrayInputStream bis = new ByteArrayInputStream(qrcode);
	    BufferedImage i = ImageIO.read(bis);
	    ImageIO.write(i, "jpg", f);
	    
	    image = new ImageElement("qr.jpg");
    	
    	image.setWidth(image.getWidth()/6);
    	image.setHeight(image.getHeight()/6);
    	document.add(image, new VerticalLayoutHint(Alignment.Right, 0, 0,
    		0, 0, true));
	    
    	File f2 = new File("logo.png");
	    BufferedImage i2 = ImageIO.read(new URL("https://raw.githubusercontent.com/DrBackmischung/Kino-Dokumentation/main/Kinovation.png"));
	    ImageIO.write(i2, "png", f2);
    	
	    image = new ImageElement("logo.png");
    	
    	image.setWidth(image.getWidth()/4);
    	image.setHeight(image.getHeight()/4);
    	document.add(image, new VerticalLayoutHint(Alignment.Left, 0, 0,
    		0, 0, true));
    	
    	document.add(new VerticalSpacer(120));

    	Paragraph paragraph = new Paragraph();

    	paragraph = new Paragraph();
    	paragraph.addMarkup("*Buchung für "+evo.getShowTitle()+" am "+evo.getShowDate()+" um "+evo.getShowTime()+"*\nRaum: "+evo.getString1(), 15,
    		BaseFont.Helvetica);
    	document.add(paragraph, new VerticalLayoutHint(Alignment.Left, 0, 0,
    		40, 20));

    	for(Ticket t : tickets) {
    		
    		int row = t.getSeat().getReihe();
    		int nr = t.getSeat().getPlace();
    		String type = convertSeatType(t.getSeat());
    		double price = t.getPrice().getPrice();
    		String text = "Reihe "+row+", Sitz "+nr+" - Kategorie: "+type+"\nPreis: "+price+"€\n\n";
        	paragraph = new Paragraph();
        	paragraph.addMarkup(text, 14, BaseFont.Helvetica);
        	document.add(paragraph);
    	}

    	paragraph = new Paragraph();
    	paragraph.addMarkup("Die AGBs sind auf der Website unter localhost:8080 zu sehen. Bitte besuchen Sie localhost:3000 für weitere Informationen. Für eine Widerufserklärung besuchen Sie bitte localhost:5999.", 6,
    		BaseFont.Times);
    	paragraph.setAbsolutePosition(new Position(hMargin, vMargin));
    	document.add(paragraph);

        ByteArrayInputStream in;
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
        document.save(out);
        in = new ByteArrayInputStream(out.toByteArray());
        
        return in;
    	
    }
    
    public String convertSeatType(Seat s) {
    	if(s.getType() == SeatType.LODGE) return "Loge";
    	else if(s.getType() == SeatType.PREMIUM) return "Premium";
    	else if(s.getType() == SeatType.DOUBLESEAT) return "Sofa / Doppelsitz";
    	else if(s.getType() == SeatType.WHEELCHAIR) return "Rollstuhlplatz";
    	else return "Parkett";
    }
	
}
