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
import org.mockito.InjectMocks;
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

import de.wi2020sebgroup1.cinema.configurationObject.ShowConfigurationObject;
import de.wi2020sebgroup1.cinema.entities.Show;
import de.wi2020sebgroup1.cinema.repositories.CinemaRepository;
import de.wi2020sebgroup1.cinema.repositories.CinemaRoomRepository;
import de.wi2020sebgroup1.cinema.repositories.CinemaRoomSeatingPlanRepository;
import de.wi2020sebgroup1.cinema.repositories.MovieRepository;
import de.wi2020sebgroup1.cinema.repositories.SeatRepository;
import de.wi2020sebgroup1.cinema.repositories.ShowRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class ShowControllerTest {
	
	MockMvc mvc;
	
	@InjectMocks
	private ShowController showcontroller;
	
	@MockBean
	ShowRepository repo;
	
	@MockBean
	CinemaRepository cinemaRepository;
	
	@MockBean
	MovieRepository movieRepository;
	
	@MockBean
	CinemaRoomRepository cinemaRoomRepository;
	
	@MockBean
	CinemaRoomSeatingPlanRepository seatingPlanRepository;
	
	@MockBean
	SeatRepository seatRepository;
    
    @Autowired
    WebApplicationContext wac;
	
	JacksonTester<Show> jt;
	JacksonTester<ShowConfigurationObject> jtco;
	
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
    
    Show getShow() {
    	Show s = new Show();
    	s.setId(uuid);
    	return s;
    }
    
    Optional<Show> getOptionalShow() {
    	Show s = getShow();
    	return Optional.of(s);
    }
    
    @Test
    void testGetAll() throws Exception {
    	when(repo.findAll()).thenReturn(new ArrayList<Show>());
        mvc.perform(get("/show/getAll"))
                .andExpect(status().isOk());
    }
    
    @Test
    void testGetById() throws Exception {
        when(repo.findById(uuid)).thenReturn(getOptionalShow());
        MockHttpServletResponse response = mvc.perform(get("/show/"+uuid)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn().getResponse();
        assertEquals(jt.write(getShow()).getJson(), response.getContentAsString());
    }
    
    @Test
    void testGetByIdException() throws Exception {
        mvc.perform(get("/show/"+new UUID(0, 0))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void testPut() throws Exception{
        
        mvc.perform(
            put("/show/add/").contentType(MediaType.APPLICATION_JSON).content(jt.write(getShow()).getJson()))
        		.andExpect(status().isCreated());

    }

    @Test
    void testPutException() throws Exception{
        
        mvc.perform(
            put("/show/add/")
            	.contentType(MediaType.APPLICATION_JSON).content(jtco.write(new ShowConfigurationObject(new Date(1), new Time(1), new Time(1), null, uuid, uuid)).getJson()))
        		.andExpect(status().isNotFound());
        
        mvc.perform(
            put("/show/add/")
            	.contentType(MediaType.APPLICATION_JSON).content(jtco.write(new ShowConfigurationObject(new Date(1), new Time(1), new Time(1), uuid, null, uuid)).getJson()))
        		.andExpect(status().isNotFound());
        
        mvc.perform(
            put("/show/add/")
            	.contentType(MediaType.APPLICATION_JSON).content(jtco.write(new ShowConfigurationObject(new Date(1), new Time(1), new Time(1), null, null, uuid)).getJson()))
        		.andExpect(status().isNotFound());

    }

    @Test
    void testDelete() throws Exception{
        
        mvc.perform(
            delete("/show/"+uuid+"/"))
        		.andExpect(status().isOk());

    }
}
