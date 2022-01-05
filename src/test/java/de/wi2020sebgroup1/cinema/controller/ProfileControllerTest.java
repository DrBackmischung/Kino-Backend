package de.wi2020sebgroup1.cinema.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.sql.Time;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.wi2020sebgroup1.cinema.entities.Booking;
import de.wi2020sebgroup1.cinema.entities.News;
import de.wi2020sebgroup1.cinema.entities.Review;
import de.wi2020sebgroup1.cinema.entities.Ticket;
import de.wi2020sebgroup1.cinema.entities.User;
import de.wi2020sebgroup1.cinema.enums.BookingState;
import de.wi2020sebgroup1.cinema.enums.TicketState;
import de.wi2020sebgroup1.cinema.repositories.BookingRepositroy;
import de.wi2020sebgroup1.cinema.repositories.NewsRepository;
import de.wi2020sebgroup1.cinema.repositories.ReviewRepository;
import de.wi2020sebgroup1.cinema.repositories.TicketRepository;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
@AutoConfigureMockMvc
public class ProfileControllerTest {
	
	MockMvc mvc;
	
	@MockBean
	BookingRepositroy bookingRepositroy;
	
	@MockBean
	TicketRepository ticketRepository;

	@MockBean
	NewsRepository newsRepository;
	
	@MockBean
	ReviewRepository reviewRepository;
    
    @Autowired
    WebApplicationContext wac;
	
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
    	User u = new User();
    	u.setId(uuid);
    	return u;
    }
    
    ArrayList<Ticket> getTickets() {
    	ArrayList<Ticket> list = new ArrayList<>();
    	list.add(new Ticket(TicketState.PAID, getUser(), null, null, null));
    	return list;
    }
    
    List<News> getNews() {
    	List<News> list = new ArrayList<>();
    	list.add(new News(new Date(2), new Time(2), "head", "content", "link", getUser()));
    	return list;
    }
    
    List<Review> getReviews() {
    	List<Review> list = new ArrayList<>();
    	list.add(new Review(new Date(2), new Time(2), "head", "content", null, getUser()));
    	return list;
    }
    
    Booking getBooking() {
    	Booking p = new Booking(new Date(2), getTickets(), null, BookingState.Canceled);
    	p.setId(uuid);
    	return p;
    }
    
    Optional<Booking> getOptionalBooking() {
    	Booking p = getBooking();
    	return Optional.of(p);
    }
    
    @Test
    void testGetTickets() throws Exception {
    	when(ticketRepository.findAll()).thenReturn(getTickets());
        mvc.perform(get("/user/"+uuid+"/tickets"))
                .andExpect(status().isOk());
    }
    
    @Test
    void testGetBookings() throws Exception {
    	when(ticketRepository.findAll()).thenReturn(getTickets());
    	when(bookingRepositroy.findById(uuid)).thenReturn(getOptionalBooking());
        mvc.perform(get("/user/"+uuid+"/tickets"))
                .andExpect(status().isOk());
    }
    
    @Test
    void testGetNews() throws Exception {
    	when(newsRepository.findAll()).thenReturn(getNews());
        mvc.perform(get("/user/"+uuid+"/news"))
                .andExpect(status().isOk());
    }
    
    @Test
    void testGetReviews() throws Exception {
    	when(reviewRepository.findAll()).thenReturn(getReviews());
        mvc.perform(get("/user/"+uuid+"/reviews"))
                .andExpect(status().isOk());
    }

}
