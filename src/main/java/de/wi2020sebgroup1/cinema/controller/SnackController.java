package de.wi2020sebgroup1.cinema.controller;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

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

import de.wi2020sebgroup1.cinema.entities.Snack;
import de.wi2020sebgroup1.cinema.exceptions.SnackNotFoundException;
import de.wi2020sebgroup1.cinema.repositories.SnackRepository;

@Controller
@RestController
@RequestMapping("/snack")
public class SnackController {
	
	@Autowired
	SnackRepository snackRepository;
	
	@PutMapping("/add")
	public ResponseEntity<Object> addSnack(@RequestBody Snack e){
		return new ResponseEntity<Object>(snackRepository.save(e), HttpStatus.CREATED);
	}
	
	
	@GetMapping("/getAll")
	public ResponseEntity<Object> getAll(){
		return new ResponseEntity<Object>(snackRepository.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getSpecific(@PathVariable UUID id){
		try {
			return new ResponseEntity<Object>(snackRepository.findById(id).get(), HttpStatus.OK);
		}
		catch(NoSuchElementException e) {
			return new ResponseEntity<Object>(new SnackNotFoundException(id).getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteNews(@PathVariable UUID id){
		Optional<Snack> o = snackRepository.findById(id);
		try {
			snackRepository.deleteById(o.get().getId());
			return new ResponseEntity<Object>(new String("Snack with id \"" + id + "\" deleted!"), HttpStatus.OK);
		} catch(NoSuchElementException e) {
			return new ResponseEntity<Object>(new SnackNotFoundException(id).getMessage(), HttpStatus.NOT_FOUND);
		}
	}

}
