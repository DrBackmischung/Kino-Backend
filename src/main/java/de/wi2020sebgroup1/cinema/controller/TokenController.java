package de.wi2020sebgroup1.cinema.controller;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.wi2020sebgroup1.cinema.configurationObject.EmailVariablesObject;
import de.wi2020sebgroup1.cinema.configurationObject.PWResetObject;
import de.wi2020sebgroup1.cinema.configurationObject.TokenConfigurationObject;
import de.wi2020sebgroup1.cinema.entities.Token;
import de.wi2020sebgroup1.cinema.entities.User;
import de.wi2020sebgroup1.cinema.exceptions.TokenNotApplicableByGivenUserException;
import de.wi2020sebgroup1.cinema.exceptions.TokenNotFoundException;
import de.wi2020sebgroup1.cinema.exceptions.TokenNotValidException;
import de.wi2020sebgroup1.cinema.exceptions.UserNotFoundException;
import de.wi2020sebgroup1.cinema.helper.Response;
import de.wi2020sebgroup1.cinema.repositories.TokenRepository;
import de.wi2020sebgroup1.cinema.repositories.UserRepository;
import de.wi2020sebgroup1.cinema.services.EmailService;

@Controller
@RestController
public class TokenController {
	
	@Autowired
	TokenRepository tokenRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EmailService emailService;
	
	@PutMapping("/reset")
	public ResponseEntity<Object> startReset(@RequestBody TokenConfigurationObject tco){
		Token t = new Token();
		t.setValid(tco.valid);
		String mail = null, username = null, firstName = null, lastName = null, link = null;
		if(tco.userID != null) {
			Optional<User> userSearch = userRepository.findById(tco.userID);
			try {
				User u = userSearch.get();
				t.setUser(u);
				mail = u.getEmail();
				username = u.getUserName();
				firstName = u.getFirstName();
				lastName = u.getName();
				link = "";
			} catch (NoSuchElementException e) { 
				return new ResponseEntity<Object>(new UserNotFoundException(tco.userID).getMessage(),
						Response.NOT_FOUND.status());
			}
		}
		Token saved = tokenRepository.save(t);
		emailService.sendMail(mail, "Password reset!", new EmailVariablesObject(username, firstName, lastName, link, "", "", "", "", "", "", ""), "PWReset.html");
		
		return new ResponseEntity<Object>(saved, Response.CREATED.status());
	}
	
	@PutMapping("/reset/confirm")
	public ResponseEntity<Object> reset(@RequestBody PWResetObject pwr){
		Optional<Token> tokenSearch = tokenRepository.findById(pwr.tokenID);
		try {
			Token t = tokenSearch.get();
			System.out.println("PrintStuff");
			System.out.println(t.getUser().getId().toString());
			System.out.println(pwr.userID.toString());
			if(!t.getUser().getId().toString().equals(pwr.userID.toString())) {
				return new ResponseEntity<Object>(new TokenNotApplicableByGivenUserException(pwr.tokenID, pwr.userID).getMessage(),
						Response.UNAUTHORIZED.status());
			}
			System.out.println("PrintStuff Valid");
			System.out.println(t.isValid());
			if(!t.isValid()) {
				return new ResponseEntity<Object>(new TokenNotValidException(pwr.tokenID).getMessage(),
						Response.UNAUTHORIZED.status());
			}
			t.setValid(false);
			Optional<User> userSearch = userRepository.findById(pwr.userID);
			try {
				User u = userSearch.get();
				u.setPassword(pwr.password);
				userRepository.save(u);
			} catch (NoSuchElementException e) {
				return new ResponseEntity<Object>(new UserNotFoundException(pwr.userID).getMessage(),
						Response.NOT_FOUND.status());
			}
			return new ResponseEntity<Object>(tokenRepository.save(t), Response.CHANGED.status());
		} catch (NoSuchElementException e) {
			return new ResponseEntity<Object>(new TokenNotFoundException(pwr.tokenID).getMessage(),
					Response.NOT_FOUND.status());
		}
	}
	
	@GetMapping("/check/{id}")
	public ResponseEntity<Object> check(@PathVariable UUID id){
		Optional<Token> tokenSearch = tokenRepository.findById(id);
		try {
			Token t = tokenSearch.get();
			return new ResponseEntity<Object>(t.isValid(), Response.OK.status());
		} catch (NoSuchElementException e) {
			return new ResponseEntity<Object>(new TokenNotFoundException(id).getMessage(), Response.NOT_FOUND.status());
		}
	}
	
}
