package de.wi2020sebgroup1.cinema.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

import de.wi2020sebgroup1.cinema.entities.Movie;
import de.wi2020sebgroup1.cinema.entities.Review;
import de.wi2020sebgroup1.cinema.entities.Show;
import de.wi2020sebgroup1.cinema.exceptions.MovieNotCreatableException;
import de.wi2020sebgroup1.cinema.exceptions.MovieNotFoundException;
import de.wi2020sebgroup1.cinema.exceptions.ReviewForMovieNotFoundException;
import de.wi2020sebgroup1.cinema.repositories.MovieRepository;
import de.wi2020sebgroup1.cinema.repositories.ReviewRepository;
import de.wi2020sebgroup1.cinema.repositories.ShowRepository;
import de.wi2020sebgroup1.cinema.services.ShowService;

@Controller
@RestController
@RequestMapping("/movie")
public class MovieController {
	
	@Autowired
	MovieRepository movieRepository;
	
	@Autowired
	ShowRepository showRepository;
	
	@Autowired
	ShowService showService;
	
	@Autowired
	ReviewRepository reviewRepository;
	
	/**
	 * Call to add a movie
	 * 
	 * @param movie - the movie to add
	 * @return ResponseEntity - the added movie
	 */
	@PutMapping("/add")
	public ResponseEntity<Object> addMovie(@RequestBody Movie movie){
		
		try {
			Movie createdMovie = movieRepository.save(movie);
			return new ResponseEntity<Object>(createdMovie, HttpStatus.CREATED);
		}
		catch(IllegalArgumentException e) {
			return new ResponseEntity<Object>(new MovieNotCreatableException().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Call to update a movie
	 * 
	 * @param id - the movie to update
	 * @param movie - the new movie details
	 * @return ResponseEntity - the updated movie
	 */
	@PutMapping("/update/{id}")
	public ResponseEntity<Object> updateMovie(@PathVariable UUID id,@RequestBody Movie movie){
		
		Optional<Movie> toUpdate = movieRepository.findById(id);
		
		try {
			UUID currentMovieID = toUpdate.get().getId();
			movie.setId(currentMovieID);
			movieRepository.save(movie);
			return new ResponseEntity<Object>(movie, HttpStatus.OK);
			
		}
		catch(NoSuchElementException e) {
			return new ResponseEntity<Object>(new MovieNotFoundException(id).getMessage(), HttpStatus.NOT_FOUND);
		}
		
	}
	
	/**
	 * Call to get all movies in the database
	 * 
	 * @return ResponseEntity
	 */
	@GetMapping("/getAll")
	public ResponseEntity<Iterable<Movie>> getAll(){
		return new ResponseEntity<Iterable<Movie>>(movieRepository.findAll(), HttpStatus.OK);	
	}
	
	/*
	@GetMapping("/getAll")
	public ResponseEntity<Iterable<Movie>> getAllOld(){
		
		return getAll();
	}
	*/
	
	/**
	 * Call to get a specific movie
	 * 
	 * @param id movie
	 * @return ResponseEntity
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Object> getSpecific(@PathVariable UUID id){
		
		Optional<Movie> movie = movieRepository.findById(id);
		
		try {
			Movie toReturn = movie.get();
			return new ResponseEntity<Object>(toReturn, HttpStatus.OK);
		}
		catch(NoSuchElementException e) {
			return new ResponseEntity<Object>(new MovieNotFoundException(id).getMessage(), HttpStatus.NOT_FOUND);
		}
		
	}
	
	/**
	 * Call to get all Shows for a given movie until the next thursday
	 * 
	 * @param id
	 * @return ResponseEntity
	 */
	@GetMapping("/{id}/shows")
	public ResponseEntity<Object> getShowsForMovie(@PathVariable UUID id){
		return showService.getAllByMovie(id);
	}
	
	@GetMapping("/{id}/reviews")
	public ResponseEntity<Object> getReviewsForMovie(@PathVariable UUID id){
		try {
			Movie movie = movieRepository.findById(id).get();
		
			try {
				List<Review> reviews = reviewRepository.findAllByMovie(movie).get();
				return new ResponseEntity<Object>(reviews, HttpStatus.OK);
			}
			catch(NoSuchElementException e) {
				return new ResponseEntity<Object>(new ReviewForMovieNotFoundException(id), HttpStatus.NOT_FOUND);
			}

		}
		catch(NoSuchElementException e) {
			return new ResponseEntity<Object>(new MovieNotFoundException(id), HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * Call to delete a movie
	 * 
	 * @param id - id of the movie to delete
	 * @return ResponseEntity - status of delete
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteMovie(@PathVariable UUID id){
		Optional<Movie> o = movieRepository.findById(id);
		try {
			movieRepository.deleteById(o.get().getId());
			return new ResponseEntity<Object>(new String("Movie with id \"" + id + "\" deleted!"), HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Object>(new MovieNotFoundException(id).getMessage(), HttpStatus.NOT_FOUND);
		}
	}

}
