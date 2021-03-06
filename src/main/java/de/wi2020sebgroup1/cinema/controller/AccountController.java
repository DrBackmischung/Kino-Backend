package de.wi2020sebgroup1.cinema.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.wi2020sebgroup1.cinema.configurationObject.EmailVariablesObject;
import de.wi2020sebgroup1.cinema.configurationObject.UserLoginObject;
import de.wi2020sebgroup1.cinema.configurationObject.UserRegistrationObject;
import de.wi2020sebgroup1.cinema.entities.City;
import de.wi2020sebgroup1.cinema.entities.User;
import de.wi2020sebgroup1.cinema.exceptions.RoleNotFoundException;
import de.wi2020sebgroup1.cinema.exceptions.UserAlreadyExistsException;
import de.wi2020sebgroup1.cinema.repositories.CityRepository;
import de.wi2020sebgroup1.cinema.repositories.RoleRepository;
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
	RoleRepository roleRepository;
	
	@Autowired
	EmailService emailService;
	
	@PutMapping("/registration")
	public ResponseEntity<Object> register(@RequestBody UserRegistrationObject uro){
		
		boolean alreadyExists = false;
		
		if(!(uro.passwordHash.equals(uro.passwordConfirmHash))) {
			return new ResponseEntity<Object>("Incorrect password!", HttpStatus.UNAUTHORIZED);
		}
		
		User user = userRepository.findByUsernameSpecial(uro.username);
		if(user != null) {
			alreadyExists = true;
		}
		
		try {
			user = userRepository.findByEmailEquals(uro.email).get();
			alreadyExists = true;
		}catch(Exception e) {};
		
		if(alreadyExists) {
			return new ResponseEntity<Object>(new UserAlreadyExistsException(uro.email, uro.username), HttpStatus.NOT_ACCEPTABLE);
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
			toAdd.setRole(roleRepository.findByAuthorization("USER").get());
		} catch(NoSuchElementException e) {
			return new ResponseEntity<Object>(new RoleNotFoundException("USER"), HttpStatus.NOT_FOUND);
		}

		emailService.sendMail(uro.email, "Registration completed!", new EmailVariablesObject(uro.username, uro.firstName, uro.name, "", "", "", "", "", "", "", ""), "Registration.html");
		
		return new ResponseEntity<Object>(userRepository.save(toAdd), HttpStatus.CREATED);
	}
	
	@PutMapping("/login")
	public ResponseEntity<Object> login(HttpServletResponse response, @RequestBody UserLoginObject ulo){
		
		Optional<User> userSearch = userRepository.findByUsername(ulo.username);
		User u = null;
		try {
			u = userSearch.get();
			if(!(u.getPassword().equals(ulo.passwordHash))) {
				return new ResponseEntity<Object>("Incorrect password!", HttpStatus.UNAUTHORIZED);
			}
		} catch(NoSuchElementException e) {
			return new ResponseEntity<Object>("No user for username found!", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Object>(u, HttpStatus.OK);
		
	}
	
}
