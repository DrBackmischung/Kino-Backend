package de.wi2020sebgroup1.cinema.controller;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.wi2020sebgroup1.cinema.configurationObject.SeatsBlueprintConfigurationObject;
import de.wi2020sebgroup1.cinema.entities.CinemaRoom;
import de.wi2020sebgroup1.cinema.entities.Price;
import de.wi2020sebgroup1.cinema.entities.SeatsBluePrint;
import de.wi2020sebgroup1.cinema.enums.SeatType;
import de.wi2020sebgroup1.cinema.repositories.CinemaRoomRepository;
import de.wi2020sebgroup1.cinema.repositories.PriceRepository;
import de.wi2020sebgroup1.cinema.repositories.SeatBluePrintRepository;
import de.wi2020sebgroup1.cinema.services.QRCodeGenerator;
import de.wi2020sebgroup1.cinema.services.SeatBlueprintService;
import de.wi2020sebgroup1.cinema.services.SeatService;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
@AutoConfigureMockMvc
public class SeatBluePrintControllerTest {
	
	MockMvc mvc;
	
	@MockBean
	SeatBluePrintRepository repo;
	
	@MockBean
	PriceRepository priceRepository;
	
	@MockBean
	CinemaRoomRepository cinemaRoomRepository;
	
	@MockBean
	SeatBlueprintService seatBlueprintService;
	
	@MockBean
	SeatService seatService;
	
	@MockBean
	QRCodeGenerator qrCodeGenerator;
    
    @Autowired
    WebApplicationContext wac;
	
	JacksonTester<SeatsBlueprintConfigurationObject> jtco;
	JacksonTester<List<SeatsBlueprintConfigurationObject>> jtcolist;
	JacksonTester<List<UUID>> jtuuid;
	
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
	
	CinemaRoom getCinemaRoom() {
		CinemaRoom c = new CinemaRoom();
		c.setId(uuid);
		return c;
	}
	
	Optional<CinemaRoom> getOptionalCinemaRoom() {
		CinemaRoom c = getCinemaRoom();
		return Optional.of(c);
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

	List<SeatsBluePrint> getSeats() {
		List<SeatsBluePrint> l = new ArrayList<>();
		l.add(new SeatsBluePrint(2, 2, SeatType.LODGE, null, getCinemaRoom(), null));
		return l;
	}
	
	List<UUID> getIDs() {
		List<UUID> u = new ArrayList<>();
		u.add(uuid);
		return u;
	}
	
	List<SeatsBlueprintConfigurationObject> getSBPCO() {
		List<SeatsBlueprintConfigurationObject> s = new ArrayList<>();
		s.add(new SeatsBlueprintConfigurationObject(2, 2, SeatType.LODGE, uuid));
		return s;
	}
    
    @Test
    void testGetAll() throws Exception {
    	when(repo.findAll()).thenReturn(new ArrayList<SeatsBluePrint>());
        mvc.perform(get("/seatsBlueprint/getAll"))
                .andExpect(status().isOk());
    }
    
    @Test
    void testGetById() throws Exception {
        when(seatBlueprintService.getAllForRoom(uuid)).thenReturn(new ResponseEntity<>(getSeats(), HttpStatus.OK));
        mvc.perform(get("/seatsBlueprint/room/"+uuid)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
    
    @Test
    void testGetByIdException() throws Exception {
        when(seatBlueprintService.getAllForRoom(uuid)).thenReturn(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
        mvc.perform(get("/seatsBlueprint/room/"+new UUID(0,0))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void testPut() throws Exception {

    	when(priceRepository.findById(uuid)).thenReturn(getOptionalPrice());
    	when(cinemaRoomRepository.findById(uuid)).thenReturn(getOptionalCinemaRoom());
    	when(seatBlueprintService.add(getSBPCO())).thenReturn(new ResponseEntity<>("Test", HttpStatus.CREATED));
    	mvc.perform(put("/seatsBlueprint/add/")
        		.contentType(MediaType.APPLICATION_JSON).content(jtco.write(new SeatsBlueprintConfigurationObject(2, 2, SeatType.LODGE, uuid)).getJson()))
				.andExpect(status().isOk());
    }

    @Test
    void testMassPut() throws Exception{

    	when(priceRepository.findById(uuid)).thenReturn(getOptionalPrice());
    	when(cinemaRoomRepository.findById(uuid)).thenReturn(getOptionalCinemaRoom());
    	when(seatBlueprintService.add(getSBPCO())).thenReturn(new ResponseEntity<>("Test", HttpStatus.CREATED));
    	mvc.perform(put("/seatsBlueprint/massAdd/")
        		.contentType(MediaType.APPLICATION_JSON).content(jtcolist.write(getSBPCO()).getJson()))
				.andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception{

    	when(seatBlueprintService.delete(getIDs())).thenReturn(new ResponseEntity<>("Test", HttpStatus.OK));
        mvc.perform(
            delete("/seatsBlueprint/"+uuid+"/"))
        		.andExpect(status().isOk());

    }

    @Test
    void testMassDelete() throws Exception{

    	when(seatBlueprintService.delete(getIDs())).thenReturn(new ResponseEntity<>("Test", HttpStatus.OK));
        mvc.perform(delete("/seatsBlueprint/massDelete/")
			.contentType(MediaType.APPLICATION_JSON).content(jtuuid.write(getIDs()).getJson()))
			.andExpect(status().isOk());

    }
    
}
