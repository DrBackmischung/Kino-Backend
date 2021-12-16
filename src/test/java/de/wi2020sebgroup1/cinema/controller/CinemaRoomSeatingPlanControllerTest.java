package de.wi2020sebgroup1.cinema.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import de.wi2020sebgroup1.cinema.configurationObject.CinemaRoomSeattingPlanConfigurationObject;
import de.wi2020sebgroup1.cinema.entities.CinemaRoom;
import de.wi2020sebgroup1.cinema.entities.CinemaRoomSeatingPlan;
import de.wi2020sebgroup1.cinema.repositories.CinemaRoomRepository;
import de.wi2020sebgroup1.cinema.repositories.CinemaRoomSeatingPlanRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class CinemaRoomSeatingPlanControllerTest {
	
	MockMvc mvc;
	
	@MockBean
	CinemaRoomSeatingPlanRepository repo;
	
	@MockBean
	CinemaRoomRepository cinemaRoomRepository;
    
    @Autowired
    WebApplicationContext wac;
	
	JacksonTester<CinemaRoomSeatingPlan> jt;
	JacksonTester<CinemaRoomSeattingPlanConfigurationObject> jtco;
	
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
    
    CinemaRoomSeatingPlan getCinemaRoomSeatingPlan() {
    	CinemaRoomSeatingPlan c = new CinemaRoomSeatingPlan(20);
    	c.setId(uuid);
    	return c;
    }
    
    Optional<CinemaRoomSeatingPlan> getOptionalCinemaRoomSeatingPlan() {
    	CinemaRoomSeatingPlan c = getCinemaRoomSeatingPlan();
    	return Optional.of(c);
    }
    
    CinemaRoom getCinemaRoom() {
    	CinemaRoom c = new CinemaRoom(2, true);
    	c.setId(uuid);
    	return c;
    }
    
    Optional<CinemaRoom> getOptionalCinemaRoom() {
    	CinemaRoom c = getCinemaRoom();
    	return Optional.of(c);
    }
    
    @Test
    void testGetAll() throws Exception {
    	when(repo.findAll()).thenReturn(new ArrayList<CinemaRoomSeatingPlan>());
        mvc.perform(get("/seatingPlan/getAll"))
                .andExpect(status().isOk());
    }
    
    @Test
    void testGetById() throws Exception {
        when(repo.findById(uuid)).thenReturn(getOptionalCinemaRoomSeatingPlan());
        MockHttpServletResponse response = mvc.perform(get("/seatingPlan/"+uuid)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn().getResponse();
        assertEquals(jt.write(getCinemaRoomSeatingPlan()).getJson(), response.getContentAsString());
    }
    
    @Test
    void testGetByIdException() throws Exception {
        mvc.perform(get("/seatingPlan/"+new UUID(0, 0))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void testPut() throws Exception{
    	
        when(cinemaRoomRepository.findById(uuid)).thenReturn(getOptionalCinemaRoom());
        mvc.perform(
            put("/seatingPlan/add/")
            	.contentType(MediaType.APPLICATION_JSON).content(jt.write(getCinemaRoomSeatingPlan()).getJson()))
        		.andExpect(status().isCreated());

    }

    @Test
    void testPutException() throws Exception{
        
        mvc.perform(
            put("/seatingPlan/add/")
            	.contentType(MediaType.APPLICATION_JSON).content(jtco.write(new CinemaRoomSeattingPlanConfigurationObject(20, 20, uuid)).getJson()))
        		.andExpect(status().isNotFound());

    }

    @Test
    void testUpdate() throws Exception{

        when(repo.findById(uuid)).thenReturn(getOptionalCinemaRoomSeatingPlan());
        when(cinemaRoomRepository.findById(uuid)).thenReturn(getOptionalCinemaRoom());
        mvc.perform(
            put("/seatingPlan/"+uuid, uuid, getCinemaRoomSeatingPlan())
            	.contentType(MediaType.APPLICATION_JSON).content(jt.write(getCinemaRoomSeatingPlan()).getJson()))
        		.andExpect(status().isNotFound());

    }

    @Test
    void testUpdateException() throws Exception{
        
        mvc.perform(
            put("/seatingPlan/"+uuid, uuid, getCinemaRoomSeatingPlan())
            	.contentType(MediaType.APPLICATION_JSON).content(jt.write(getCinemaRoomSeatingPlan()).getJson()))
        		.andExpect(status().isNotFound());

        when(repo.findById(uuid)).thenReturn(getOptionalCinemaRoomSeatingPlan());
        mvc.perform(
            put("/seatingPlan/"+uuid, uuid, getCinemaRoomSeatingPlan())
            	.contentType(MediaType.APPLICATION_JSON).content(jt.write(getCinemaRoomSeatingPlan()).getJson()))
        		.andExpect(status().isNotFound());

    }

    @Test
    void testDelete() throws Exception{
        
        mvc.perform(
            delete("/seatingPlan/"+uuid+"/"))
        		.andExpect(status().isOk());

    }

}
