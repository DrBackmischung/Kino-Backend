package de.wi2020sebgroup1.cinema.controller;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.wi2020sebgroup1.cinema.configurationObject.UserRegistrationObject;
import de.wi2020sebgroup1.cinema.entities.City;
import de.wi2020sebgroup1.cinema.entities.User;
import de.wi2020sebgroup1.cinema.exceptions.CityNotFoundException;
import de.wi2020sebgroup1.cinema.repositories.CityRepository;
import de.wi2020sebgroup1.cinema.repositories.UserRepository;

@Controller
@RestController
public class RegistrationController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CityRepository cityRepository;
	
	@PutMapping("/registration")
	public ResponseEntity<Object> register(@RequestBody UserRegistrationObject uro){
		
		if(uro.passwordHash != uro.passwordConfirmHash) {
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
		
		return new ResponseEntity<Object>(userRepository.save(toAdd), HttpStatus.CREATED);
	}
	
}
