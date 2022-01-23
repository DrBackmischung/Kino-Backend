package de.wi2020sebgroup1.cinema.controller;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.wi2020sebgroup1.cinema.entities.Role;
import de.wi2020sebgroup1.cinema.entities.User;
import de.wi2020sebgroup1.cinema.exceptions.RoleNotFoundException;
import de.wi2020sebgroup1.cinema.exceptions.UserNotFoundException;
import de.wi2020sebgroup1.cinema.repositories.RoleRepository;
import de.wi2020sebgroup1.cinema.repositories.UserRepository;

@Controller
@RestController
@RequestMapping("/role")
public class RoleController {
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@PutMapping("/add")
	public ResponseEntity<Object> add(@RequestBody Role r){
		return new ResponseEntity<>(roleRepository.save(r), HttpStatus.CREATED);
	}
	
	@PutMapping("/admin/{id}")
	public ResponseEntity<Object> admin(@PathVariable UUID id){
		Optional<User> toUpdate = userRepository.findById(id);
		try {
			User u = toUpdate.get();
			u.setRole(roleRepository.findByAuthorization("ADMIN").get());
			return new ResponseEntity<>(userRepository.save(u), HttpStatus.CREATED);
		} catch(NoSuchElementException e) {
			return new ResponseEntity<>(new UserNotFoundException(id).getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/user/{id}")
	public ResponseEntity<Object> user(@PathVariable UUID id){
		Optional<User> toUpdate = userRepository.findById(id);
		try {
			User u = toUpdate.get();
			u.setRole(roleRepository.findByAuthorization("USER").get());
			return new ResponseEntity<>(userRepository.save(u), HttpStatus.CREATED);
		} catch(NoSuchElementException e) {
			return new ResponseEntity<>(new UserNotFoundException(id).getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<Object> getByUser(@PathVariable UUID id){
		Optional<User> search = userRepository.findById(id);
		try {
			User u = search.get();
			Role r = u.getRole();
			return new ResponseEntity<>(r.getAutorization(), HttpStatus.CREATED);
		} catch(NoSuchElementException e) {
			return new ResponseEntity<>(new UserNotFoundException(id).getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getSpecific(@PathVariable UUID id){
		Optional<Role> r = roleRepository.findById(id);
		try {
			return new ResponseEntity<>(r.get(), HttpStatus.OK);
		}catch(NoSuchElementException e) {
			return new ResponseEntity<>(new RoleNotFoundException(id).getMessage(), HttpStatus.NOT_FOUND);
		}
		
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<Object> getAll(){
		return new ResponseEntity<Object>(roleRepository.findAll(),HttpStatus.OK);
	}
	
	@Transactional
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable UUID id){
		Optional<Role> o = roleRepository.findById(id);
		try {
			roleRepository.deleteById(o.get().getId());
			return new ResponseEntity<>(id, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<Object>(new RoleNotFoundException(id).getMessage(), HttpStatus.NOT_FOUND);
		}
	}

}
