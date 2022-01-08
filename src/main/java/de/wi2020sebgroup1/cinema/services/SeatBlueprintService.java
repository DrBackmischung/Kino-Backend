package de.wi2020sebgroup1.cinema.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import de.wi2020sebgroup1.cinema.configurationObject.SeatsBlueprintConfigurationObject;
import de.wi2020sebgroup1.cinema.entities.CinemaRoom;
import de.wi2020sebgroup1.cinema.entities.CinemaRoomSeatingPlan;
import de.wi2020sebgroup1.cinema.entities.Price;
import de.wi2020sebgroup1.cinema.entities.SeatsBluePrint;
import de.wi2020sebgroup1.cinema.repositories.CinemaRoomRepository;
import de.wi2020sebgroup1.cinema.repositories.CinemaRoomSeatingPlanRepository;
import de.wi2020sebgroup1.cinema.repositories.PriceRepository;
import de.wi2020sebgroup1.cinema.repositories.SeatBluePrintRepository;

@Service
public class SeatBlueprintService {
	
	
	@Autowired
	SeatBluePrintRepository seatBluePrintRepository;
	
	@Autowired
	PriceRepository priceRepository;
	
	@Autowired
	CinemaRoomRepository cinemaRoomRepository;
	
	@Autowired
	CinemaRoomSeatingPlanRepository seatingPlanRepository;
	
	public ResponseEntity<Object> add(List<SeatsBlueprintConfigurationObject> seatConfig){
		
		ArrayList<SeatsBluePrint> seatsToAdd = new ArrayList<>();
		
		try {
			for(SeatsBlueprintConfigurationObject seatObject:seatConfig) {
				Price price = priceRepository.findByType(seatObject.type).get();
				CinemaRoom cinemaRoom = cinemaRoomRepository.findById(seatObject.cinemaRoomID).get();
				CinemaRoomSeatingPlan seatingPlan = cinemaRoom.getCinemaRoomSeatingPlan();
				
				SeatsBluePrint seatBluePrint = new SeatsBluePrint(seatObject.line, seatObject.place, seatObject.type, price,
						cinemaRoom, seatingPlan);	
				seatsToAdd.add(seatBluePrint);
				
			}
			
			return new ResponseEntity<Object>(seatBluePrintRepository.saveAll(seatsToAdd), HttpStatus.CREATED);
			
		}
		catch(NoSuchElementException nSE) {
			return new ResponseEntity<Object>(new String("One or more given ids are not found!"), HttpStatus.CONFLICT);
		}catch(IllegalArgumentException iAE) {
			return new ResponseEntity<Object>(new String("Seat blueprints couldn't be saved!!"), HttpStatus.CONFLICT);
		}catch (Exception e) {
			return new ResponseEntity<Object>(new String("A unkown error appeared!"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	public ResponseEntity<Object> delete(List<UUID> ids) {
		
		try {
			
			seatBluePrintRepository.deleteAllById(ids);
			return new ResponseEntity<Object>(new String("Seat Blueprints deleted!"), HttpStatus.OK);
		}
		catch(IllegalArgumentException  e) {
			return new ResponseEntity<Object>(new String("Some of the ids were null or not found!"), HttpStatus.CONFLICT);
		}
		
	}
	
	public ResponseEntity<Object> getAllForRoom(UUID id){
		
		try {
			CinemaRoom cinemaRoom = cinemaRoomRepository.findById(id).get();
			return new ResponseEntity<Object>(seatBluePrintRepository.findAllByCinemaRoom(cinemaRoom), HttpStatus.OK);
		}
		catch(NoSuchElementException e) {
			//TODO add custom exception
			return new ResponseEntity<Object>(new String("Cinema Room not found"), HttpStatus.NOT_FOUND);
		}
		
		
	}

}
