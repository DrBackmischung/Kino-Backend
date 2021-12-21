package de.wi2020sebgroup1.cinema.controller;

import java.util.ArrayList;
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

import de.wi2020sebgroup1.cinema.configurationObject.NewsConfigurationObject;
import de.wi2020sebgroup1.cinema.entities.News;
import de.wi2020sebgroup1.cinema.entities.User;
import de.wi2020sebgroup1.cinema.exceptions.NewsNotFoundException;
import de.wi2020sebgroup1.cinema.exceptions.UserNotFoundException;
import de.wi2020sebgroup1.cinema.repositories.NewsRepository;
import de.wi2020sebgroup1.cinema.repositories.UserRepository;

@Controller
@RestController
@RequestMapping("/news")
public class NewsController {
	
	@Autowired
	NewsRepository newsRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@PutMapping("/add")
	public ResponseEntity<Object> addNews(@RequestBody NewsConfigurationObject newsConfigurationObject){
		News toAdd = new News();
		if(newsConfigurationObject.userID != null) {
			Optional<User> userSearch = userRepository.findById(newsConfigurationObject.userID);
			try {
				User u = userSearch.get();
				toAdd.setUser(u);
			} catch(NoSuchElementException e) {
				return new ResponseEntity<Object>(new UserNotFoundException(newsConfigurationObject.userID).getMessage(), HttpStatus.NOT_FOUND);
			}
		}
		toAdd.setHeader(newsConfigurationObject.header);
		toAdd.setContent(newsConfigurationObject.content);
		toAdd.setDate(newsConfigurationObject.date);
		toAdd.setTime(newsConfigurationObject.time);
		toAdd.setPictureLink(newsConfigurationObject.pictureLink);
		
		return new ResponseEntity<Object>(newsRepository.save(toAdd), HttpStatus.CREATED);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<Object> getAll(){
		return new ResponseEntity<Object>(newsRepository.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getSpecific(@PathVariable UUID id){
		try {
			return new ResponseEntity<Object>(newsRepository.findById(id).get(), HttpStatus.OK);
		}
		catch(NoSuchElementException e) {
			return new ResponseEntity<Object>(new NewsNotFoundException(id).getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@SuppressWarnings("deprecation")
	@GetMapping("/q/{year}/{month}/{day}")
	public ResponseEntity<Object> getAllForDate(@PathVariable int year, @PathVariable int month, @PathVariable int day){
		Iterable<News> listSearch = newsRepository.findAll();
		List<News> news = new ArrayList<News>();
		for(News n : listSearch) {
			if(n.getDate().getYear() == year && n.getDate().getMonth() == month && n.getDate().getDay() == day) {
				news.add(n);
			}
		}
		return new ResponseEntity<Object>(newsRepository.saveAll(news), HttpStatus.OK);
	}
	
	@SuppressWarnings("deprecation")
	@GetMapping("/q/{year}/{month}")
	public ResponseEntity<Object> getAllForMonth(@PathVariable int year, @PathVariable int month){
		Iterable<News> listSearch = newsRepository.findAll();
		List<News> news = new ArrayList<News>();
		for(News n : listSearch) {
			if(n.getDate().getYear() == year && n.getDate().getMonth() == month) {
				news.add(n);
			}
		}
		return new ResponseEntity<Object>(newsRepository.saveAll(news), HttpStatus.OK);
	}
	
	@SuppressWarnings("deprecation")
	@GetMapping("/q/{year}")
	public ResponseEntity<Object> getAllForYear(@PathVariable int year){
		Iterable<News> listSearch = newsRepository.findAll();
		List<News> news = new ArrayList<News>();
		for(News n : listSearch) {
			if(n.getDate().getYear() == year) {
				news.add(n);
			}
		}
		return new ResponseEntity<Object>(newsRepository.saveAll(news), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteNews(@PathVariable UUID id){
		Optional<News> o = newsRepository.findById(id);
		try {
			newsRepository.deleteById(o.get().getId());
			return new ResponseEntity<Object>(new String("News with id \"" + id + "\" deleted!"), HttpStatus.OK);
		} catch(NoSuchElementException e) {
			return new ResponseEntity<Object>(new NewsNotFoundException(id).getMessage(), HttpStatus.NOT_FOUND);
		}
	}

}
