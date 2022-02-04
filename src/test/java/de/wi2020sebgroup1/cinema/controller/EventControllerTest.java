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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.wi2020sebgroup1.cinema.entities.Event;
import de.wi2020sebgroup1.cinema.repositories.EventRepository;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
@AutoConfigureMockMvc
public class EventControllerTest {
	
	MockMvc mvc;
	
	@MockBean
	EventRepository repo;
    
    @Autowired
    WebApplicationContext wac;
	
	JacksonTester<Event> jt;
	
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
    
    Event getEvent() {
    	Event e = new Event();
    	e.setId(uuid);
    	return e;
    }
    
    Optional<Event> getOptionalEvent() {
    	Event e = getEvent();
    	return Optional.of(e);
    }
    
    @Test
    void testGetAll() throws Exception {
    	when(repo.findAll()).thenReturn(new ArrayList<Event>());
        mvc.perform(get("/event/getAll"))
                .andExpect(status().isOk());
    }
    
    @Test
    void testGetById() throws Exception {
        when(repo.findById(uuid)).thenReturn(getOptionalEvent());
        MockHttpServletResponse response = mvc.perform(get("/event/"+uuid)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn().getResponse();
        assertEquals(jt.write(getEvent()).getJson(), response.getContentAsString());
    }
    
    @Test
    void testGetByIdException() throws Exception {
        mvc.perform(get("/event/"+new UUID(0, 0))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void testPut() throws Exception{

        mvc.perform(
            put("/event/add/").contentType(MediaType.APPLICATION_JSON).content(jt.write(getEvent()).getJson()))
        		.andExpect(status().isCreated());

    }

    @Test
    void testDelete() throws Exception{

    	when(repo.findById(uuid)).thenReturn(getOptionalEvent());
        mvc.perform(
            delete("/event/"+uuid+"/"))
        		.andExpect(status().isOk());

    }

    @Test
    void testDeleteException() throws Exception{

        mvc.perform(
            delete("/event/"+uuid+"/"))
        		.andExpect(status().isNotFound());

    }
}
