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

import de.wi2020sebgroup1.cinema.entities.Price;
import de.wi2020sebgroup1.cinema.exceptions.PriceNotFoundException;
import de.wi2020sebgroup1.cinema.repositories.PriceRepository;

@Controller
@RestController
@RequestMapping("/price")
public class PriceController {
	
	@Autowired
	PriceRepository priceRepository;
	
	@PutMapping("/add")
	public ResponseEntity<Object> addPrice(@RequestBody Price price){
		return new ResponseEntity<Object>(priceRepository.save(price), HttpStatus.CREATED);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<Object> getAll(){
		return new ResponseEntity<Object>(priceRepository.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getSpecific(@PathVariable UUID id){
		Optional<Price> priceSearch = priceRepository.findById(id);
		
		try {
			return new ResponseEntity<Object>(priceSearch.get(), HttpStatus.OK);
		}catch(NoSuchElementException e) {
			return new ResponseEntity<Object>(new PriceNotFoundException(id).getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteById(@PathVariable UUID id){
		try {
			priceRepository.deleteById(id);
			return new ResponseEntity<Object>( new String ("Price with id \"" + id + "\" deleted!"), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<Object>(new PriceNotFoundException(id).getMessage(), HttpStatus.NOT_FOUND);
		}
		
	}

}
