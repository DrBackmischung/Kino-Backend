package de.wi2020sebgroup1.cinema.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.wi2020sebgroup1.cinema.configurationObject.PWResetObject;
import de.wi2020sebgroup1.cinema.configurationObject.TokenConfigurationObject;
import de.wi2020sebgroup1.cinema.entities.Token;
import de.wi2020sebgroup1.cinema.entities.User;
import de.wi2020sebgroup1.cinema.repositories.TokenRepository;
import de.wi2020sebgroup1.cinema.repositories.UserRepository;
import de.wi2020sebgroup1.cinema.services.EmailService;
import de.wi2020sebgroup1.cinema.services.HTMLService;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
@AutoConfigureMockMvc
public class TokenControllerTest {
	
	MockMvc mvc;
	
	@MockBean
	TokenRepository repo;
	
	@MockBean
	UserRepository userRepository;
	
	@MockBean
	EmailService emailService;
	
	@MockBean
	HTMLService htmlService;
    
    @Autowired
    WebApplicationContext wac;
	
	JacksonTester<PWResetObject> jtpwro;
	JacksonTester<TokenConfigurationObject> jtco;
	
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
    	User s = new User();
    	s.setId(uuid);
    	s.setPassword("1234");
    	return s;
    }
    
    Optional<User> getOptionalUser() {
    	User s = getUser();
    	return Optional.of(s);
    }
    
    Token getToken() {
    	Token s = new Token();
    	s.setUser(getUser());
    	s.setId(uuid);
    	s.setValid(true);
    	return s;
    }
    
    Optional<Token> getOptionalToken() {
    	Token s = getToken();
    	return Optional.of(s);
    }
    
    Token getTokenNV() {
    	Token s = new Token();
    	s.setUser(getUser());
    	s.setId(uuid);
    	s.setValid(false);
    	return s;
    }
    
    Optional<Token> getOptionalTokenNV() {
    	Token s = getTokenNV();
    	return Optional.of(s);
    }
    
    @Test
    void testReset() throws Exception {
        mvc.perform(put("/reset/")
        		.contentType(MediaType.APPLICATION_JSON).content(jtco.write(new TokenConfigurationObject(true, null)).getJson()))
				.andExpect(status().isCreated());
    	when(userRepository.findById(uuid)).thenReturn(getOptionalUser());
        mvc.perform(put("/reset/")
        		.contentType(MediaType.APPLICATION_JSON).content(jtco.write(new TokenConfigurationObject(true, uuid)).getJson()))
				.andExpect(status().isCreated());
    }
    
    @Test
    void testResetException() throws Exception {
        mvc.perform(put("/reset/")
        		.contentType(MediaType.APPLICATION_JSON).content(jtco.write(new TokenConfigurationObject(true, uuid)).getJson()))
				.andExpect(status().isNotFound());
    }
    
    @Test
    void testResetFinal() throws Exception {
    	when(repo.findById(uuid)).thenReturn(getOptionalToken());
    	when(userRepository.findById(uuid)).thenReturn(getOptionalUser());
        mvc.perform(put("/reset/confirm")
        		.contentType(MediaType.APPLICATION_JSON).content(jtpwro.write(new PWResetObject("password", uuid, uuid)).getJson()))
				.andExpect(status().isCreated());
    }
    
    @Test
    void testResetFinalException() throws Exception {
    	
        mvc.perform(put("/reset/confirm")
        		.contentType(MediaType.APPLICATION_JSON).content(jtpwro.write(new PWResetObject("password", uuid, uuid)).getJson()))
				.andExpect(status().isNotFound());
        
    	when(repo.findById(uuid)).thenReturn(getOptionalToken());
        mvc.perform(put("/reset/confirm")
        		.contentType(MediaType.APPLICATION_JSON).content(jtpwro.write(new PWResetObject("password", uuid, new UUID(5,5))).getJson()))
				.andExpect(status().isUnauthorized());
        
    	when(repo.findById(uuid)).thenReturn(getOptionalTokenNV());
        mvc.perform(put("/reset/confirm")
        		.contentType(MediaType.APPLICATION_JSON).content(jtpwro.write(new PWResetObject("password", uuid, uuid)).getJson()))
				.andExpect(status().isUnauthorized());
        
    	when(repo.findById(uuid)).thenReturn(getOptionalToken());
        mvc.perform(put("/reset/confirm")
        		.contentType(MediaType.APPLICATION_JSON).content(jtpwro.write(new PWResetObject("password", uuid, uuid)).getJson()))
				.andExpect(status().isNotFound());
    }
    
//    @Test
//    void testCheck() throws Exception {
//    	when(repo.findById(uuid)).thenReturn(getOptionalToken());
//        mvc.perform(get("/check/"+uuid)
//            .accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk());
//    }
//    
//    @Test
//    void testCheckException() throws Exception {
//        mvc.perform(get("/check/"+uuid)
//            .accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNotFound());
//    }

}
