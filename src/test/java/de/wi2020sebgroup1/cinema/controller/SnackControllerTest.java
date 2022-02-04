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

import de.wi2020sebgroup1.cinema.entities.Snack;
import de.wi2020sebgroup1.cinema.repositories.SnackRepository;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
@AutoConfigureMockMvc
public class SnackControllerTest {
	
	MockMvc mvc;
	
	@MockBean
	SnackRepository repo;
    
    @Autowired
    WebApplicationContext wac;
	
	JacksonTester<Snack> jt;
	
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
    
    Snack getSnack() {
    	Snack e = new Snack();
    	e.setId(uuid);
    	return e;
    }
    
    Optional<Snack> getOptionalSnack() {
    	Snack e = getSnack();
    	return Optional.of(e);
    }
    
    @Test
    void testGetAll() throws Exception {
    	when(repo.findAll()).thenReturn(new ArrayList<Snack>());
        mvc.perform(get("/snack/getAll"))
                .andExpect(status().isOk());
    }
    
    @Test
    void testGetById() throws Exception {
        when(repo.findById(uuid)).thenReturn(getOptionalSnack());
        MockHttpServletResponse response = mvc.perform(get("/snack/"+uuid)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn().getResponse();
        assertEquals(jt.write(getSnack()).getJson(), response.getContentAsString());
    }
    
    @Test
    void testGetByIdException() throws Exception {
        mvc.perform(get("/snack/"+new UUID(0, 0))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void testPut() throws Exception{

        mvc.perform(
            put("/snack/add/").contentType(MediaType.APPLICATION_JSON).content(jt.write(getSnack()).getJson()))
        		.andExpect(status().isCreated());

    }

    @Test
    void testDelete() throws Exception{

    	when(repo.findById(uuid)).thenReturn(getOptionalSnack());
        mvc.perform(
            delete("/snack/"+uuid+"/"))
        		.andExpect(status().isOk());

    }

    @Test
    void testDeleteException() throws Exception{

        mvc.perform(
            delete("/snack/"+uuid+"/"))
        		.andExpect(status().isNotFound());

    }
}
