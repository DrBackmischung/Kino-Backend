package de.wi2020sebgroup1.cinema.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import de.wi2020sebgroup1.cinema.configurationObject.SeatsBlueprintConfigurationObject;
import de.wi2020sebgroup1.cinema.entities.CinemaRoom;
import de.wi2020sebgroup1.cinema.entities.Price;
import de.wi2020sebgroup1.cinema.entities.SeatsBluePrint;
import de.wi2020sebgroup1.cinema.repositories.CinemaRoomRepository;
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
	
	
	public Object add(List<SeatsBlueprintConfigurationObject> seatConfig) {
		
		ArrayList<SeatsBluePrint> seatsToAdd = new ArrayList<>();
		
		try {
			for(SeatsBlueprintConfigurationObject seatObject:seatConfig) {
				Price price = priceRepository.findById(seatObject.priceID).get();
				CinemaRoom cinemaRoom = cinemaRoomRepository.findById(seatObject.cinemaRoomID).get();
				SeatsBluePrint seatBluePrint = new SeatsBluePrint(seatObject.line, seatObject.place,price,cinemaRoom);
				
				seatsToAdd.add(seatBluePrint);
				
			}
			
			return seatBluePrintRepository.saveAll(seatsToAdd);
			
			
		}
		catch (Exception e) {
			return null;
		}
	}

}
