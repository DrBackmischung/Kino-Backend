package de.wi2020sebgroup1.cinema.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
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

import de.wi2020sebgroup1.cinema.configurationObject.TicketConfigurationObject;
import de.wi2020sebgroup1.cinema.entities.Price;
import de.wi2020sebgroup1.cinema.entities.Seat;
import de.wi2020sebgroup1.cinema.entities.Show;
import de.wi2020sebgroup1.cinema.entities.Ticket;
import de.wi2020sebgroup1.cinema.entities.User;
import de.wi2020sebgroup1.cinema.enums.SeatState;
import de.wi2020sebgroup1.cinema.enums.SeatType;
import de.wi2020sebgroup1.cinema.repositories.PriceRepository;
import de.wi2020sebgroup1.cinema.repositories.SeatRepository;
import de.wi2020sebgroup1.cinema.repositories.ShowRepository;
import de.wi2020sebgroup1.cinema.repositories.TicketRepository;
import de.wi2020sebgroup1.cinema.repositories.UserRepository;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
@AutoConfigureMockMvc
public class TicketControllerTest {
	
	MockMvc mvc;
	
	@MockBean
	TicketRepository repo;
	
	@MockBean
	UserRepository userRepository;
	
	@MockBean
	SeatRepository seatRepository;
	
	@MockBean
	PriceRepository priceRepository;
	
	@MockBean
	ShowRepository showRepository;
    
	@Autowired
    WebApplicationContext wac;
	
	JacksonTester<Ticket> jt;
	JacksonTester<TicketConfigurationObject> jtco;
	
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
    
    Ticket getTicket() {
    	Ticket t = new Ticket();
    	t.setSeat(getSeat(false));
    	t.setId(uuid);
    	return t;
    }
    
    Optional<Ticket> getOptionalTicket() {
    	Ticket t = getTicket();
    	return Optional.of(t);
    }
    
    User getUser() {
    	User s = new User();
    	s.setId(uuid);
    	return s;
    }
    
    Optional<User> getOptionalUser() {
    	User s = getUser();
    	return Optional.of(s);
    }
    
    Seat getSeat(boolean blocked) {
    	Seat s = new Seat();
    	s.setId(uuid);
    	if(blocked)
    		s.setState(SeatState.RESERVED);
    	else
    		s.setState(SeatState.FREE);
    	return s;
    }
    
    Optional<Seat> getOptionalSeat(boolean blocked) {
    	Seat s = getSeat(blocked);
    	return Optional.of(s);
    }
    
    Price getPrice() {
    	Price s = new Price(20, SeatType.PARQUET);
    	s.setId(uuid);
    	return s;
    }
    
    Optional<Price> getOptionalPrice() {
    	Price s = getPrice();
    	return Optional.of(s);
    }
    
    Show getShow() {
    	Show s = new Show();
    	s.setId(uuid);
    	return s;
    }
    
    Optional<Show> getOptionalShow() {
    	Show s = getShow();
    	return Optional.of(s);
    }
    
    @Test
    void testGetAll() throws Exception {
    	when(repo.findAll()).thenReturn(new ArrayList<Ticket>());
        mvc.perform(get("/ticket/getAll"))
                .andExpect(status().isOk());
    }
    
    @Test
    void testGetById() throws Exception {
        when(repo.findById(uuid)).thenReturn(getOptionalTicket());
        MockHttpServletResponse response = mvc.perform(get("/ticket/"+uuid)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn().getResponse();
        assertEquals(jt.write(getTicket()).getJson(), response.getContentAsString());
    }
    
    @Test
    void testGetByIdException() throws Exception {
        mvc.perform(get("/ticket/"+new UUID(0, 0))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void testPut() throws Exception{
    	
        when(seatRepository.findById(uuid)).thenReturn(getOptionalSeat(false));
        when(showRepository.findById(uuid)).thenReturn(getOptionalShow());
        when(priceRepository.findById(uuid)).thenReturn(getOptionalPrice());
        when(userRepository.findById(uuid)).thenReturn(getOptionalUser());
        
        mvc.perform(
            put("/ticket/add/").contentType(MediaType.APPLICATION_JSON).content(jtco.write(new TicketConfigurationObject(uuid, uuid, uuid, uuid)).getJson()))
        		.andExpect(status().isCreated());

    }

    @Test
    void testPutException() throws Exception{
    	
        when(seatRepository.findById(uuid)).thenReturn(getOptionalSeat(true));
        when(showRepository.findById(uuid)).thenReturn(getOptionalShow());
        when(priceRepository.findById(uuid)).thenReturn(getOptionalPrice());
        when(userRepository.findById(uuid)).thenReturn(getOptionalUser());
        
        mvc.perform(
            put("/ticket/add/").contentType(MediaType.APPLICATION_JSON).content(jtco.write(new TicketConfigurationObject(uuid, uuid, uuid, uuid)).getJson()))
        		.andExpect(status().isNotAcceptable());

    }

    @Test
    void testPutShowException() throws Exception{
    	
        when(seatRepository.findById(uuid)).thenReturn(getOptionalSeat(false));
        when(showRepository.findById(uuid)).thenThrow(new IllegalArgumentException());
        when(priceRepository.findById(uuid)).thenReturn(getOptionalPrice());
        when(userRepository.findById(uuid)).thenReturn(getOptionalUser());
        
        mvc.perform(
            put("/ticket/add/").contentType(MediaType.APPLICATION_JSON).content(jtco.write(new TicketConfigurationObject(uuid, uuid, uuid, uuid)).getJson()))
        		.andExpect(status().isNotFound());

    }

    @Test
    void testPutPriceException() throws Exception{
    	
        when(seatRepository.findById(uuid)).thenReturn(getOptionalSeat(false));
        when(showRepository.findById(uuid)).thenReturn(getOptionalShow());
        when(priceRepository.findById(uuid)).thenThrow(new IllegalArgumentException());
        when(userRepository.findById(uuid)).thenReturn(getOptionalUser());
        
        mvc.perform(
            put("/ticket/add/").contentType(MediaType.APPLICATION_JSON).content(jtco.write(new TicketConfigurationObject(uuid, uuid, uuid, uuid)).getJson()))
        		.andExpect(status().isNotFound());

    }

    @Test
    void testPutUserException() throws Exception{
    	
        when(seatRepository.findById(uuid)).thenReturn(getOptionalSeat(false));
        when(showRepository.findById(uuid)).thenReturn(getOptionalShow());
        when(priceRepository.findById(uuid)).thenReturn(getOptionalPrice());
        when(userRepository.findById(uuid)).thenThrow(new IllegalArgumentException());
        
        mvc.perform(
            put("/ticket/add/").contentType(MediaType.APPLICATION_JSON).content(jtco.write(new TicketConfigurationObject(uuid, uuid, uuid, uuid)).getJson()))
        		.andExpect(status().isNotFound());

    }

    @Test
    void testCancel() throws Exception{
    	
        when(repo.findById(uuid)).thenReturn(getOptionalTicket());
        when(seatRepository.findById(uuid)).thenReturn(getOptionalSeat(true));
    	
    	mvc.perform(get("/ticket/cancel/"+uuid)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void testCancelException() throws Exception{
    	
    	mvc.perform(get("/ticket/cancel/"+uuid)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    	
        when(repo.findById(uuid)).thenReturn(getOptionalTicket());
    	
    	mvc.perform(get("/ticket/cancel/"+uuid)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }
	
}
