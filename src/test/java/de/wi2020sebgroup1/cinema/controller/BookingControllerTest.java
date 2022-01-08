package de.wi2020sebgroup1.cinema.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
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

import de.wi2020sebgroup1.cinema.configurationObject.BookingConfigurationObject;
import de.wi2020sebgroup1.cinema.entities.Booking;
import de.wi2020sebgroup1.cinema.entities.Seat;
import de.wi2020sebgroup1.cinema.entities.Show;
import de.wi2020sebgroup1.cinema.entities.Ticket;
import de.wi2020sebgroup1.cinema.entities.User;
import de.wi2020sebgroup1.cinema.enums.BookingState;
import de.wi2020sebgroup1.cinema.enums.SeatState;
import de.wi2020sebgroup1.cinema.repositories.BookingRepositroy;
import de.wi2020sebgroup1.cinema.repositories.SeatRepository;
import de.wi2020sebgroup1.cinema.repositories.ShowRepository;
import de.wi2020sebgroup1.cinema.repositories.TicketRepository;
import de.wi2020sebgroup1.cinema.repositories.UserRepository;
import de.wi2020sebgroup1.cinema.services.QRCodeGenerator;
import de.wi2020sebgroup1.cinema.services.SeatService;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
@AutoConfigureMockMvc
public class BookingControllerTest {
	
	MockMvc mvc;
	
	@MockBean
	BookingRepositroy repo;
	
	@MockBean
	TicketRepository ticketRepository;
	
	@MockBean
	UserRepository userRepositroy;
	
	@MockBean
	ShowRepository showRepository;
	
	@MockBean
	SeatRepository seatRepository;
	
	@MockBean
	SeatService seatService;
	
	@MockBean
	QRCodeGenerator qrCodeGenerator;
    
    @Autowired
    WebApplicationContext wac;
	
	JacksonTester<Booking> jt;
	JacksonTester<BookingConfigurationObject> jtco;
	
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
    
    Booking getBooking() {
    	Booking b = new Booking();
    	ArrayList<Ticket> l = new ArrayList<>();
    	l.add(getTicket());
    	b.setTickets(l);
    	b.setState(BookingState.Reserved);
    	b.setId(uuid);
    	return b;
    }
    
    Optional<Booking> getOptionalBooking() {
    	Booking b = getBooking();
    	return Optional.of(b);
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
    
    Show getShow() {
    	Show s = new Show();
    	s.setId(uuid);
    	return s;
    }
    
    Optional<Show> getOptionalShow() {
    	Show s = getShow();
    	return Optional.of(s);
    }
    
    ArrayList<UUID> getIDs() {
    	ArrayList<UUID> ids = new ArrayList<>();
    	ids.add(uuid);
    	return ids;
    }
    
    @Test
    void testGetAll() throws Exception {
    	when(repo.findAll()).thenReturn(new ArrayList<Booking>());
        mvc.perform(get("/booking/getAll"))
                .andExpect(status().isOk());
    }
    
    @Test
    void testGetById() throws Exception {
        when(repo.findById(uuid)).thenReturn(getOptionalBooking());
        MockHttpServletResponse response = mvc.perform(get("/booking/"+uuid)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn().getResponse();
        assertEquals(jt.write(getBooking()).getJson(), response.getContentAsString());
    }
    
    @Test
    void testGetByIdException() throws Exception {
        mvc.perform(get("/booking/"+new UUID(0, 0))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void testPut() throws Exception{
    	when(seatService.reserveSeats(getIDs(), uuid)).thenReturn(true);
    	when(userRepositroy.findById(uuid)).thenReturn(getOptionalUser());
    	when(showRepository.findById(uuid)).thenReturn(getOptionalShow());
    	mvc.perform(put("/booking/add/")
        		.contentType(MediaType.APPLICATION_JSON).content(jtco.write(new BookingConfigurationObject(new Date(2), uuid, uuid, getIDs(), BookingState.Paid)).getJson()))
				.andExpect(status().isCreated());
    }

    @Test
    void testPutException() throws Exception{
    	when(showRepository.findById(uuid)).thenReturn(getOptionalShow());
    	mvc.perform(put("/booking/add/")
        		.contentType(MediaType.APPLICATION_JSON).content(jtco.write(new BookingConfigurationObject(new Date(2), uuid, uuid, getIDs(), BookingState.Paid)).getJson()))
				.andExpect(status().isConflict());
    	when(seatService.reserveSeats(getIDs(), uuid)).thenReturn(true);
    	mvc.perform(put("/booking/add/")
        		.contentType(MediaType.APPLICATION_JSON).content(jtco.write(new BookingConfigurationObject(new Date(2), uuid, uuid, getIDs(), BookingState.Paid)).getJson()))
				.andExpect(status().isConflict());
    }

    @Test
    void testUpdate() throws Exception{
    	when(repo.findById(uuid)).thenReturn(getOptionalBooking());
    	mvc.perform(put("/booking/"+uuid+"/changeStatus/")
        		.contentType(MediaType.APPLICATION_JSON).content(jtco.write(new BookingConfigurationObject(new Date(2), uuid, uuid, getIDs(), BookingState.Canceled)).getJson()))
				.andExpect(status().isOk());
    	mvc.perform(put("/booking/"+uuid+"/changeStatus/")
        		.contentType(MediaType.APPLICATION_JSON).content(jtco.write(new BookingConfigurationObject(new Date(2), uuid, uuid, getIDs(), BookingState.Paid)).getJson()))
				.andExpect(status().isOk());
    	mvc.perform(put("/booking/"+uuid+"/changeStatus/")
        		.contentType(MediaType.APPLICATION_JSON).content(jtco.write(new BookingConfigurationObject(new Date(2), uuid, uuid, getIDs(), BookingState.Reserved)).getJson()))
				.andExpect(status().isNotModified());
    }

    @Test
    void testUpdateException() throws Exception{
    	mvc.perform(put("/booking/"+uuid+"/changeStatus/")
        		.contentType(MediaType.APPLICATION_JSON).content(jtco.write(new BookingConfigurationObject(new Date(2), uuid, uuid, getIDs(), BookingState.Canceled)).getJson()))
				.andExpect(status().isNotFound());
    }
    
}
