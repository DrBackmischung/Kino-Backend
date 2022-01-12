package de.wi2020sebgroup1.cinema.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

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

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
@AutoConfigureMockMvc
public class ResponseControllerTest {
	
	MockMvc mvc;
	
	@MockBean
	ResponseRepository repo;
    
    @Autowired
    WebApplicationContext wac;
	
	JacksonTester<ResponseEntity> jt;
	
	static UUID uuid;

    @BeforeEach
    void beforeEach() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    
    ResponseEntity getResponseEntity() {
    	ResponseEntity c = new ResponseEntity(200, "Ok", "Alles Ok");
    	return c;
    }
    
    Optional<ResponseEntity> getOptionalResponseEntity() {
    	ResponseEntity c = getResponseEntity();
    	return Optional.of(c);
    }
    
    @Test
    void testGetAll() throws Exception {
    	when(repo.findAll()).thenReturn(new ArrayList<ResponseEntity>());
        mvc.perform(get("/error/getAll"))
                .andExpect(status().isOk());
    }
    
    @Test
    void testGetById() throws Exception {
        when(repo.findById(anyInt())).thenReturn(getOptionalResponseEntity());
        MockHttpServletResponse response = mvc.perform(get("/error/404")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn().getResponse();
        assertEquals(jt.write(getResponseEntity()).getJson(), response.getContentAsString());
    }
    
    @Test
    void testGetByIdException() throws Exception {
        mvc.perform(get("/error/0")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void testPut() throws Exception{
        
        mvc.perform(
            put("/error/add/").contentType(MediaType.APPLICATION_JSON).content(jt.write(getResponseEntity()).getJson()))
        		.andExpect(status().isCreated());

    }

    @Test
    void testUpdate() throws Exception{
        
    	when(repo.findById(anyInt())).thenReturn(getOptionalResponseEntity());
        mvc.perform(
            put("/error/404").contentType(MediaType.APPLICATION_JSON).content(jt.write(getResponseEntity()).getJson()))
        		.andExpect(status().isOk());

    }

    @Test
    void testUpdateException() throws Exception{
        
        mvc.perform(
            put("/error/404").contentType(MediaType.APPLICATION_JSON).content(jt.write(getResponseEntity()).getJson()))
        		.andExpect(status().isNotFound());

    }

}
