package de.wi2020sebgroup1.cinema.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.wi2020sebgroup1.cinema.configurationObject.ReviewConfigurationObject;
import de.wi2020sebgroup1.cinema.entities.Movie;
import de.wi2020sebgroup1.cinema.entities.Review;
import de.wi2020sebgroup1.cinema.entities.User;
import de.wi2020sebgroup1.cinema.repositories.MovieRepository;
import de.wi2020sebgroup1.cinema.repositories.ReviewRepository;
import de.wi2020sebgroup1.cinema.repositories.UserRepository;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
@AutoConfigureMockMvc
public class ReviewControllerTest {
	
	MockMvc mvc;
	
	@MockBean
	ReviewRepository repo;
	
	@MockBean
	UserRepository userRepository;
	
	@MockBean
	MovieRepository movieRepository;
    
    @Autowired
    WebApplicationContext wac;
	
	JacksonTester<Review> jt;
	JacksonTester<ReviewConfigurationObject> jtco;
	
	static UUID uuid;
	
	@BeforeAll
	static void beforeAll() {
		uuid = new UUID(2, 2);
	}

    @BeforeEach
    void beforeEach() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    
    Review getReview() {
    	Review r = new Review(null, null, "Review!", "Mathis stinkt", null, null);
    	r.setId(uuid);
    	return r;
    }
    
    Optional<Review> getOptionalReview() {
    	Review r = getReview();
    	return Optional.of(r);
    }
    
    User getUser() {
    	User s = new User();
    	s.setId(uuid);
    	return s;
    }
    
    Optional<User> getOptionalUser() {
    	User s = getUser();
    	return Optional.of(s);
    }
    
    Movie getMovie() {
    	Movie m = new Movie("Shrek 3", "deutsch", 2.5, "Kitty Blume", "Ein Film", "localhost/img", 0);
    	m.setId(uuid);
    	return m;
    }
    
    Optional<Movie> getOptionalMovie() {
    	Movie m = getMovie();
    	return Optional.of(m);
    }
    
    @Test
    void testGetAll() throws Exception {
    	when(repo.findAll()).thenReturn(new ArrayList<Review>());
        mvc.perform(get("/review/getAll"))
                .andExpect(status().isOk());
    }
    
    @Test
    void testGetById() throws Exception {
        when(repo.findById(uuid)).thenReturn(getOptionalReview());
        MockHttpServletResponse response = mvc.perform(get("/review/"+uuid)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn().getResponse();
        assertEquals(jt.write(getReview()).getJson(), response.getContentAsString());
    }
    
    @Test
    void testGetByIdException() throws Exception {
        mvc.perform(get("/review/"+new UUID(0, 0))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void testPut() throws Exception{

        mvc.perform(
            put("/review/add/").contentType(MediaType.APPLICATION_JSON).content(jt.write(getReview()).getJson()))
        		.andExpect(status().isCreated());

        when(userRepository.findById(uuid)).thenReturn(getOptionalUser());
        when(movieRepository.findById(uuid)).thenReturn(getOptionalMovie());
        mvc.perform(
            put("/review/add/")
            	.contentType(MediaType.APPLICATION_JSON).content(jtco.write(new ReviewConfigurationObject(new Date(2), new Time(3), "Head", "Body", uuid, uuid)).getJson()))
        		.andExpect(status().isCreated());

    }

    @Test
    void testPutException() throws Exception{
        
        mvc.perform(
            put("/review/add/")
            	.contentType(MediaType.APPLICATION_JSON).content(jtco.write(new ReviewConfigurationObject(new Date(2), new Time(3), "Head", "Body", uuid, null)).getJson()))
        		.andExpect(status().isNotFound());
        
        mvc.perform(
            put("/review/add/")
            	.contentType(MediaType.APPLICATION_JSON).content(jtco.write(new ReviewConfigurationObject(new Date(2), new Time(3), "Head", "Body", null, uuid)).getJson()))
        		.andExpect(status().isNotFound());

    }

    @Test
    void testDelete() throws Exception{

    	when(repo.findById(uuid)).thenReturn(getOptionalReview());
        mvc.perform(
            delete("/review/"+uuid+"/"))
        		.andExpect(status().isOk());

    }

    @Test
    void testDeleteException() throws Exception{

        mvc.perform(
            delete("/review/"+uuid+"/"))
        		.andExpect(status().isNotFound());

    }
}
