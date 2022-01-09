package de.wi2020sebgroup1.cinema.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import de.wi2020sebgroup1.cinema.entities.Seat;
import de.wi2020sebgroup1.cinema.enums.SeatState;
import de.wi2020sebgroup1.cinema.helper.SemaphoreVault;
import de.wi2020sebgroup1.cinema.repositories.BookingRepositroy;
import de.wi2020sebgroup1.cinema.repositories.SeatRepository;
import de.wi2020sebgroup1.cinema.repositories.ShowRepository;
import de.wi2020sebgroup1.cinema.repositories.TicketRepository;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class SeatServiceTest {
	
	@Autowired
	SeatService seatService;
	
	@MockBean
	SeatRepository seatRepository;
	
	@MockBean
	ShowRepository showRepository;
	
	@MockBean
	TicketRepository ticketRepository;
	
	@MockBean
	BookingRepositroy bookingRepositroy;
	
	@MockBean
	SemaphoreVault semaphoreVault;
	
	static UUID uuid;
	
	@BeforeAll
	static void beforeAll() {
		uuid = new UUID(2, 2);
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
    
    ArrayList<UUID> getIds() {
    	ArrayList<UUID> a = new ArrayList<>();
    	a.add(uuid);
    	a.add(uuid);
    	a.add(uuid);
    	return a;
    }
	
	@Test
	void testReserve() {
		when(seatRepository.findById(uuid)).thenReturn(getOptionalSeat(false));
		assertDoesNotThrow(new Executable() {
            @Override
            public void execute() {
            	seatService.reserveSeats(getIds(), uuid);           
            }
        });
		when(seatRepository.findById(uuid)).thenReturn(getOptionalSeat(true));
		when(seatRepository.findById(uuid)).thenReturn(getOptionalSeat(false));
		when(seatRepository.findById(uuid)).thenReturn(getOptionalSeat(true));
		assertDoesNotThrow(new Executable() {
            @Override
            public void execute() {
            	seatService.reserveSeats(getIds(), uuid);           
            }
        });
	}
	
	@Test
	void testReserveException() {
		when(seatRepository.findById(uuid)).thenReturn(getOptionalSeat(false));
		Thread.currentThread().interrupt();
		assertDoesNotThrow(new Executable() {
            @Override
            public void execute() {
            	seatService.reserveSeats(getIds(), uuid);           
            }
        });
	}
	
	@Test
	void testFree() {
		when(seatRepository.findById(uuid)).thenReturn(getOptionalSeat(false));
		assertDoesNotThrow(new Executable() {
            @Override
            public void execute() {
            	seatService.freeSeats(getIds(), uuid);           
            }
        });
	}
	
	@Test
	void testFreeException() {
		when(seatRepository.findById(uuid)).thenReturn(getOptionalSeat(false));
		Thread.currentThread().interrupt();
		assertDoesNotThrow(new Executable() {
            @Override
            public void execute() {
            	seatService.freeSeats(getIds(), uuid);           
            }
        });
	}

}
