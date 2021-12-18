package de.wi2020sebgroup1.cinema.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

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

import de.wi2020sebgroup1.cinema.entities.City;
import de.wi2020sebgroup1.cinema.repositories.CityRepository;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
@AutoConfigureMockMvc
public class CityControllerTest {
	
	MockMvc mvc;
	
	@MockBean
	CityRepository repo;
    
    @Autowired
    WebApplicationContext wac;
	
	JacksonTester<City> jt;
	
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
    
    City getCity() {
    	City c = new City(68159, "Mannheim");
    	c.setId(uuid);
    	return c;
    }
    
    Optional<City> getOptionalCity() {
    	City c = getCity();
    	return Optional.of(c);
    }
    
    List<City> getCityList() {
    	List<City> l = new ArrayList<>();
    	l.add(getCity());
    	return l;
    }
    
    @Test
    void testGetAll() throws Exception {
    	when(repo.findAll()).thenReturn(new ArrayList<City>());
        mvc.perform(get("/city/getAll"))
                .andExpect(status().isOk());
    }
    
    @Test
    void testGetById() throws Exception {
        when(repo.findById(uuid)).thenReturn(getOptionalCity());
        MockHttpServletResponse response = mvc.perform(get("/city/"+uuid)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn().getResponse();
        assertEquals(jt.write(getCity()).getJson(), response.getContentAsString());
    }
    
    @Test
    void testGetByIdException() throws Exception {
        mvc.perform(get("/city/"+new UUID(0, 0))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void testPut() throws Exception{
        
        mvc.perform(
            put("/city/add/").contentType(MediaType.APPLICATION_JSON).content(jt.write(getCity()).getJson()))
        		.andExpect(status().isCreated());

        when(repo.findByPlz(anyInt())).thenReturn(getCityList());
        mvc.perform(
            put("/city/add/").contentType(MediaType.APPLICATION_JSON).content(jt.write(getCity()).getJson()))
        		.andExpect(status().isOk());

    }

    @Test
    void testPutException() throws Exception{

        when(repo.save(any())).thenThrow(new IllegalArgumentException());
        mvc.perform(
            put("/city/add/").contentType(MediaType.APPLICATION_JSON).content(jt.write(getCity()).getJson()))
        		.andExpect(status().isNotAcceptable());

    }

    @Test
    void testDelete() throws Exception{

    	when(repo.findById(uuid)).thenReturn(getOptionalCity());
        mvc.perform(
            delete("/city/"+uuid+"/"))
        		.andExpect(status().isOk());

    }

    @Test
    void testDeleteException() throws Exception{
        
        mvc.perform(
            delete("/city/"+uuid+"/"))
        		.andExpect(status().isNotFound());

    }
}
