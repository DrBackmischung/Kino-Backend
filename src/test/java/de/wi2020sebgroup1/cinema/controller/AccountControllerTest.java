package de.wi2020sebgroup1.cinema.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

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

import de.wi2020sebgroup1.cinema.configurationObject.EmailVariablesObject;
import de.wi2020sebgroup1.cinema.configurationObject.UserLoginObject;
import de.wi2020sebgroup1.cinema.configurationObject.UserRegistrationObject;
import de.wi2020sebgroup1.cinema.entities.City;
import de.wi2020sebgroup1.cinema.entities.Role;
import de.wi2020sebgroup1.cinema.entities.User;
import de.wi2020sebgroup1.cinema.repositories.CityRepository;
import de.wi2020sebgroup1.cinema.repositories.RoleRepository;
import de.wi2020sebgroup1.cinema.repositories.UserRepository;
import de.wi2020sebgroup1.cinema.services.EmailService;
import de.wi2020sebgroup1.cinema.services.HTMLService;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
@AutoConfigureMockMvc
public class AccountControllerTest {
	
	MockMvc mvc;

	@MockBean
	UserRepository userRepository;
	
	@MockBean
	CityRepository cityRepository;
	
	@MockBean
	RoleRepository roleRepository;
	
	@MockBean
	EmailService emailService;
	
	@MockBean
	HTMLService htmlService;
    
    @Autowired
    WebApplicationContext wac;
	
	JacksonTester<UserLoginObject> jt_ulo;
	JacksonTester<UserRegistrationObject> jt_uro;
	
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
    
    List<City> getCityList() {
    	List<City> l = new ArrayList<>();
    	l.add(new City(68159, "Mannheim"));
    	return l;
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
    
    Role getRole() {
    	Role s = new Role();
    	s.setID(uuid);
    	s.setAuthorization("USER");
    	return s;
    }
    
    Optional<Role> getOptionalRole() {
    	Role r = getRole();
    	return Optional.of(r);
    }
    
    List<City> getEmptyCityList() {
    	List<City> l = new ArrayList<>();
    	return l;
    }
    
    
    @Test
    void testRegister() throws Exception {
    	when(cityRepository.findByPlz(anyInt())).thenReturn(getCityList());
    	when(roleRepository.findByAuthorization("USER")).thenReturn(getOptionalRole());
        mvc.perform(put("/registration/")
        		.contentType(MediaType.APPLICATION_JSON).content(jt_uro.write(new UserRegistrationObject("DrBackmischung", "Mathis", "Neunzig", "mathis.neunzig@gmail.com", "1234", "1234", "Parkring", "21", 68159, "Mannheim")).getJson()))
				.andExpect(status().isCreated());
    	
        when(cityRepository.findByPlz(anyInt())).thenReturn(getCityList());
    	when(roleRepository.findByAuthorization("USER")).thenReturn(getOptionalRole());
        mvc.perform(put("/registration/")
        		.contentType(MediaType.APPLICATION_JSON).content(jt_uro.write(new UserRegistrationObject("Tsawlen", "Tomke", "Müller", "jost-tomke-mueller@t-online.de", "1234", "1234", "Lichtenau", "5", 35315, "Homberg (Ohm)")).getJson()))
				.andExpect(status().isCreated());
    }
    
    @Test
    void testRegisterMail() throws Exception {
    	
    	Properties properties = new Properties();
	    properties.put("mail.smtp.auth",  "true");
	    properties.put("mail.smtp.starttls.enable", "true");
	    properties.put("mail.smtp.host", "smtp.gmail.com");
	    properties.put("mail.smtp.port", "587");
	    Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("wwi2020seb@gmail.com", "Kino2020SEB");
            }
        });
	    EmailVariablesObject e = new EmailVariablesObject("DrBackmischung", "Mathis", "Neunzig", null, null, null, null, null, null, null, null);
    	when(emailService.prepareMessage(session, "wwi2020seb@gmail.com", "mathis.neunzig@gmail.com", "Registration completed!", e, "Registration.html")).thenReturn(new MimeMessage(session));
    	when(htmlService.read("Registration.html", e)).thenReturn("<h1>Test</h1>");
    	when(cityRepository.findByPlz(anyInt())).thenReturn(getCityList());
    	when(roleRepository.findByAuthorization("USER")).thenReturn(getOptionalRole());
        mvc.perform(put("/registration/")
        		.contentType(MediaType.APPLICATION_JSON).content(jt_uro.write(new UserRegistrationObject("DrBackmischung", "Mathis", "Neunzig", "mathis.neunzig@gmail.com", "1234", "1234", "Parkring", "21", 68159, "Mannheim")).getJson()))
				.andExpect(status().isCreated());
    }
    
    
    @Test
    void testRegisterException() throws Exception {
        mvc.perform(put("/registration/")
        		.contentType(MediaType.APPLICATION_JSON).content(jt_uro.write(new UserRegistrationObject("DrBackmischung", "Mathis", "Neunzig", "mathis.neunzig@gmail.com", "1234", "4321", "Parkring", "21", 68159, "Mannheim")).getJson()))
				.andExpect(status().isUnauthorized());when(cityRepository.findByPlz(anyInt())).thenReturn(getCityList());
    }
    
    @Test
    void testLogin() throws Exception {
    	when(userRepository.findByUsername(any())).thenReturn(getOptionalUser());
        mvc.perform(put("/login/")
        		.contentType(MediaType.APPLICATION_JSON).content(jt_ulo.write(new UserLoginObject("DrBackmischung", "1234")).getJson()))
				.andExpect(status().isOk());
    }
    
    @Test
    void testLoginException() throws Exception {
        mvc.perform(put("/login/")
        		.contentType(MediaType.APPLICATION_JSON).content(jt_ulo.write(new UserLoginObject("DrBackmischung", "1234")).getJson()))
				.andExpect(status().isNotFound());
    	when(userRepository.findByUsername(any())).thenReturn(getOptionalUser());
        mvc.perform(put("/login/")
        		.contentType(MediaType.APPLICATION_JSON).content(jt_ulo.write(new UserLoginObject("DrBackmischung", "4321")).getJson()))
				.andExpect(status().isUnauthorized());
    }
	
}
