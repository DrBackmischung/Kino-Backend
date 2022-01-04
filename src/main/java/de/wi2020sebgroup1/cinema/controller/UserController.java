package de.wi2020sebgroup1.cinema.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import javax.management.relation.RoleNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.wi2020sebgroup1.cinema.configurationObject.UserConfigurationObject;
import de.wi2020sebgroup1.cinema.entities.City;
import de.wi2020sebgroup1.cinema.entities.CreditCard;
import de.wi2020sebgroup1.cinema.entities.Role;
import de.wi2020sebgroup1.cinema.entities.User;
import de.wi2020sebgroup1.cinema.exceptions.MovieNotFoundException;
import de.wi2020sebgroup1.cinema.exceptions.UserNotFoundException;
import de.wi2020sebgroup1.cinema.repositories.CityRepository;
import de.wi2020sebgroup1.cinema.repositories.CreditCardRepository;
import de.wi2020sebgroup1.cinema.repositories.RoleRepository;
import de.wi2020sebgroup1.cinema.repositories.UserRepository;

@Controller
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private CreditCardRepository creditCardRepository;
		
	@GetMapping("/getAll")
	public ResponseEntity<Iterable<User>> getUsers(){
		return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
	}	
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getSpecific(@PathVariable UUID id){
		
		Optional<User> user = userRepository.findById(id);
		
		try {
			User toReturn = user.get();
			return new ResponseEntity<Object>(toReturn, HttpStatus.OK);
		}
		catch(NoSuchElementException e) {
			return new ResponseEntity<Object>(new UserNotFoundException(id), HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PutMapping("/add")
	public ResponseEntity<User> addUser(@RequestBody User newUser){
		return new ResponseEntity<>( userRepository.save(newUser), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> update(@PathVariable UUID id, @RequestBody UserConfigurationObject uco){
		
		Optional<User> toUpdate = userRepository.findById(id);
		User u = new User();
		
		try {
			UUID currentUser = toUpdate.get().getId();
			u.setId(currentUser);
			u.setUserName(uco.username);
			u.setPassword(uco.password);
			u.setEmail(uco.email);
			u.setFirstName(uco.firstName);
			u.setName(uco.name);
			u.setNumber(uco.number);
			u.setPayPalMail(uco.payPalMail);
			u.setStreet(uco.street);
			
			if(uco.plz != 0) {
				List<City> citySearch = cityRepository.findByPlz(uco.plz);
				if(citySearch.size() == 0) {
					u.setCity(cityRepository.save(new City(uco.plz, uco.city)));
				} else {
					City c = citySearch.get(0);
					u.setCity(c);
				}
			}
			
			if(uco.roleID != null) {
				Optional<Role> roleSearch = roleRepository.findById(uco.roleID);
				try {
					Role role = roleSearch.get();
					u.setRole(role);
				}
				catch(NoSuchElementException e) {
					return new ResponseEntity<Object>(new RoleNotFoundException(uco.roleID.toString()).getMessage(), HttpStatus.NOT_FOUND);
				}
			}
			
			if(uco.creditCardID != null) {
				Optional<CreditCard> creditCardSearch = creditCardRepository.findById(uco.creditCardID);
				try {
					CreditCard card = creditCardSearch.get();
					u.setCreditCard(card);
				}
				catch(NoSuchElementException e) {
					return new ResponseEntity<Object>(new RoleNotFoundException(uco.creditCardID.toString()).getMessage(), HttpStatus.NOT_FOUND);
				}
			}
			userRepository.save(u);
			return new ResponseEntity<Object>(u, HttpStatus.OK);
			
		}
		catch(NoSuchElementException e) {
			return new ResponseEntity<Object>(new MovieNotFoundException(id).getMessage(), HttpStatus.NOT_FOUND);
		}
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable UUID id){
		Optional<User> o = userRepository.findById(id);
		try {
			userRepository.deleteById(o.get().getId());
			return new ResponseEntity<>(id, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new UserNotFoundException(id).getMessage(), HttpStatus.NOT_FOUND);
		}
	}

}
