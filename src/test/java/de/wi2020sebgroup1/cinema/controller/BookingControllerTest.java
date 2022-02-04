package de.wi2020sebgroup1.cinema.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.sql.Time;
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
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.wi2020sebgroup1.cinema.configurationObject.BookingConfigurationObject;
import de.wi2020sebgroup1.cinema.configurationObject.EmailVariablesObject;
import de.wi2020sebgroup1.cinema.entities.Booking;
import de.wi2020sebgroup1.cinema.entities.CinemaRoom;
import de.wi2020sebgroup1.cinema.entities.Movie;
import de.wi2020sebgroup1.cinema.entities.Price;
import de.wi2020sebgroup1.cinema.entities.Seat;
import de.wi2020sebgroup1.cinema.entities.Show;
import de.wi2020sebgroup1.cinema.entities.Snack;
import de.wi2020sebgroup1.cinema.entities.Ticket;
import de.wi2020sebgroup1.cinema.entities.User;
import de.wi2020sebgroup1.cinema.enums.BookingState;
import de.wi2020sebgroup1.cinema.enums.SeatState;
import de.wi2020sebgroup1.cinema.enums.SeatType;
import de.wi2020sebgroup1.cinema.repositories.BookingRepositroy;
import de.wi2020sebgroup1.cinema.repositories.PriceRepository;
import de.wi2020sebgroup1.cinema.repositories.SeatRepository;
import de.wi2020sebgroup1.cinema.repositories.ShowRepository;
import de.wi2020sebgroup1.cinema.repositories.SnackRepository;
import de.wi2020sebgroup1.cinema.repositories.TicketRepository;
import de.wi2020sebgroup1.cinema.repositories.UserRepository;
import de.wi2020sebgroup1.cinema.services.EmailService;
import de.wi2020sebgroup1.cinema.services.HTMLService;
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
	SnackRepository snackRepository;
	
	@MockBean
	PriceRepository priceRepository;
	
	@MockBean
	SeatService seatService;
	
	@MockBean
	QRCodeGenerator qrCodeGenerator;
	
	@MockBean
	EmailService emailService;
	
	@MockBean
	HTMLService htmlService;
    
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
    
    Optional<List<Ticket>> getOptionalTicketList() {
    	List<Ticket> list = new ArrayList<>();
    	list.add(getTicket());
    	return Optional.of(list);
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
    	s.setType(SeatType.PARQUET);
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
    	Price p = new Price(18, SeatType.PARQUET);
    	p.setId(uuid);
    	return p;
    }
    
    Optional<Price> getOptionalPrice() {
    	Price p = getPrice();
    	return Optional.of(p);
    }
    
    Snack getSnack() {
    	Snack s = new Snack("big", "coke", "localhost", 0);
    	s.setId(uuid);
    	return s;
    }
    
    Optional<Snack> getOptionalSnack() {
    	Snack s = getSnack();
    	return Optional.of(s);
    }
    
    Show getShow() {
    	Show s = new Show();
    	s.setId(uuid);
    	s.setMovie(new Movie());
    	s.setCinemaRoom(new CinemaRoom());
    	s.setShowDate(new Date(1));
    	s.setStartTime(new Time(1));
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
    void testGetTickets() throws Exception {
        when(repo.findById(uuid)).thenReturn(getOptionalBooking());
        when(ticketRepository.findAllByBookingID(uuid)).thenReturn(getOptionalTicketList());
    	mvc.perform(get("/booking/"+uuid+"/tickets")
    		.accept(MediaType.APPLICATION_JSON))
    		.andExpect(status().isOk());
    }
    
    @Test
    void testGetTicketsException() throws Exception {
        when(repo.findById(uuid)).thenReturn(getOptionalBooking());
    	mvc.perform(get("/booking/"+uuid+"/tickets")
    		.accept(MediaType.APPLICATION_JSON))
    		.andExpect(status().isNotFound());
    }
    
    @Test
    void testGetTicketsExceptionNoBooking() throws Exception {
    	mvc.perform(get("/booking/"+uuid+"/tickets")
    		.accept(MediaType.APPLICATION_JSON))
    		.andExpect(status().isNotFound());
    }

    @SuppressWarnings({ "static-access", "deprecation" })
	@Test
    void testPut() throws Exception {
    	
    	when(userRepositroy.findById(uuid)).thenReturn(getOptionalUser());
    	when(showRepository.findById(uuid)).thenReturn(getOptionalShow());
    	when(seatService.reserveSeats(getIDs(), uuid)).thenReturn(true);
    	when(seatRepository.findById(uuid)).thenReturn(getOptionalSeat(false));
    	when(snackRepository.findById(uuid)).thenReturn(getOptionalSnack());
    	when(priceRepository.findByType(getSeat(false).getType())).thenReturn(getOptionalPrice());
    	
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
	    EmailVariablesObject e = new EmailVariablesObject(getUser().getUserName(), getUser().getFirstName(), getUser().getName(), "", "", getShow().getMovie().getTitle(), getShow().getShowDate().getDay()+"."+getShow().getShowDate().getMonth()+"."+getShow().getShowDate().getYear(), getShow().getStartTime().toString().substring(0,5), getShow().getCinemaRoom().getRoomName(), "", "");
    	when(emailService.prepareMessageWithAttachment(session, "wwi2020seb@gmail.com", "mathis.neunzig@gmail.com", "Registration completed!", e, "Registration.html", emailService.createDocument(e, qrCodeGenerator.generateQRCode("Test"), new ArrayList<>()))).thenReturn(new MimeMessage(session));
    	when(htmlService.read("Registration.html", e)).thenReturn("<h1>Test</h1>");
    	mvc.perform(put("/booking/add/")
        		.contentType(MediaType.APPLICATION_JSON).content(jtco.write(new BookingConfigurationObject(new Date(2), uuid, uuid, getIDs(), getIDs(), BookingState.Paid)).getJson()))
				.andExpect(status().isCreated());
    }

    @SuppressWarnings({ "static-access", "deprecation" })
	@Test
    void testPutNullSnacks() throws Exception {
    	
    	when(userRepositroy.findById(uuid)).thenReturn(getOptionalUser());
    	when(showRepository.findById(uuid)).thenReturn(getOptionalShow());
    	when(seatService.reserveSeats(getIDs(), uuid)).thenReturn(true);
    	when(seatRepository.findById(uuid)).thenReturn(getOptionalSeat(false));
    	when(snackRepository.findById(uuid)).thenReturn(getOptionalSnack());
    	when(priceRepository.findByType(getSeat(false).getType())).thenReturn(getOptionalPrice());
    	
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
	    EmailVariablesObject e = new EmailVariablesObject(getUser().getUserName(), getUser().getFirstName(), getUser().getName(), "", "", getShow().getMovie().getTitle(), getShow().getShowDate().getDay()+"."+getShow().getShowDate().getMonth()+"."+getShow().getShowDate().getYear(), getShow().getStartTime().toString().substring(0,5), getShow().getCinemaRoom().getRoomName(), "", "");
    	when(emailService.prepareMessageWithAttachment(session, "wwi2020seb@gmail.com", "mathis.neunzig@gmail.com", "Registration completed!", e, "Registration.html", emailService.createDocument(e, qrCodeGenerator.generateQRCode("Test"), new ArrayList<>()))).thenReturn(new MimeMessage(session));
    	when(htmlService.read("Registration.html", e)).thenReturn("<h1>Test</h1>");
    	mvc.perform(put("/booking/add/")
        		.contentType(MediaType.APPLICATION_JSON).content(jtco.write(new BookingConfigurationObject(new Date(2), uuid, uuid, getIDs(), null, BookingState.Paid)).getJson()))
				.andExpect(status().isCreated());
    }

    @Test
    void testPutException() throws Exception{
    	when(showRepository.findById(uuid)).thenReturn(getOptionalShow());
    	mvc.perform(put("/booking/add/")
        		.contentType(MediaType.APPLICATION_JSON).content(jtco.write(new BookingConfigurationObject(new Date(2), uuid, uuid, getIDs(), getIDs(), BookingState.Paid)).getJson()))
				.andExpect(status().isConflict());
    	when(seatService.reserveSeats(getIDs(), uuid)).thenReturn(true);
    	mvc.perform(put("/booking/add/")
        		.contentType(MediaType.APPLICATION_JSON).content(jtco.write(new BookingConfigurationObject(new Date(2), uuid, uuid, getIDs(), getIDs(), BookingState.Paid)).getJson()))
				.andExpect(status().isConflict());
    }
    
    @Test
    void testPutException2() throws Exception {
    	when(userRepositroy.findById(uuid)).thenReturn(getOptionalUser());
    	when(showRepository.findById(uuid)).thenReturn(getOptionalShow());
    	when(seatService.reserveSeats(getIDs(), uuid)).thenReturn(true);
    	when(seatRepository.findById(uuid)).thenReturn(getOptionalSeat(false));
    	when(priceRepository.findByType(getSeat(false).getType())).thenReturn(getOptionalPrice());
    	mvc.perform(put("/booking/add/")
        		.contentType(MediaType.APPLICATION_JSON).content(jtco.write(new BookingConfigurationObject(new Date(2), uuid, uuid, getIDs(), getIDs(), BookingState.Paid)).getJson()))
				.andExpect(status().isNotFound());
    }
    
    @Test
    void testPutException3() throws Exception {
    	when(userRepositroy.findById(uuid)).thenReturn(getOptionalUser());
    	when(showRepository.findById(uuid)).thenReturn(getOptionalShow());
    	when(seatService.reserveSeats(getIDs(), uuid)).thenReturn(true);
    	when(snackRepository.findById(uuid)).thenReturn(getOptionalSnack());
    	when(priceRepository.findByType(getSeat(false).getType())).thenReturn(getOptionalPrice());
    	mvc.perform(put("/booking/add/")
        		.contentType(MediaType.APPLICATION_JSON).content(jtco.write(new BookingConfigurationObject(new Date(2), uuid, uuid, getIDs(), getIDs(), BookingState.Paid)).getJson()))
				.andExpect(status().isNotFound());
    }
    
    @Test
    void testPutException4() throws Exception {
    	when(userRepositroy.findById(uuid)).thenReturn(getOptionalUser());
    	when(showRepository.findById(uuid)).thenReturn(getOptionalShow());
    	when(seatService.reserveSeats(getIDs(), uuid)).thenReturn(true);
    	when(seatRepository.findById(uuid)).thenReturn(getOptionalSeat(false));
    	when(snackRepository.findById(uuid)).thenReturn(getOptionalSnack());
    	mvc.perform(put("/booking/add/")
        		.contentType(MediaType.APPLICATION_JSON).content(jtco.write(new BookingConfigurationObject(new Date(2), uuid, uuid, getIDs(), getIDs(), BookingState.Paid)).getJson()))
				.andExpect(status().isNotFound());
    }

    @Test
    void testUpdate() throws Exception{
    	when(repo.findById(uuid)).thenReturn(getOptionalBooking());
    	mvc.perform(put("/booking/"+uuid+"/changeStatus/")
        		.contentType(MediaType.APPLICATION_JSON).content(jtco.write(new BookingConfigurationObject(new Date(2), uuid, uuid, getIDs(), getIDs(), BookingState.Canceled)).getJson()))
				.andExpect(status().isOk());
    	when(repo.findById(uuid)).thenReturn(getOptionalBooking());
    	mvc.perform(put("/booking/"+uuid+"/changeStatus/")
        		.contentType(MediaType.APPLICATION_JSON).content(jtco.write(new BookingConfigurationObject(new Date(2), uuid, uuid, getIDs(), getIDs(), BookingState.Paid)).getJson()))
				.andExpect(status().isOk());
    	when(repo.findById(uuid)).thenReturn(getOptionalBooking());
    	mvc.perform(put("/booking/"+uuid+"/changeStatus/")
        		.contentType(MediaType.APPLICATION_JSON).content(jtco.write(new BookingConfigurationObject(new Date(2), uuid, uuid, getIDs(), getIDs(), BookingState.Reserved)).getJson()))
				.andExpect(status().isNotModified());
    }

    @Test
    void testUpdateException() throws Exception{
    	mvc.perform(put("/booking/"+uuid+"/changeStatus/")
        		.contentType(MediaType.APPLICATION_JSON).content(jtco.write(new BookingConfigurationObject(new Date(2), uuid, uuid, getIDs(), getIDs(), BookingState.Canceled)).getJson()))
				.andExpect(status().isNotFound());
    }
    
}
