package de.wi2020sebgroup1.cinema.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import de.wi2020sebgroup1.cinema.configurationObject.SeatsBlueprintConfigurationObject;
import de.wi2020sebgroup1.cinema.entities.CinemaRoom;
import de.wi2020sebgroup1.cinema.entities.Price;
import de.wi2020sebgroup1.cinema.entities.SeatsBluePrint;
import de.wi2020sebgroup1.cinema.enums.SeatType;
import de.wi2020sebgroup1.cinema.repositories.CinemaRoomRepository;
import de.wi2020sebgroup1.cinema.repositories.PriceRepository;
import de.wi2020sebgroup1.cinema.repositories.SeatBluePrintRepository;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class SeatBlueprintServiceTest {
	
	@Autowired
	SeatBlueprintService seatBlueprintService;

	@MockBean
	SeatBluePrintRepository seatBluePrintRepository;

	@MockBean
	PriceRepository priceRepository;

	@MockBean
	CinemaRoomRepository cinemaRoomRepository;

	@MockBean
	SeatService seatService;

	@MockBean
	QRCodeGenerator qrCodeGenerator;
	
	static UUID uuid;
	
	@BeforeAll
	static void beforeAll() {
		uuid = new UUID(2, 2);
	}
	
	List<SeatsBlueprintConfigurationObject> getSBPCO() {
		List<SeatsBlueprintConfigurationObject> l = new ArrayList<>();
		l.add(new SeatsBlueprintConfigurationObject(1, 1, SeatType.LODGE, uuid));
		l.add(new SeatsBlueprintConfigurationObject(2, 1, SeatType.LODGE, uuid));
		l.add(new SeatsBlueprintConfigurationObject(1, 2, SeatType.LODGE, uuid));
		return l;
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
    	Price s = new Price(20, SeatType.LODGE);
    	s.setId(uuid);
    	return s;
    }

    Optional<Price> getOptionalPrice() {
    	Price s = getPrice();
    	return Optional.of(s);
    }
    
    ArrayList<UUID> getIds() {
    	ArrayList<UUID> a = new ArrayList<>();
    	a.add(uuid);
    	a.add(uuid);
    	a.add(uuid);
    	return a;
    }
    
    ArrayList<UUID> getIdsWithNull() {
    	ArrayList<UUID> a = new ArrayList<>();
    	a.add(null);
    	a.add(uuid);
    	a.add(uuid);
    	return a;
    }
    
    List<SeatsBluePrint> getSBP() {
    	List<SeatsBluePrint> l = new ArrayList<>();
    	l.add(new SeatsBluePrint(1, 1, SeatType.LODGE, null, null, null));
    	l.add(new SeatsBluePrint(1, 2, SeatType.LODGE, null, null, null));
    	return l;
    }
	
	@Test
	void testAdd() {

    	when(priceRepository.findByType(SeatType.LODGE)).thenReturn(getOptionalPrice());
    	when(cinemaRoomRepository.findById(uuid)).thenReturn(getOptionalCinemaRoom());
		assertDoesNotThrow(new Executable() {
            @Override
            public void execute() {
            	seatBlueprintService.add(getSBPCO());         
            }
        });
		
	}
	
	@Test
	void testAddException() {
		
		assertDoesNotThrow(new Executable() {
            @Override
            public void execute() {
            	seatBlueprintService.add(getSBPCO());         
            }
        });
		
	}
	
	@Test
	void testDelete() {

		assertDoesNotThrow(new Executable() {
            @Override
            public void execute() {
            	seatBlueprintService.delete(getIds());         
            }
        });
		
	}
	
	@Test
	void testDeleteException() {

		assertDoesNotThrow(new Executable() {
            @Override
            public void execute() {
            	seatBlueprintService.delete(getIdsWithNull());         
            }
        });
		
	}
	
	@Test
	void testGet() {

    	when(cinemaRoomRepository.findById(uuid)).thenReturn(getOptionalCinemaRoom());
    	when(seatBluePrintRepository.findAllByCinemaRoom(getCinemaRoom())).thenReturn(getSBP());
		assertDoesNotThrow(new Executable() {
            @Override
            public void execute() {
            	seatBlueprintService.getAllForRoom(uuid);         
            }
        });
		
	}
	
	@Test
	void testGetException() {

    	when(cinemaRoomRepository.findById(null)).thenThrow(NoSuchElementException.class);
		assertDoesNotThrow(new Executable() {
            @Override
            public void execute() {
            	seatBlueprintService.getAllForRoom(uuid);         
            }
        });
		
	}
	
}
