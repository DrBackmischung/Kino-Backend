package de.wi2020sebgroup1.cinema.controller;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
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
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.wi2020sebgroup1.cinema.entities.Movie;
import de.wi2020sebgroup1.cinema.entities.Review;
import de.wi2020sebgroup1.cinema.entities.Show;
import de.wi2020sebgroup1.cinema.repositories.MovieRepository;
import de.wi2020sebgroup1.cinema.repositories.ReviewRepository;
import de.wi2020sebgroup1.cinema.repositories.ShowRepository;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
@AutoConfigureMockMvc
public class MovieControllerTest {
	
	MockMvc mvc;
	
	@MockBean
	MovieRepository repo;
	
	@MockBean
	ShowRepository showRepository;
	
	@MockBean
	ReviewRepository reviewRepository;
    
    @Autowired
    WebApplicationContext wac;
	
	JacksonTester<Movie> jt;
	
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
    
    Movie getMovie() {
    	Movie m = new Movie("Shrek 3", "deutsch", null, 2.5, "Kitty Blume", "Ein Film", "localhost/img", null, null, null, null, 0);
    	m.setId(uuid);
    	return m;
    }
    
    Optional<Movie> getOptionalMovie() {
    	Movie m = getMovie();
    	return Optional.of(m);
    }
    
    Show getShow() {
    	Show s = new Show();
    	s.setId(uuid);
    	s.setMovie(getMovie());
    	s.setShowDate(java.sql.Date.valueOf(LocalDate.now()));
    	return s;
    }
    
    Optional<List<Show>> getOptionalShows() {
    	Show s = getShow();
    	Show s2 = getShow();
    	List<Show> l = new ArrayList<>();
    	l.add(s);
    	l.add(s2);
    	return Optional.of(l);
    }
    
    Optional<List<Review>> getOptionalReviews(){
    	List<Review> list = new ArrayList<>();
    	list.add(new Review());
    	return Optional.of(list);
    }
    
    @Test
    void testGetAll() throws Exception {
    	when(repo.findAll()).thenReturn(new ArrayList<Movie>());
        mvc.perform(get("/movie/getAll"))
                .andExpect(status().isOk());
    }
    
    @Test
    void testGetById() throws Exception {
        when(repo.findById(uuid)).thenReturn(getOptionalMovie());
        MockHttpServletResponse response = mvc.perform(get("/movie/"+uuid)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn().getResponse();
        assertEquals(jt.write(getMovie()).getJson(), response.getContentAsString());
    }
    
    @Test
    void testGetByIdException() throws Exception {
        mvc.perform(get("/movie/"+new UUID(0, 0))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }
    
    @Test
    void testGetReview() throws Exception {
        when(repo.findById(uuid)).thenReturn(getOptionalMovie());
        when(reviewRepository.findAllByMovie(getMovie())).thenReturn(getOptionalReviews());
        mvc.perform(get("/movie/"+uuid+"/reviews")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
    
    @Test
    void testGetReviewException() throws Exception {
        mvc.perform(get("/movie/"+uuid+"/reviews")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
        
        when(repo.findById(uuid)).thenReturn(getOptionalMovie());
        mvc.perform(get("/movie/"+uuid+"/reviews")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }
    
    @Test
    void testGetShowsByIdException() throws Exception {
        mvc.perform(get("/movie/"+uuid+"/shows")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

        when(repo.findById(uuid)).thenReturn(getOptionalMovie());
        mvc.perform(get("/movie/"+uuid+"/shows")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testPut() throws Exception{
        
        mvc.perform(
            put("/movie/add/").contentType(MediaType.APPLICATION_JSON).content(jt.write(getMovie()).getJson()))
        		.andExpect(status().isCreated());

    }

    @Test
    void testPutException() throws Exception{

        when(repo.save(any())).thenThrow(new IllegalArgumentException());
        mvc.perform(
            put("/movie/add/").contentType(MediaType.APPLICATION_JSON).content(jt.write(getMovie()).getJson()))
        		.andExpect(status().isInternalServerError());

    }

    @Test
    void testUpdate() throws Exception{

        when(repo.findById(uuid)).thenReturn(getOptionalMovie());
        mvc.perform(
            put("/movie/update/"+uuid, uuid, getMovie())
            	.contentType(MediaType.APPLICATION_JSON).content(jt.write(getMovie()).getJson()))
        		.andExpect(status().isOk());

    }

    @Test
    void testUpdateException() throws Exception{
        
        mvc.perform(
            put("/movie/update/"+uuid, uuid, getMovie())
            	.contentType(MediaType.APPLICATION_JSON).content(jt.write(getMovie()).getJson()))
        		.andExpect(status().isNotFound());

    }

    @Test
    void testDelete() throws Exception{

    	when(repo.findById(uuid)).thenReturn(getOptionalMovie());
        mvc.perform(
            delete("/movie/"+uuid+"/"))
        		.andExpect(status().isOk());

    }

    @Test
    void testDeleteException() throws Exception{
        
        mvc.perform(
            delete("/movie/"+uuid+"/"))
        		.andExpect(status().isNotFound());

    }
}

