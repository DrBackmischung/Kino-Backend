package de.wi2020sebgroup1.cinema.controller;

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
import de.wi2020sebgroup1.cinema.repositories.MovieRepository;

@Controller
@RestController
@RequestMapping("/movie")
public class MovieController {
	
	@Autowired
	MovieRepository movieRepository;
	
	@PutMapping
	public ResponseEntity<Object> addMovie(@RequestBody Movie movie){
		
		try {
			Movie createdMovie = movieRepository.save(movie);
			return new ResponseEntity<Object>(createdMovie, HttpStatus.CREATED);
		}
		catch(IllegalArgumentException e) {
			return new ResponseEntity<Object>(new String("Movie creation failed!"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
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
			return new ResponseEntity<Object>(new String("No Movie with id \"" + id + "\" found!"), HttpStatus.NOT_FOUND);
		}
		
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<Iterable<Movie>> getAll(){
		return new ResponseEntity<Iterable<Movie>>(movieRepository.findAll(), HttpStatus.OK);	
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getSpecific(@PathVariable UUID id){
		
		Optional<Movie> movie = movieRepository.findById(id);
		
		try {
			Movie toReturn = movie.get();
			return new ResponseEntity<Object>(toReturn, HttpStatus.OK);
		}
		catch(NoSuchElementException e) {
			return new ResponseEntity<Object>(new String("No movie with id \"" + id + "\" found!"), HttpStatus.NOT_FOUND);
		}
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteMovie(@PathVariable UUID id){
		try {
			movieRepository.deleteById(id);
			return new ResponseEntity<Object>(new String("Movie with id \"" + id + "\" deleted!"), HttpStatus.OK);
		}
		catch(IllegalArgumentException e) {
			return new ResponseEntity<Object>(new String("No movie with id \"" + id + "\" found!"), HttpStatus.NOT_FOUND);
		}
	}

}
