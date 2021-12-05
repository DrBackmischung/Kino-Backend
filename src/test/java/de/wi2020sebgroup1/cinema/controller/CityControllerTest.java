package de.wi2020sebgroup1.cinema.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.wi2020sebgroup1.cinema.entities.City;
import de.wi2020sebgroup1.cinema.repositories.CityRepository;

@SpringBootTest
public class CityControllerTest {
	
	MockMvc mvc;
	
	@Autowired
	@MockBean
	CityRepository repo;
    
    @Autowired
    WebApplicationContext wac;
	
	JacksonTester<City> jt;
	
	static City c1;
	
	@BeforeAll
	static void beforeAll() {
		
	}

    @BeforeEach
    void beforeEach() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    
    @Test
    void testGetAll() throws Exception {
    	when(repo.findAll()).thenReturn(new ArrayList<City>());
        mvc.perform(get("/city/getAll"))
                .andExpect(status().isOk());
    }
    
    @Test
    void testGetById() throws Exception {
    	when(repo.findAll()).thenReturn(cities);
        MvcResult result = this.mvc.perform(get("/city/getAll"))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
            
        assertTrue(result.getResponse().getContentAsString().contains("\"plz\":68159"));
        assertTrue(result.getResponse().getContentAsString().contains("\"plz\":26127"));
        assertTrue(result.getResponse().getContentAsString().contains("\"plz\":26133"));
    }

}
