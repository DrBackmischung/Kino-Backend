package de.wi2020sebgroup1.cinema.services;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import de.wi2020sebgroup1.cinema.entities.Movie;
import de.wi2020sebgroup1.cinema.exceptions.MovieNotFoundException;
import de.wi2020sebgroup1.cinema.repositories.MovieRepository;
import de.wi2020sebgroup1.cinema.repositories.ShowRepository;

@Service
public class ShowService {
	
	@Autowired
	MovieRepository movieRepository;
	
	@Autowired
	ShowRepository showRepository;
		
	/**
	 * This function is responsible for getting all Shows by a Movie until the next Thursday
	 * 
	 * @param movieId - the Id of the movie to search shows for
	 * @return ResponseEntity - A list of shows in range and a status
	 */
	public ResponseEntity<Object> getAllByMovie(UUID movieId){
		
		
		try {
			Movie movie = movieRepository.findById(movieId).get();
			try {
				movie = movieRepository.findById(movieId).get();
			}
			catch(NoSuchElementException nSE) {
				return new ResponseEntity<Object>(new MovieNotFoundException(movieId), HttpStatus.NOT_FOUND);
			}
			
			LocalDate date = LocalDate.now();
			int today = date.getDayOfWeek().getValue();
			int offset = (today > 4) ? ((4 - today) + 7) : ((4 - today));
			LocalDate nextThursday = date.plusDays(offset);
			
			return new ResponseEntity<Object>(showRepository.findAllByShowDateBetweenAndMovie(java.sql.Date.valueOf(date), java.sql.Date.valueOf(nextThursday), movie).get(),HttpStatus.OK);
			
		}
		catch(Exception e) {
			return new ResponseEntity<Object>(new String("Error finding shows!"), HttpStatus.NOT_FOUND);
		}
		
		
	}

}
