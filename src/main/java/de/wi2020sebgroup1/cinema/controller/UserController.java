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

import de.wi2020sebgroup1.cinema.Entities.UserRepository;
import de.wi2020sebgroup1.cinema.Entities.User;
import de.wi2020sebgroup1.cinema.Entities.Cinema;
import de.wi2020sebgroup1.cinema.Entities.CinemaRepository;
import de.wi2020sebgroup1.cinema.Entities.City;
import de.wi2020sebgroup1.cinema.Entities.CityRepository;

@Controller
@RestController
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CinemaRepository cinemaRepository;
	
	@Autowired
	private CityRepository cityRepo;
	

	
	
	@GetMapping("/users")
	public ResponseEntity<Iterable<User>> getUsers(){
		return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
	}
	
	@PutMapping("/user")
	public ResponseEntity<User> addUser(@RequestBody User newUser){
		return new ResponseEntity<>( userRepository.save(newUser), HttpStatus.OK);
	}

	
	@PutMapping("/cinema")
	public ResponseEntity<Cinema> addCinema(@RequestBody Cinema newCinema){
		return new ResponseEntity<>( cinemaRepository.save(newCinema), HttpStatus.OK);
	}
	
	@GetMapping("/cinemas")
	public ResponseEntity<Iterable<Cinema>> getCinemas(){
		return new ResponseEntity<>(cinemaRepository.findAll(),HttpStatus.OK);
	}
	
	@GetMapping("/cities")
	public ResponseEntity<Iterable<City>> getCities(){
		return new ResponseEntity<>(cityRepo.findAll(),HttpStatus.OK);
	}
	

}
