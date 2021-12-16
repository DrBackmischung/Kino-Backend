package de.wi2020sebgroup1.cinema.controller;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

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

import de.wi2020sebgroup1.cinema.configurationObject.CinemaRoomSeattingPlanConfigurationObject;
import de.wi2020sebgroup1.cinema.entities.CinemaRoom;
import de.wi2020sebgroup1.cinema.entities.CinemaRoomSeatingPlan;
import de.wi2020sebgroup1.cinema.repositories.CinemaRoomRepository;
import de.wi2020sebgroup1.cinema.repositories.CinemaRoomSeatingPlanRepository;

@Controller
@RestController
@RequestMapping("/seatingPlan")
public class CinemaRoomSeatingPlanController {
	
	@Autowired
	CinemaRoomSeatingPlanRepository seatingPlanRepository;
	
	@Autowired
	CinemaRoomRepository cinemaRoomRepository;
	
	@PutMapping("/add")
	@Transactional
	public ResponseEntity<Object> addSeatingPlan(@RequestBody CinemaRoomSeattingPlanConfigurationObject seatingPlanConfigurationObject){
		CinemaRoomSeatingPlan seatingPlan = new CinemaRoomSeatingPlan();
		CinemaRoom cinemaRoom = new CinemaRoom();
		if(seatingPlanConfigurationObject.cinemaRoomID != null) {
			try {
				Optional<CinemaRoom> cinemaRoomSearch =  cinemaRoomRepository.findById(seatingPlanConfigurationObject.cinemaRoomID);
				cinemaRoom = cinemaRoomSearch.get();
				seatingPlan.setCinemaRoom(cinemaRoom);
			}
			catch(NoSuchElementException e) {
				return new ResponseEntity<Object>(new String ("No CinemaRoom with id \"" + seatingPlanConfigurationObject.cinemaRoomID + "\" found!"), 
						HttpStatus.NOT_FOUND );
			}
		}
		seatingPlan.setSeats(seatingPlanConfigurationObject.seats);
		seatingPlan.setReihen(seatingPlanConfigurationObject.reihen);
		cinemaRoom.setCinemaRoomSeatingPlan(seatingPlan);
		return new ResponseEntity<Object>(seatingPlanRepository.save(seatingPlan), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateSeatingPlan(@PathVariable UUID id, @RequestBody CinemaRoomSeattingPlanConfigurationObject seatingPlanConfigurationObject){
		Optional<CinemaRoomSeatingPlan> oldSeatingPlan = seatingPlanRepository.findById(id);
		
		try {
			CinemaRoomSeatingPlan seatingPlan = new CinemaRoomSeatingPlan();
			seatingPlan.setId(oldSeatingPlan.get().getId());

			if(seatingPlanConfigurationObject.cinemaRoomID != null) {
				try {
					Optional<CinemaRoom> cinemaRoom =  cinemaRoomRepository.findById(seatingPlanConfigurationObject.cinemaRoomID);
					seatingPlan.setCinemaRoom(cinemaRoom.get());
				}
				catch(NoSuchElementException e) {
					return new ResponseEntity<Object>(new String ("No CinemaRoom with id \"" + seatingPlanConfigurationObject.cinemaRoomID + "\" found!"), 
							HttpStatus.NOT_FOUND );
				}
			}
			seatingPlan.setSeats(seatingPlanConfigurationObject.seats);
			seatingPlan.setReihen(seatingPlanConfigurationObject.reihen);
			return new ResponseEntity<Object>(seatingPlanRepository.save(seatingPlan), HttpStatus.OK);
			
		} catch(NoSuchElementException e) {
			return new ResponseEntity<Object>(new String ("No CinemaRoom with id \"" + seatingPlanConfigurationObject.cinemaRoomID + "\" found!"), 
					HttpStatus.NOT_FOUND );
		}
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<Object> getAll(){
		return new ResponseEntity<Object>(seatingPlanRepository.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getById(@PathVariable UUID id){
		Optional<CinemaRoomSeatingPlan> seatingPlan = seatingPlanRepository.findById(id);
		try {
			return new ResponseEntity<Object>(seatingPlan.get(), HttpStatus.OK);
		}catch(NoSuchElementException e) {
			return new ResponseEntity<Object>(new String ("No CinemaRoom with id \"" + id + "\" found!"), 
					HttpStatus.NOT_FOUND );
		}
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Object> deleteById(@PathVariable UUID id){
		seatingPlanRepository.deleteById(id);
		return new ResponseEntity<Object>(new String("Seatingplan with id \"" + id +"\" deleted!"), HttpStatus.OK);
		
	}

}
