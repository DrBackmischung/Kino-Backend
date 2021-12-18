package de.wi2020sebgroup1.cinema.controller;
import java.util.List;
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

import de.wi2020sebgroup1.cinema.entities.Cinema;
import de.wi2020sebgroup1.cinema.entities.City;
import de.wi2020sebgroup1.cinema.exceptions.CinemaNotFoundException;
import de.wi2020sebgroup1.cinema.repositories.CinemaRepository;
import de.wi2020sebgroup1.cinema.repositories.CityRepository;

@Controller
@RestController
@RequestMapping("/cinema")
public class CinemaController {
	
	@Autowired
	CinemaRepository cinemaRepository;
	
	@Autowired
	CityRepository cityRepository;
	
	@PutMapping("/add")
	public ResponseEntity<Cinema> addCinema(@RequestBody Cinema newCinema){
		
		Cinema toAddCinema = newCinema;
		City toCheckCity = newCinema.getCity();
		
		List<City> toGetCity = cityRepository.findByPlz(toCheckCity.getPlz());
		if(toGetCity.size() == 1) {
			toAddCinema.setCity(toGetCity.get(0));
		}
		
		return new ResponseEntity<>( cinemaRepository.save(toAddCinema), HttpStatus.CREATED);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<Iterable<Cinema>> getCinemas(){
		return new ResponseEntity<>(cinemaRepository.findAll(),HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getCinema(@PathVariable UUID id){
		Optional<Cinema> search = cinemaRepository.findById(id);
		try {
			Cinema found = search.get();
			return new ResponseEntity<>(found, HttpStatus.OK);
		}catch(NoSuchElementException e) {
			return new ResponseEntity<Object>(new CinemaNotFoundException(id).getMessage(), HttpStatus.NOT_FOUND);
		}
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteCinema(@PathVariable UUID id){
		Optional<Cinema> o = cinemaRepository.findById(id);
		try {
			cinemaRepository.deleteById(o.get().getId());
			return new ResponseEntity<>(id, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new CinemaNotFoundException(id).getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	


}