package de.wi2020sebgroup1.cinema.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.wi2020sebgroup1.cinema.entities.City;
import de.wi2020sebgroup1.cinema.exceptions.CityNotCreatableException;
import de.wi2020sebgroup1.cinema.exceptions.CityNotFoundException;
import de.wi2020sebgroup1.cinema.repositories.CityRepository;

@Controller
@RestController
@RequestMapping("/city")
public class CityController {
	
	@Autowired
	CityRepository cityRepository;
	
	@PutMapping("/add")
	public ResponseEntity<Object> addCity(@RequestBody City toAddCity){
		List<City> toGetCity = cityRepository.findByPlz(toAddCity.getPlz());
		if(toGetCity.size() == 1) {
			return new ResponseEntity<>(toGetCity.get(0), HttpStatus.OK);
		}
		try {
			cityRepository.save(toAddCity);
			return new ResponseEntity<>(toAddCity, HttpStatus.CREATED);
		}catch(IllegalArgumentException e) {
			return new ResponseEntity<Object>(new CityNotCreatableException().getMessage(), HttpStatus.NOT_ACCEPTABLE);
			
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getCity(@PathVariable UUID id){
		Optional<City> foundCity = cityRepository.findById(id);
		try {
			return new ResponseEntity<>(foundCity.get(), HttpStatus.FOUND);
		}catch(NoSuchElementException e) {
			return new ResponseEntity<>(new CityNotFoundException(id).getMessage(), HttpStatus.NOT_FOUND);
		}
		
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<Iterable<City>> getCities(){
		return new ResponseEntity<>(cityRepository.findAll(),HttpStatus.OK);
	}

}
