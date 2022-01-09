package de.wi2020sebgroup1.cinema.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import de.wi2020sebgroup1.cinema.entities.Movie;
import de.wi2020sebgroup1.cinema.helper.SemaphoreVault;
import de.wi2020sebgroup1.cinema.repositories.MovieRepository;
import de.wi2020sebgroup1.cinema.repositories.ShowRepository;

@Service
public class ShowService {
	
	@Autowired
	MovieRepository movieRepository;
	
	@Autowired
	ShowRepository showRepository;
	
	public ResponseEntity<Object> getAllByMovie(UUID movieId){
		
		
		try {
			Movie movie = movieRepository.findById(movieId).get();
			LocalDate date = LocalDate.now();
			int today = date.getDayOfWeek().getValue();
			int offset = (today > 4) ? ((4 - today) + 7) : ((4 - today));
			LocalDate nextThursday = date.plusDays(offset);
			
			System.out.println(nextThursday);
			
			return new ResponseEntity<Object>(showRepository.findAllByShowDateBetweenAndMovie(java.sql.Date.valueOf(date), java.sql.Date.valueOf(nextThursday), movie).get(),HttpStatus.OK);
			
		}
		catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.CONFLICT);
		}
		
		
	}

}
