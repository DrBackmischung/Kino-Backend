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

import de.wi2020sebgroup1.cinema.configurationObject.ReviewConfigurationObject;
import de.wi2020sebgroup1.cinema.entities.Movie;
import de.wi2020sebgroup1.cinema.entities.Review;
import de.wi2020sebgroup1.cinema.entities.User;
import de.wi2020sebgroup1.cinema.exceptions.MovieNotFoundException;
import de.wi2020sebgroup1.cinema.exceptions.ReviewNotFoundException;
import de.wi2020sebgroup1.cinema.exceptions.UserNotFoundException;
import de.wi2020sebgroup1.cinema.repositories.MovieRepository;
import de.wi2020sebgroup1.cinema.repositories.ReviewRepository;
import de.wi2020sebgroup1.cinema.repositories.UserRepository;

@Controller
@RestController
@RequestMapping("/review")
public class ReviewController {
	
	@Autowired
	ReviewRepository reviewRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	MovieRepository movieRepository;
	
	@PutMapping("/add")
	public ResponseEntity<Object> addReview(@RequestBody ReviewConfigurationObject rco){
		Review toAdd = new Review();
		if(rco.userID != null) {
			Optional<User> userSearch = userRepository.findById(rco.userID);
			try {
				User u = userSearch.get();
				toAdd.setUser(u);
			} catch(NoSuchElementException e) {
				return new ResponseEntity<Object>(new UserNotFoundException(rco.userID).getMessage(), HttpStatus.NOT_FOUND);
			}
		}
		if(rco.movieID != null) {
			Optional<Movie> movieSearch = movieRepository.findById(rco.userID);
			try {
				Movie m = movieSearch.get();
				toAdd.setMovie(m);
			} catch(NoSuchElementException e) {
				return new ResponseEntity<Object>(new MovieNotFoundException(rco.movieID).getMessage(), HttpStatus.NOT_FOUND);
			}
		}
		toAdd.setHeader(rco.header);
		toAdd.setContent(rco.content);
		toAdd.setDate(rco.date);
		toAdd.setTime(rco.time);
		
		return new ResponseEntity<Object>(reviewRepository.save(toAdd), HttpStatus.CREATED);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<Object> getAll(){
		return new ResponseEntity<Object>(reviewRepository.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getSpecific(@PathVariable UUID id){
		try {
			return new ResponseEntity<Object>(reviewRepository.findById(id).get(), HttpStatus.OK);
		}
		catch(NoSuchElementException e) {
			return new ResponseEntity<Object>(new ReviewNotFoundException(id).getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteNews(@PathVariable UUID id){
		Optional<Review> o = reviewRepository.findById(id);
		try {
			reviewRepository.deleteById(o.get().getId());
			return new ResponseEntity<Object>(new String("Review with id \"" + id + "\" deleted!"), HttpStatus.OK);
		} catch(NoSuchElementException e) {
			return new ResponseEntity<Object>(new ReviewNotFoundException(id).getMessage(), HttpStatus.NOT_FOUND);
		}
	}

}
