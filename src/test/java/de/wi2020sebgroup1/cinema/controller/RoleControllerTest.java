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

import de.wi2020sebgroup1.cinema.entities.Role;
import de.wi2020sebgroup1.cinema.entities.User;
import de.wi2020sebgroup1.cinema.repositories.RoleRepository;
import de.wi2020sebgroup1.cinema.repositories.UserRepository;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
@AutoConfigureMockMvc
public class RoleControllerTest {
	
	MockMvc mvc;
	
	@MockBean
	RoleRepository repo;
	
	@MockBean
	UserRepository userRepository;
    
    @Autowired
    WebApplicationContext wac;
	
	JacksonTester<Role> jt;
	
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
    
    Role getRole() {
    	Role r = new Role("Admin", "ADMIN");
    	r.setID(uuid);
    	return r;
    }
    
    Optional<Role> getOptionalRole() {
    	Role r = getRole();
    	return Optional.of(r);
    }
    
    User getUser() {
    	User u = new User();
    	u.setRole(getRole());
    	return u;
    }
    
    Optional<User> getOptionalUser() {
    	User u = getUser();
    	return Optional.of(u);
    }
    
    @Test
    void testGetAll() throws Exception {
    	when(repo.findAll()).thenReturn(new ArrayList<Role>());
        mvc.perform(get("/role/getAll"))
                .andExpect(status().isOk());
    }
    
    @Test
    void testGetById() throws Exception {
        when(repo.findById(uuid)).thenReturn(getOptionalRole());
        MockHttpServletResponse response = mvc.perform(get("/role/"+uuid)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn().getResponse();
        assertEquals(jt.write(getRole()).getJson(), response.getContentAsString());
    }
    
    @Test
    void testGetByIdException() throws Exception {
        mvc.perform(get("/role/"+new UUID(0, 0))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }
    
    @Test
    void testGetByUserId() throws Exception {
        when(userRepository.findById(uuid)).thenReturn(getOptionalUser());
        mvc.perform(get("/role/user/"+uuid)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
    
    @Test
    void testGetByUserIdException() throws Exception {
        mvc.perform(get("/role/user/"+uuid)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void testPut() throws Exception{

        mvc.perform(
            put("/role/add/").contentType(MediaType.APPLICATION_JSON).content(jt.write(getRole()).getJson()))
        		.andExpect(status().isCreated());

    }

    @Test
    void testChange() throws Exception{

    	when(userRepository.findById(uuid)).thenReturn(getOptionalUser());
    	when(repo.findByAuthorization("ADMIN")).thenReturn(getOptionalRole());
        mvc.perform(
            put("/role/ADMIN/"+uuid).accept(MediaType.APPLICATION_JSON).content(""))
        		.andExpect(status().isOk());

    }

    @Test
    void testDelete() throws Exception{

    	when(repo.findById(uuid)).thenReturn(getOptionalRole());
        mvc.perform(
            delete("/role/"+uuid+"/"))
        		.andExpect(status().isOk());

    }

    @Test
    void testDeleteException() throws Exception{

        mvc.perform(
            delete("/role/"+uuid+"/"))
        		.andExpect(status().isNotFound());

    }
}
