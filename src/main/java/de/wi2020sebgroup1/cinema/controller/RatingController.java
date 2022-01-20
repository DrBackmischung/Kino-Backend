package de.wi2020sebgroup1.cinema.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.wi2020sebgroup1.cinema.entities.Movie;
import de.wi2020sebgroup1.cinema.entities.Review;
import de.wi2020sebgroup1.cinema.exceptions.MovieNotFoundException;
import de.wi2020sebgroup1.cinema.repositories.MovieRepository;
import de.wi2020sebgroup1.cinema.repositories.ReviewRepository;

@Controller
@RestController
@RequestMapping("/rating")
public class RatingController {
	
	@Autowired
	ReviewRepository reviewRepository;
	
	@Autowired
	MovieRepository movieRepository;
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getSpecific(@PathVariable UUID id){
		Optional<Movie> movieSearch = movieRepository.findById(id);
		try {
			Movie m = movieSearch.get();
			Optional<List<Review>> listSearch = reviewRepository.findAllByMovie(m);
			try {
				double rating = 0;
				List<Review> reviews = listSearch.get();
				for(Review r : reviews) {
					rating += r.getRating();
				}
				rating = rating / reviews.size();
				return new ResponseEntity<Object>(rating, HttpStatus.OK);
			} catch(NoSuchElementException e) {
				return new ResponseEntity<Object>("Error while loading reviews for movie", HttpStatus.NOT_FOUND);
			}
		}
		catch(NoSuchElementException e) {
			return new ResponseEntity<Object>(new MovieNotFoundException(id).getMessage(), HttpStatus.NOT_FOUND);
		}
	}

}
