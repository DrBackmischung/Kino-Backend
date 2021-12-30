package de.wi2020sebgroup1.cinema.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import de.wi2020sebgroup1.cinema.entities.CinemaRoom;
import de.wi2020sebgroup1.cinema.entities.SeatsBluePrint;

public interface SeatBluePrintRepository extends CrudRepository<SeatsBluePrint, UUID> {
	
	List<SeatsBluePrint> findAllByCinemaRoom(CinemaRoom cinemaRoom);

}
