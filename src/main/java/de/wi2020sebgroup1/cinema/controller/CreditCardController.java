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

import de.wi2020sebgroup1.cinema.entities.CreditCard;
import de.wi2020sebgroup1.cinema.exceptions.CreditCardNotFoundException;
import de.wi2020sebgroup1.cinema.repositories.CreditCardRepository;

@Controller
@RestController
@RequestMapping("/creditcard")
public class CreditCardController {
	
	@Autowired
	CreditCardRepository creditCardRepository;
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getSpecific(@PathVariable UUID id){
		
		Optional<CreditCard> cc = creditCardRepository.findById(id);
		
		try {
			CreditCard toReturn = cc.get();
			return new ResponseEntity<Object>(toReturn, HttpStatus.OK);
		}
		catch(NoSuchElementException e) {
			return new ResponseEntity<Object>(new CreditCardNotFoundException(id).getMessage(), HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PutMapping("/add")
	public ResponseEntity<Object> addCC(@RequestBody CreditCard newCC){
		return new ResponseEntity<Object>(creditCardRepository.save(newCC), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteCC(@PathVariable UUID id){
		Optional<CreditCard> o = creditCardRepository.findById(id);
		try {
			creditCardRepository.deleteById(o.get().getId());
			return new ResponseEntity<>(id, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new CreditCardNotFoundException(id).getMessage(), HttpStatus.NOT_FOUND);
		}
	}

}
