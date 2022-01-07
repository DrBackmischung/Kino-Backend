package de.wi2020sebgroup1.cinema.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.wi2020sebgroup1.cinema.configurationObject.UserLoginObject;
import de.wi2020sebgroup1.cinema.configurationObject.UserRegistrationObject;
import de.wi2020sebgroup1.cinema.entities.City;
import de.wi2020sebgroup1.cinema.entities.User;
import de.wi2020sebgroup1.cinema.repositories.CityRepository;
import de.wi2020sebgroup1.cinema.repositories.UserRepository;
import de.wi2020sebgroup1.cinema.services.HTMLService;

@Controller
@RestController
public class AccountController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CityRepository cityRepository;
	
	@Autowired
    private JavaMailSender emailSender;
	
	@Autowired
	HTMLService htmlService;
	
	@PutMapping("/registration")
	public ResponseEntity<Object> register(@RequestBody UserRegistrationObject uro){
		
		if(!(uro.passwordHash.equals(uro.passwordConfirmHash))) {
			return new ResponseEntity<Object>("Incorrect password!", HttpStatus.UNAUTHORIZED);
		}
		
		User toAdd = new User();
		
		if(uro.plz != 0) {
			List<City> citySearch = cityRepository.findByPlz(uro.plz);
			if(citySearch.size() == 0) {
				toAdd.setCity(cityRepository.save(new City(uro.plz, uro.city)));
			} else {
				City c = citySearch.get(0);
				toAdd.setCity(c);
			}
		}
		
		toAdd.setUserName(uro.username);
		toAdd.setEmail(uro.email);
		toAdd.setName(uro.name);
		toAdd.setFirstName(uro.firstName);
		toAdd.setPassword(uro.passwordHash);
		toAdd.setStreet(uro.street);
		toAdd.setNumber(uro.number);

		try {
			System.out.println("Sender: "+emailSender.toString());
			emailSender.send(composeMail(uro.email, "Registration Completed!", "Hi!"));
			System.out.println("Sender: "+emailSender.toString());
//			emailSender.send(composeMail(uro.email, "Registration Completed!", htmlService.read("Registration.html", uro.username)));
		} catch (MailException | MessagingException e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<Object>(userRepository.save(toAdd), HttpStatus.CREATED);
	}
	
	@PutMapping("/login")
	public ResponseEntity<Object> login(HttpServletResponse response, @RequestBody UserLoginObject ulo){
		
		Optional<User> userSearch = userRepository.findByUsername(ulo.username);
		try {
			User u = userSearch.get();
			if(!(u.getPassword().equals(ulo.passwordHash))) {
				return new ResponseEntity<Object>("Incorrect password!", HttpStatus.UNAUTHORIZED);
			}
		} catch(NoSuchElementException e) {
			return new ResponseEntity<Object>("No user for username found!", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Object>(null, HttpStatus.OK);
		
	}
	
	public MimeMessage composeMail(String to, String subject, String body) throws MessagingException {

		MimeMessage mail = emailSender.createMimeMessage();
		System.out.println("PrintStuff");
		System.out.println(mail.toString());
		System.out.println("PrintStuff");
		MimeMessageHelper messageHelper = new MimeMessageHelper(mail, false, "UTF-8");
		System.out.println(messageHelper.toString());
        messageHelper.setFrom(to);
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(body, true);
        System.out.println(mail.toString());
        
        return mail;
        
	}
	
}
