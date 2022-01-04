package de.wi2020sebgroup1.cinema.controller;

import java.util.NoSuchElementException;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.wi2020sebgroup1.cinema.configurationObject.UserLoginObject;
import de.wi2020sebgroup1.cinema.configurationObject.UserRegistrationObject;
import de.wi2020sebgroup1.cinema.entities.City;
import de.wi2020sebgroup1.cinema.entities.User;
import de.wi2020sebgroup1.cinema.exceptions.CityNotFoundException;
import de.wi2020sebgroup1.cinema.helper.UserVerificator;
import de.wi2020sebgroup1.cinema.repositories.CityRepository;
import de.wi2020sebgroup1.cinema.repositories.UserRepository;
import de.wi2020sebgroup1.cinema.services.EmailService;

@Controller
@RestController
public class AccountController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CityRepository cityRepository;
	
	@Autowired
    private JavaMailSender emailSender;
	
	@PutMapping("/registration")
	public ResponseEntity<Object> register(@RequestBody UserRegistrationObject uro){
		
		if(!(uro.passwordHash.equals(uro.passwordConfirmHash))) {
			return new ResponseEntity<Object>("Incorrect password!", HttpStatus.NOT_ACCEPTABLE);
		}
		
		User toAdd = new User();
		
		if(uro.cityID != null) {
			Optional<City> citySearch = cityRepository.findById(uro.cityID);
			try {
				City c = citySearch.get();
				toAdd.setCity(c);
			} catch(NoSuchElementException e) {
				return new ResponseEntity<Object>(new CityNotFoundException(uro.cityID).getMessage(), HttpStatus.NOT_FOUND);
			}
		}
		
		toAdd.setUserName(uro.username);
		toAdd.setEmail(uro.email);
		toAdd.setName(uro.name);
		toAdd.setFirstName(uro.firstName);
		toAdd.setPassword(uro.passwordHash);
		toAdd.setStreet(uro.street);
		toAdd.setNumber(uro.number);
		
		emailSender.send(EmailService.composeMail(uro.email, "Registration completed!", "Welcome "+uro.username+" to the Cinema."));
		
		return new ResponseEntity<Object>(userRepository.save(toAdd), HttpStatus.CREATED);
	}
	
	@PutMapping("/login")
	public ResponseEntity<Object> login(HttpServletResponse response, @RequestBody UserLoginObject ulo){
		
		Optional<User> userSearch = userRepository.findByUsername(ulo.username);
		try {
			User u = userSearch.get();
			if(!(u.getPassword().equals(ulo.passwordHash))) {
				return new ResponseEntity<Object>("Wrong password!", HttpStatus.UNAUTHORIZED);
			}
				
			
			/*
			 * Im Cookie wird einmal die User ID gespeichert und ein Key.
			 * Der Key ist nur der HashCode +1, damit man nicht einfach so die userID verändern
			 * kann,, um sich als Admin auszugeben.
			 */
			
			Cookie c = new Cookie("userID", u.getId().toString());
			response.addCookie(c);
			Cookie c2 = new Cookie("key", ""+u.getId().hashCode()+UserVerificator.KEY_OFFSET);
			response.addCookie(c2);
		} catch(NoSuchElementException e) {
			return new ResponseEntity<Object>("No user for username found!", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Object>(null, HttpStatus.OK);
		
	}
	
}
