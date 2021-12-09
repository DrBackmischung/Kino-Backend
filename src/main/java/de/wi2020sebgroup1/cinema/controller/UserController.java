package de.wi2020sebgroup1.cinema.controller;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.wi2020sebgroup1.cinema.entities.Cinema;
import de.wi2020sebgroup1.cinema.entities.City;
import de.wi2020sebgroup1.cinema.entities.User;
import de.wi2020sebgroup1.cinema.exceptions.CinemaNotFoundException;
import de.wi2020sebgroup1.cinema.exceptions.UserNotFoundException;
import de.wi2020sebgroup1.cinema.repositories.CinemaRepository;
import de.wi2020sebgroup1.cinema.repositories.CityRepository;
import de.wi2020sebgroup1.cinema.repositories.UserRepository;

@Controller
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
		
	@GetMapping("/getAll")
	public ResponseEntity<Iterable<User>> getUsers(){
		return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getUser(@PathVariable UUID id){
		Optional<User> search = userRepository.findById(id);
		try {
			User found = search.get();
			return new ResponseEntity<>(found, HttpStatus.FOUND);
		}catch(NoSuchElementException e) {
			return new ResponseEntity<Object>(new UserNotFoundException(id).getMessage(), HttpStatus.NOT_FOUND);
		}
		
	}
	
}
