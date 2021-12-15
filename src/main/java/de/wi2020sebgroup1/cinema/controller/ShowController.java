package de.wi2020sebgroup1.cinema.controller;

import java.util.ArrayList;
import java.util.List;
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

import de.wi2020sebgroup1.cinema.configurationObject.ShowConfigurationObject;
import de.wi2020sebgroup1.cinema.entities.Cinema;
import de.wi2020sebgroup1.cinema.entities.CinemaRoom;
import de.wi2020sebgroup1.cinema.entities.CinemaRoomSeatingPlan;
import de.wi2020sebgroup1.cinema.entities.Movie;
import de.wi2020sebgroup1.cinema.entities.Seat;
import de.wi2020sebgroup1.cinema.entities.Show;
import de.wi2020sebgroup1.cinema.repositories.CinemaRepository;
import de.wi2020sebgroup1.cinema.repositories.CinemaRoomRepository;
import de.wi2020sebgroup1.cinema.repositories.CinemaRoomSeatingPlanRepository;
import de.wi2020sebgroup1.cinema.repositories.MovieRepository;
import de.wi2020sebgroup1.cinema.repositories.SeatRepository;
import de.wi2020sebgroup1.cinema.repositories.ShowRepository;

@Controller
@RestController
@RequestMapping("/show")
public class ShowController {
	
	@Autowired
	ShowRepository showRepository;
	
	@Autowired
	CinemaRepository cinemaRepository;
	
	@Autowired
	MovieRepository movieRepository;
	
	@Autowired
	CinemaRoomRepository cinemaRoomRepository;
	
	@Autowired
	CinemaRoomSeatingPlanRepository seatingPlanRepository;
	
	@Autowired
	SeatRepository seatRepository;
	
	@PutMapping("/add")
	public ResponseEntity<Object> addShow(@RequestBody ShowConfigurationObject showConfigurationObject){
		
		Show toAdd = new Show();
		toAdd.setStartTime(showConfigurationObject.start);
		toAdd.setEndTime(showConfigurationObject.end);
		toAdd.setShowDate(showConfigurationObject.date);
		
		if(showConfigurationObject.cinemaID != null) {
			Optional<Cinema> cinemaSearch = cinemaRepository.findById(showConfigurationObject.cinemaID);
			try {
				Cinema cinema = cinemaSearch.get();
				toAdd.setCinema(cinema);
			}
			catch(NoSuchElementException e)
			{
				return new ResponseEntity<Object>(new String("No Cinema with id \"" + showConfigurationObject.cinemaID + "\" found!"),
						HttpStatus.NOT_FOUND);
			}
		}
		
		if(showConfigurationObject.movieID != null) {
			Optional<Movie> movieSearch = movieRepository.findById(showConfigurationObject.movieID);
			try {
				Movie movie = movieSearch.get();
				toAdd.setMovie(movie);
			}
			catch(NoSuchElementException e)
			{
				return new ResponseEntity<Object>(new String("No Movie with id \"" + showConfigurationObject.movieID + "\" found!"),
						HttpStatus.NOT_FOUND);
			}
		}
		
		if(showConfigurationObject.cinemaRoomID != null) {
			Optional<CinemaRoom> roomSearch = cinemaRoomRepository.findById(showConfigurationObject.cinemaRoomID);
			try {
				CinemaRoom room = roomSearch.get();
				toAdd.setCinemaRoom(room);
				try {
					CinemaRoomSeatingPlan seatingPlan = room.getCinemaRoomSeatingPlan();
					int seatsPerRow = seatingPlan.getSeats() / seatingPlan.getReihen();
					List<Seat> showSeats = new ArrayList<>();
					
					for(int i = 1; i <= seatingPlan.getReihen(); i++) {
						for(int j = 1; j <= seatsPerRow; j++) {
							Seat newSeat = new Seat(i, j, false, false, 0, seatingPlan, toAdd);
							showSeats.add(newSeat);
						}
					}
					
					seatRepository.saveAll(showSeats);
				}
				catch(NoSuchElementException e)
				{
					return new ResponseEntity<Object>(new String("No Seating Plan for given Room found!"),
							HttpStatus.NOT_FOUND);
				}
			}
			catch(NoSuchElementException e)
			{
				return new ResponseEntity<Object>(new String("No Room with id \"" + showConfigurationObject.cinemaRoomID + "\" found!"),
						HttpStatus.NOT_FOUND);
			}
		}

		return new ResponseEntity<Object>(showRepository.save(toAdd), HttpStatus.OK);
		
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<Object> getAll(){
		return new ResponseEntity(showRepository.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getSpecific(@PathVariable UUID id){
		
		Optional<Show> toSearch = showRepository.findById(id);
		try {
			return new ResponseEntity<Object>(toSearch.get(), HttpStatus.OK);
		}
		catch(NoSuchElementException e)
		{
			return new ResponseEntity<Object>(new String("No Show with id \"" + id + "\" found!"),
					HttpStatus.NOT_FOUND);
		}

	}
	
	@GetMapping("/{id}/seats")
	public ResponseEntity<Object> getSeatsForShow(@PathVariable UUID id){
		
		Optional<Show> showSearch = showRepository.findById(id);
		try {
			Optional<List<Seat>> seatsSearch = seatRepository.findAllByShow(showSearch.get());
			try {
				return new ResponseEntity<Object>(seatsSearch.get(), HttpStatus.OK);
			}
			catch(NoSuchElementException e)
			{
				return new ResponseEntity<Object>(new String("No Seats for Show with id \"" + id + "\" found!"),
						HttpStatus.NOT_FOUND);
			}
		}
		catch(NoSuchElementException e)
		{
			return new ResponseEntity<Object>(new String("No Show with id \"" + id + "\" found!"),
					HttpStatus.NOT_FOUND);
		}
	}
	
	@Transactional
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteShow(@PathVariable UUID id){
		Optional<Show> showSearch = showRepository.findById(id);
		try {
			Show show = showSearch.get();
			//seatRepository.deleteAllByShow(show);
			showRepository.deleteById(id);
			return new ResponseEntity<Object>(new String("Show with id \"" + id + "\" deleted!"), HttpStatus.OK);
		}
		catch(NoSuchElementException e)
		{
			return new ResponseEntity<Object>(new String("No Show with id \"" + id + "\" found!"),
					HttpStatus.NOT_FOUND);
		}
	}

}
