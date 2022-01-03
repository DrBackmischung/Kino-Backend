package de.wi2020sebgroup1.cinema.helper;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.wi2020sebgroup1.cinema.exceptions.ResponseNotFoundException;

@Controller
@RestController
@RequestMapping("/error")
public class ResponseController {
	
	@Autowired
	ResponseRepository repo;
	
	@GetMapping("")
	public ResponseEntity<Object> error() {
		return new ResponseEntity<Object>("Error!", Response.OK.status());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getSpecific(@PathVariable int id){
		Optional<de.wi2020sebgroup1.cinema.helper.ResponseEntity> responseEntity = repo.findById(id);
		
		try {
			return new ResponseEntity<Object>(responseEntity.get(), Response.OK.status());
		}catch(NoSuchElementException e) {
			return new ResponseEntity<Object>(new ResponseNotFoundException(id).getMessage(), Response.NOT_FOUND.status());
		}
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<Object> getAll(){
		return new ResponseEntity<Object>(repo.findAll(), Response.OK.status());
	}
	
	@PutMapping("/add")
	public ResponseEntity<Object> addCode(@RequestBody de.wi2020sebgroup1.cinema.helper.ResponseEntity e){
		return new ResponseEntity<Object>(repo.save(e), Response.CREATED.status());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateCode(@PathVariable int id, @RequestBody de.wi2020sebgroup1.cinema.helper.ResponseEntity e){
		
		Optional<de.wi2020sebgroup1.cinema.helper.ResponseEntity> toUpdate = repo.findById(id);
		
		try {
			int currentId = toUpdate.get().getId();
			e.setId(currentId);
			repo.save(e);
			return new ResponseEntity<Object>(e, Response.CHANGED.status());
			
		}
		catch(NoSuchElementException ex) {
			return new ResponseEntity<Object>(new ResponseNotFoundException(id).getMessage(), Response.NOT_FOUND.status());
		}
		
	}
	
}
