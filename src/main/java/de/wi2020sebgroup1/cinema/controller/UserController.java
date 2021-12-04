package de.wi2020sebgroup1.cinema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
import de.wi2020sebgroup1.cinema.repositories.CinemaRepository;
import de.wi2020sebgroup1.cinema.repositories.CityRepository;
import de.wi2020sebgroup1.cinema.repositories.UserRepository;

@Controller
@RestController
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
		
	@GetMapping("/users")
	public ResponseEntity<Iterable<User>> getUsers(){
		return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
	}
	
	@PutMapping("/user")
	public ResponseEntity<User> addUser(@RequestBody User newUser){
		return new ResponseEntity<>( userRepository.save(newUser), HttpStatus.CREATED);
	}
	

}
