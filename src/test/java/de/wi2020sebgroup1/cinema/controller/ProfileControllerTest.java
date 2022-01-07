package de.wi2020sebgroup1.cinema.controller;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
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
import de.wi2020sebgroup1.cinema.repositories.UserRepository;

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
	
	@MockBean
	UserRepository userRepository;
    
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
    
    Optional<User> getOptionalUser() {
    	User u = getUser();
    	return Optional.of(u);
    }
    
    Optional<List<Ticket>> getOptionalTickets() {
    	ArrayList<Ticket> list = new ArrayList<>();
    	list.add(new Ticket(TicketState.PAID, getUser(), null, null, null));
    	return Optional.of(list);
    }
    
    Optional<List<News>> getOptionalNews() {
    	List<News> list = new ArrayList<>();
    	list.add(new News(new Date(2), new Time(2), "head", "content", "link", getUser()));
    	return Optional.of(list);
    }
    
    Optional<List<Review>> getOptionalReviews() {
    	List<Review> list = new ArrayList<>();
    	list.add(new Review(new Date(2), new Time(2), "head", "content", null, getUser()));
    	return Optional.of(list);
    }
    
    Optional<List<Booking>> getOptionalBookings() {
    	List<Booking> list = new ArrayList<>();
    	list.add(new Booking(new Date(2), new ArrayList<Ticket>(), null, BookingState.Paid));
    	return Optional.of(list); 
    }
    
    @Test
    void testGetTickets() throws Exception {
    	when(userRepository.findById(uuid)).thenReturn(getOptionalUser());
    	when(ticketRepository.findAllByUser(any())).thenReturn(getOptionalTickets());
        mvc.perform(get("/user/"+uuid+"/tickets"))
                .andExpect(status().isOk());
    }
    
    @Test
    void testGetBookings() throws Exception {
    	when(userRepository.findById(uuid)).thenReturn(getOptionalUser());
    	when(bookingRepositroy.findAllByUser(any())).thenReturn(getOptionalBookings());
        mvc.perform(get("/user/"+uuid+"/bookings"))
                .andExpect(status().isOk());
    }
    
    @Test
    void testGetNews() throws Exception {
    	when(userRepository.findById(uuid)).thenReturn(getOptionalUser());
    	when(newsRepository.findAllByUser(any())).thenReturn(getOptionalNews());
        mvc.perform(get("/user/"+uuid+"/news"))
                .andExpect(status().isOk());
    }
    
    @Test
    void testGetReviews() throws Exception {
    	when(userRepository.findById(uuid)).thenReturn(getOptionalUser());
    	when(reviewRepository.findAllByUser(any())).thenReturn(getOptionalReviews());
        mvc.perform(get("/user/"+uuid+"/reviews"))
                .andExpect(status().isOk());
    }
    
    @Test
    void testGetTicketsException() throws Exception {
    	when(ticketRepository.findAllByUser(any())).thenReturn(getOptionalTickets());
        mvc.perform(get("/user/"+uuid+"/tickets"))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void testGetBookingsException() throws Exception {
    	when(bookingRepositroy.findAllByUser(any())).thenReturn(getOptionalBookings());
        mvc.perform(get("/user/"+uuid+"/bookings"))
                .andExpect(status().isOk());
    }
    
    @Test
    void testGetNewsException() throws Exception {
    	when(newsRepository.findAllByUser(any())).thenReturn(getOptionalNews());
        mvc.perform(get("/user/"+uuid+"/news"))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void testGetReviewsException() throws Exception {
    	when(reviewRepository.findAllByUser(any())).thenReturn(getOptionalReviews());
        mvc.perform(get("/user/"+uuid+"/reviews"))
                .andExpect(status().isNotFound());
    }

}
