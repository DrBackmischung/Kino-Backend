package de.wi2020sebgroup1.cinema.controller;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.wi2020sebgroup1.cinema.entities.Movie;
import de.wi2020sebgroup1.cinema.repositories.MovieRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerTest {
	
	MockMvc mvc;
	
	@MockBean
	MovieRepository repo;
    
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
    	Movie m = new Movie("Shrek 3", "deutsch", 2.5, "Kitty Blume", "Ein Film", "localhost/img");
    	m.setId(uuid);
    	return m;
    }
    
    Optional<Movie> getOptionalMovie() {
    	Movie m = getMovie();
    	return Optional.of(m);
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
    void testPut() throws Exception{
        
        mvc.perform(
            put("/movie/add/").contentType(MediaType.APPLICATION_JSON).content(jt.write(getMovie()).getJson()))
        		.andExpect(status().isCreated());

    }

    @Test
    void testUpdateException() throws Exception{
        
        mvc.perform(
            put("/movie/"+uuid, uuid, getMovie())
            	.contentType(MediaType.APPLICATION_JSON).content(jt.write(getMovie()).getJson()))
        		.andExpect(status().isNotFound());

    }

    @Test
    void testDelete() throws Exception{
        
        mvc.perform(
            delete("/movie/"+uuid+"/"))
        		.andExpect(status().isOk());

    }
}
