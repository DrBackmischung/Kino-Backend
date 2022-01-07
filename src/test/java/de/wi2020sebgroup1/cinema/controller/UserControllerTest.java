package de.wi2020sebgroup1.cinema.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import de.wi2020sebgroup1.cinema.configurationObject.UserConfigurationObject;
import de.wi2020sebgroup1.cinema.entities.City;
import de.wi2020sebgroup1.cinema.entities.CreditCard;
import de.wi2020sebgroup1.cinema.entities.Role;
import de.wi2020sebgroup1.cinema.entities.User;
import de.wi2020sebgroup1.cinema.repositories.CityRepository;
import de.wi2020sebgroup1.cinema.repositories.CreditCardRepository;
import de.wi2020sebgroup1.cinema.repositories.RoleRepository;
import de.wi2020sebgroup1.cinema.repositories.UserRepository;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
@AutoConfigureMockMvc
public class UserControllerTest {

	MockMvc mvc;
	
	@MockBean
	UserRepository repo;
	
	@MockBean
	CityRepository cityRepository;
	
	@MockBean
	private RoleRepository roleRepository;
	
	@MockBean
	private CreditCardRepository creditCardRepository;
    
    @Autowired
    WebApplicationContext wac;
	
	JacksonTester<User> jt;
	JacksonTester<UserConfigurationObject> jtco;
	
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
    
    User getUser() {
    	User u = new User("DrBackmischung", "Mathis", "Neunzig", "mathis.neunzig@gmail.com", "abcde12345", null, null, null, null, null, null);
    	u.setId(uuid);
    	return u;
    }
    
    Optional<User> getOptionalUser() {
    	User u = getUser();
    	return Optional.of(u);
    }
    
    Role getRole() {
    	Role r = new Role();
    	r.setID(uuid);
    	return r;
    }
    
    Optional<Role> getOptionalRole() {
    	Role r = getRole();
    	return Optional.of(r);
    }
    
    CreditCard getCreditCard() {
    	CreditCard c = new CreditCard();
    	c.setId(uuid);
    	return c;
    }
    
    Optional<CreditCard> getOptionalCreditCard() {
    	CreditCard c = getCreditCard();
    	return Optional.of(c);
    }
    
    List<City> getCityList() {
    	List<City> l = new ArrayList<>();
    	l.add(new City(68159, "Mannheim"));
    	return l;
    }
    
    @Test
    void testGetAll() throws Exception {
    	when(repo.findAll()).thenReturn(new ArrayList<User>());
        mvc.perform(get("/user/getAll"))
                .andExpect(status().isOk());
    }
    
    @Test
    void testGetById() throws Exception {
        when(repo.findById(uuid)).thenReturn(getOptionalUser());
        MockHttpServletResponse response = mvc.perform(get("/user/"+uuid)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn().getResponse();
        assertEquals(jt.write(getUser()).getJson(), response.getContentAsString());
    }
    
    @Test
    void testGetByIdException() throws Exception {
        mvc.perform(get("/user/"+new UUID(0, 0))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void testPut() throws Exception{
        
        mvc.perform(
            put("/user/add/").contentType(MediaType.APPLICATION_JSON).content(jt.write(getUser()).getJson()))
        		.andExpect(status().isCreated());

    }

    @Test
    void testUpdate() throws Exception{

    	when(repo.findById(uuid)).thenReturn(getOptionalUser());
        mvc.perform(
            put("/user/"+uuid).contentType(MediaType.APPLICATION_JSON).content(jtco.write(new UserConfigurationObject(null, null, null, null, null, null, null, null, 0, null, null, null)).getJson()))
        		.andExpect(status().isOk());

    	when(repo.findById(uuid)).thenReturn(getOptionalUser());
    	when(cityRepository.findByPlz(anyInt())).thenReturn(getCityList());
    	when(creditCardRepository.findById(uuid)).thenReturn(getOptionalCreditCard());
    	when(roleRepository.findById(uuid)).thenReturn(getOptionalRole());
        mvc.perform(
            put("/user/"+uuid).contentType(MediaType.APPLICATION_JSON).content(jtco.write(new UserConfigurationObject(null, null, null, null, null, uuid, null, null, 68159, "Mannheim", null, uuid)).getJson()))
        		.andExpect(status().isOk());

    	when(repo.findById(uuid)).thenReturn(getOptionalUser());
    	when(cityRepository.findByPlz(anyInt())).thenReturn(getCityList());
        mvc.perform(
            put("/user/"+uuid).contentType(MediaType.APPLICATION_JSON).content(jtco.write(new UserConfigurationObject(null, null, null, null, null, uuid, null, null, 68199, "Mannheim", null, uuid)).getJson()))
        		.andExpect(status().isOk());

    }

    @Test
    void testUpdateException() throws Exception{

        mvc.perform(
            put("/user/"+uuid).contentType(MediaType.APPLICATION_JSON).content(jtco.write(new UserConfigurationObject(null, null, null, null, null, null, null, null, 0, null, null, null)).getJson()))
        		.andExpect(status().isNotFound());

    }

    @Test
    void testDelete() throws Exception{

    	when(repo.findById(uuid)).thenReturn(getOptionalUser());
        mvc.perform(
            delete("/user/"+uuid+"/"))
        		.andExpect(status().isOk());

    }

    @Test
    void testDeleteException() throws Exception{
        
        mvc.perform(
            delete("/user/"+uuid+"/"))
        		.andExpect(status().isNotFound());

    }

}
