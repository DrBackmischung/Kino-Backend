package de.wi2020sebgroup1.cinema.controller;

import java.util.ArrayList;
import java.util.List;
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

import de.wi2020sebgroup1.cinema.configurationObject.SeatsBlueprintConfigurationObject;
import de.wi2020sebgroup1.cinema.entities.CinemaRoom;
import de.wi2020sebgroup1.cinema.entities.Price;
import de.wi2020sebgroup1.cinema.entities.SeatsBluePrint;
import de.wi2020sebgroup1.cinema.repositories.CinemaRoomRepository;
import de.wi2020sebgroup1.cinema.repositories.PriceRepository;
import de.wi2020sebgroup1.cinema.repositories.SeatBluePrintRepository;
import de.wi2020sebgroup1.cinema.repositories.ShowRepository;
import de.wi2020sebgroup1.cinema.services.SeatBlueprintService;

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
		
		List<SeatsBlueprintConfigurationObject> seatConfigs = new ArrayList<>();
		seatConfigs.add(seatConfig);
		
		return seatBlueprintService.add(seatConfigs);
		
	}
	
	@PutMapping("/massAdd")
	public ResponseEntity<Object> massAdd(@RequestBody List<SeatsBlueprintConfigurationObject> seatConfig){
		
		return seatBlueprintService.add(seatConfig);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<Object> getAll(){
		return new ResponseEntity<Object>(seatBluePrintRepository.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/room/{id}")
	public ResponseEntity<Object> getAllForRoom(@PathVariable UUID id){
		return seatBlueprintService.getAllForRoom(id);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable UUID id){
		
		List<UUID> ids = new ArrayList<>();
		return seatBlueprintService.delete(ids);

	}
	
	@DeleteMapping("/massDelete")
	public ResponseEntity<Object> massDelete(@RequestBody List<UUID> IDs){
		
		return seatBlueprintService.delete(IDs);
	}

}
