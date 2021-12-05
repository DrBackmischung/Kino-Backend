package de.wi2020sebgroup1.cinema.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import de.wi2020sebgroup1.cinema.entities.City;
import de.wi2020sebgroup1.cinema.repositories.CityRepository;

@SpringBootTest
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
    
    // Das ganze hier ist notwendig, weil CrudRepository bei findById nur ein Optional<City> zurückgegeben wird, bei Jpa wäre das ein City.
    // Für den JacksonTester brauche ich aber eine City.
    City getCity() {
    	City c = new City(68159, "Mannheim");
    	c.setId(uuid);
    	return c;
    }
    
    Optional<City> getOptionalCity() {
    	City c = getCity();
    	return Optional.of(c);
    }
    
    @Test
    void testGetAll() throws Exception {
    	when(repo.findAll()).thenReturn(new ArrayList<City>());
        mvc.perform(get("/city/getAll"))
                .andExpect(status().isOk());
    }
    
    @Test
    void testGetById() throws Exception {	
    	Optional<City> c = getOptionalCity();
        when(repo.findById(uuid)).thenReturn(c);
        MockHttpServletResponse response = mvc.perform(get("/city/"+uuid)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is3xxRedirection())
            .andReturn().getResponse();
        assertEquals(jt.write(getCity()).getJson(), response.getContentAsString());
    }
}
