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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.wi2020sebgroup1.cinema.configurationObject.CinemaRoomConfigurationObject;
import de.wi2020sebgroup1.cinema.entities.Cinema;
import de.wi2020sebgroup1.cinema.entities.CinemaRoom;
import de.wi2020sebgroup1.cinema.entities.CinemaRoomSeatingPlan;
import de.wi2020sebgroup1.cinema.repositories.CinemaRepository;
import de.wi2020sebgroup1.cinema.repositories.CinemaRoomRepository;
import de.wi2020sebgroup1.cinema.repositories.CinemaRoomSeatingPlanRepository;

@Controller
@RestController
@RequestMapping("/cinemaRoom")
public class CinemaRoomController {
	
	@Autowired
	CinemaRoomRepository cinemaRoomRepository;
	
	@Autowired
	CinemaRepository cinemaRepository;
	
	@Autowired
	CinemaRoomSeatingPlanRepository cinemaRoomSeatingPlanRepository;
	
	@PutMapping("/add")
	public ResponseEntity<Object> addCinemaRoom(@RequestBody CinemaRoomConfigurationObject cinemaRoomConfigurationObject){
		
		CinemaRoom toBuild = new CinemaRoom();
		Cinema toAddCinema = new Cinema();
		CinemaRoomSeatingPlan toAddSeatingPlan = new CinemaRoomSeatingPlan();
		
		if(cinemaRoomConfigurationObject.cinemaRoomSeatingPlan != null) {
			try {
				toAddSeatingPlan = cinemaRoomSeatingPlanRepository.findById(cinemaRoomConfigurationObject.cinemaRoomSeatingPlan).get();
				toBuild.setCinemaRoomSeatingPlan(toAddSeatingPlan);
			}
			catch(NoSuchElementException e) {
				return new ResponseEntity<Object>(new String("No CinemaRoomSeatingPlan with id \"" + cinemaRoomConfigurationObject.cinemaRoomSeatingPlan + "\" found!"), HttpStatus.NOT_FOUND);
			}
		}
		
		if(cinemaRoomConfigurationObject.cinemaID != null) {
			try {
				toAddCinema = cinemaRepository.findById(cinemaRoomConfigurationObject.cinemaID).get();
				toBuild.setCinema(toAddCinema);
			}
			catch(NoSuchElementException e) {
				return new ResponseEntity<Object>(new String("No CinemaRoom with id \"" + cinemaRoomConfigurationObject.cinemaID + "\" found!"), HttpStatus.NOT_FOUND);
			}
		}
		
		
		toBuild.setStory(cinemaRoomConfigurationObject.story);
		toBuild.setWheelchairAccessible(cinemaRoomConfigurationObject.wheelchairAccessible);
		
		
		return new ResponseEntity<Object>(cinemaRoomRepository.save(toBuild), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateCinemaRoom(@PathVariable UUID id, @RequestBody CinemaRoomConfigurationObject cinemaRoomConfigurationObject){
		
		Optional<CinemaRoom> oldRoomSearch = cinemaRoomRepository.findById(id);
		CinemaRoom toBuild = new CinemaRoom();
		Cinema toAddCinema = new Cinema();
		CinemaRoomSeatingPlan toAddSeatingPlan = new CinemaRoomSeatingPlan();
		
		try {
			CinemaRoom oldRoom = oldRoomSearch.get(); 
			toBuild = new CinemaRoom();
			toBuild.setId(oldRoom.getId());
			
			if(cinemaRoomConfigurationObject.cinemaRoomSeatingPlan != null) {
				try {
					toAddSeatingPlan = cinemaRoomSeatingPlanRepository.findById(cinemaRoomConfigurationObject.cinemaRoomSeatingPlan).get();
					toBuild.setCinemaRoomSeatingPlan(toAddSeatingPlan);
				}
				catch(NoSuchElementException e) {
					return new ResponseEntity<Object>(new String("No CinemaRoomSeatingPlan with id \"" + cinemaRoomConfigurationObject.cinemaRoomSeatingPlan + "\" found!"), HttpStatus.NOT_FOUND);
				}
			}
			
			if(cinemaRoomConfigurationObject.cinemaID != null) {
				try {
					toAddCinema = cinemaRepository.findById(cinemaRoomConfigurationObject.cinemaID).get();
					toBuild.setCinema(toAddCinema);
				}
				catch(NoSuchElementException e) {
					return new ResponseEntity<Object>(new String("No Cinema with id \"" + cinemaRoomConfigurationObject.cinemaID + "\" found!"), HttpStatus.NOT_FOUND);
				}
			}
			
			
			toBuild.setStory(cinemaRoomConfigurationObject.story);
			toBuild.setWheelchairAccessible(cinemaRoomConfigurationObject.wheelchairAccessible);
			return new ResponseEntity<Object>(cinemaRoomRepository.save(toBuild), HttpStatus.OK);
		}
		catch(NoSuchElementException e) {
			return new ResponseEntity<Object>(new String("No CinemaRoom with id \"" + id + "\" found!"), HttpStatus.NOT_FOUND);
		}
		
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<Iterable<CinemaRoom>> getAllCinemaRooms(){
		return new ResponseEntity<Iterable<CinemaRoom>>(cinemaRoomRepository.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getCinemaRoom(@PathVariable UUID id){
		Optional<CinemaRoom> search = cinemaRoomRepository.findById(id);
		
		try {
			CinemaRoom searched = search.get();
			return new ResponseEntity<Object>(searched, HttpStatus.OK);
		}
		catch(NoSuchElementException e) {
			return new ResponseEntity<Object>(new String("No CinemaRoom with id \"" + id + "\" found!"), HttpStatus.NOT_FOUND);
		}
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteCinemaRoom(@PathVariable UUID id){
		try {
			cinemaRoomRepository.deleteById(id);
			return new ResponseEntity<Object>(new String("CinemaRoom with id \"" + id + "\" deleted!"),HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Object>(new String("No CinemaRoom with id \"" + id + "\" found!"), HttpStatus.NOT_FOUND);
		}
		
	}

}
