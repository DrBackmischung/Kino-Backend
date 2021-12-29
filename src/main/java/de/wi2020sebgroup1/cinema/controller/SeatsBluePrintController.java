package de.wi2020sebgroup1.cinema.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.wi2020sebgroup1.cinema.configurationObject.SeatsBlueprintConfigurationObject;
import de.wi2020sebgroup1.cinema.entities.CinemaRoom;
import de.wi2020sebgroup1.cinema.entities.Price;
import de.wi2020sebgroup1.cinema.entities.SeatsBluePrint;
import de.wi2020sebgroup1.cinema.repositories.CinemaRoomRepository;
import de.wi2020sebgroup1.cinema.repositories.PriceRepository;
import de.wi2020sebgroup1.cinema.repositories.SeatBluePrintRepository;
import de.wi2020sebgroup1.cinema.repositories.ShowRepository;
import de.wi2020sebgroup1.cinema.service.SeatBlueprintService;

@Controller
@RestController
@RequestMapping("/seatsBlueprint")
public class SeatsBluePrintController {
	
	@Autowired
	SeatBluePrintRepository seatBluePrintRepository;
	
	@Autowired
	PriceRepository priceRepository;
	
	@Autowired
	CinemaRoomRepository cinemaRoomRepository;
	
	@Autowired
	SeatBlueprintService seatBlueprintService;
	
	@PutMapping("/add")
	public ResponseEntity<Object> add(@RequestBody SeatsBlueprintConfigurationObject seatConfig){
		
		List<SeatsBlueprintConfigurationObject> toAdd = new ArrayList<>();
		toAdd.add(seatConfig);
		
		Object newSeats = seatBlueprintService.add(toAdd);
		
		if(newSeats != null) {
			return new ResponseEntity<Object>(newSeats, HttpStatus.OK);
		}
		else {
			//TODO Add custom exception
			return new ResponseEntity<Object>(HttpStatus.CONFLICT);
		}
		
	}
	
	@PutMapping("/massAdd")
	public ResponseEntity<Object> massAdd(@RequestBody List<SeatsBlueprintConfigurationObject> seatConfig){
		
		Object newSeats = seatBlueprintService.add(seatConfig);
		
		if(newSeats != null) {
			return new ResponseEntity<Object>(newSeats, HttpStatus.OK);
		}
		else {
			//TODO Add custom exception
			return new ResponseEntity<Object>(HttpStatus.CONFLICT);
		}	
	}

}
