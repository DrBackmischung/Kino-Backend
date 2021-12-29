package de.wi2020sebgroup1.cinema.controller;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.wi2020sebgroup1.cinema.configurationObject.TokenConfigurationObject;
import de.wi2020sebgroup1.cinema.entities.Token;
import de.wi2020sebgroup1.cinema.entities.User;
import de.wi2020sebgroup1.cinema.exceptions.UserNotFoundException;
import de.wi2020sebgroup1.cinema.helper.Response;
import de.wi2020sebgroup1.cinema.repositories.TokenRepository;
import de.wi2020sebgroup1.cinema.repositories.UserRepository;

@Controller
@RestController
public class TokenController {
	
	@Autowired
	TokenRepository repo;
	
	@Autowired
	UserRepository userRepository;
	
	@PutMapping("/create")
	public ResponseEntity<Object> add(@RequestBody TokenConfigurationObject tco){
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
		return new ResponseEntity<Object>(repo.save(t), Response.CREATED.status());
	}
	
	/*
	 * token/create
	 * token/{id}/verify
	 */
	
}
