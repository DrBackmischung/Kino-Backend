package de.wi2020sebgroup1.cinema.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import de.wi2020sebgroup1.cinema.entities.Movie;
import de.wi2020sebgroup1.cinema.entities.Review;
import de.wi2020sebgroup1.cinema.repositories.MovieRepository;
import de.wi2020sebgroup1.cinema.repositories.ReviewRepository;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
@AutoConfigureMockMvc
public class RatingControllerTest {
	
	MockMvc mvc;
	
	@MockBean
	ReviewRepository repo;
	
	@MockBean
	MovieRepository movieRepository;
    
    @Autowired
    WebApplicationContext wac;
	
	static UUID uuid;
	
	@BeforeAll
	static void beforeAll() {
		uuid = new UUID(2, 2);
	}

    @BeforeEach
    void beforeEach() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    
    Review getReview() {
    	Review r = new Review(null, null, "Review!", "Mathis stinkt", null, null, 5);
    	r.setId(uuid);
    	return r;
    }
    
    Optional<List<Review>> getOptionalReviewList() {
    	List<Review> list = new ArrayList<>();
    	list.add(getReview());
    	return Optional.of(list);
    }
    
    Movie getMovie() {
    	Movie m = new Movie("Shrek 3", "deutsch", null, 2.5, "Kitty Blume", "Ein Film", "localhost/img", null, null, null, null, 0);
    	m.setId(uuid);
    	return m;
    }
    
    Optional<Movie> getOptionalMovie() {
    	Movie m = getMovie();
    	return Optional.of(m);
    }
    
    @Test
    void testGet() throws Exception {
    	when(movieRepository.findById(uuid)).thenReturn(getOptionalMovie());
    	when(repo.findAllByMovie(getMovie())).thenReturn(getOptionalReviewList());
        mvc.perform(get("/rating/"+uuid))
                .andExpect(status().isOk());
    }
    
    @Test
    void testGetException() throws Exception {
        mvc.perform(get("/rating/"+uuid))
        	.andExpect(status().isNotFound());
    	when(movieRepository.findById(uuid)).thenReturn(getOptionalMovie());
        mvc.perform(get("/rating/"+uuid))
        	.andExpect(status().isNotFound());
    }
}
