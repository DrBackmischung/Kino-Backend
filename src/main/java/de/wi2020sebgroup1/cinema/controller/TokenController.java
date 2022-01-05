package de.wi2020sebgroup1.cinema.controller;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
	TokenRepository repo;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
    private JavaMailSender emailSender;
	
	@PutMapping("/reset")
	public ResponseEntity<Object> startReset(@RequestBody TokenConfigurationObject tco){
		Token t = new Token();
		t.setValid(tco.valid);
		if(tco.userID != null) {
			Optional<User> userSearch = userRepository.findById(tco.userID);
			try {
				User u = userSearch.get();
				t.setUser(u);
			} catch (NoSuchElementException e) {
				return new ResponseEntity<Object>(new UserNotFoundException(tco.userID).getMessage(),
						Response.NOT_FOUND.status());
			}
		}
		Token saved = repo.save(t);
		emailSender.send(EmailService.composeMail(saved.getUser().getEmail(), "Reset Password!", "Hi "+saved.getUser().getUserName()+"! Click here to reset the password: "+saved.getId()));
		return new ResponseEntity<Object>(saved, Response.CREATED.status());
	}
	
	@PutMapping("/reset/{tokenID}/{userID}")
	public ResponseEntity<Object> reset(@PathVariable UUID tokenID, @PathVariable UUID userID, @RequestBody PWResetObject pwr){
		Optional<Token> tokenSearch = repo.findById(tokenID);
		try {
			Token t = tokenSearch.get();
			if(t.getUser().getId() != userID) {
				return new ResponseEntity<Object>(new TokenNotApplicableByGivenUserException(tokenID, userID).getMessage(),
						Response.UNAUTHORIZED.status());
			}
			if(!t.isValid()) {
				return new ResponseEntity<Object>(new TokenNotValidException(tokenID).getMessage(),
						Response.UNAUTHORIZED.status());
			}
			t.setValid(false);
			Optional<User> userSearch = userRepository.findById(userID);
			try {
				User u = userSearch.get();
				u.setPassword(pwr.password);
				userRepository.save(u);
			} catch (NoSuchElementException e) {
				return new ResponseEntity<Object>(new UserNotFoundException(userID).getMessage(),
						Response.NOT_FOUND.status());
			}
			return new ResponseEntity<Object>(repo.save(t), Response.CHANGED.status());
		} catch (NoSuchElementException e) {
			return new ResponseEntity<Object>(new TokenNotFoundException(tokenID).getMessage(),
					Response.NOT_FOUND.status());
		}
	}
	
	@GetMapping("/check/{id}")
	public ResponseEntity<Object> check(@PathVariable UUID tokenID){
		Optional<Token> tokenSearch = repo.findById(tokenID);
		try {
			Token t = tokenSearch.get();
			return new ResponseEntity<Object>(t.isValid(), Response.OK.status());
		} catch (NoSuchElementException e) {
			return new ResponseEntity<Object>(new TokenNotFoundException(tokenID).getMessage(), Response.NOT_FOUND.status());
		}
	}
	
}