package de.wi2020sebgroup1.cinema.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import de.wi2020sebgroup1.cinema.entities.Movie;
import de.wi2020sebgroup1.cinema.entities.Show;
import de.wi2020sebgroup1.cinema.repositories.MovieRepository;
import de.wi2020sebgroup1.cinema.repositories.ShowRepository;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class ShowServiceTest {
	
	@MockBean
	private ShowRepository showRepository;
	
	@MockBean
	private MovieRepository movieRepository;
	
	@Autowired
	ShowService showService;
	
	static UUID uuid;
	
	@BeforeAll
	static void beforeAll() {
		uuid = new UUID(2, 2);
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
    
    Movie getMovie() {
    	Movie m = new Movie("Shrek 3", "deutsch", null, 2.5, "Kitty Blume", "Ein Film", "localhost/img", null, null, null, null, 0);
    	m.setId(uuid);
    	return m;
    }
    
    Optional<Movie> getOptionalMovie() {
    	Movie m = getMovie();
    	return Optional.of(m);
    }
    
    Optional<List<Show>> getOptionalShows() {
    	List<Show> l = new ArrayList<>();
    	l.add(getShow());
    	return Optional.of(l);
    }
	
	@Test
	void testGetAllByMovie() {
		when(movieRepository.findById(uuid)).thenReturn(getOptionalMovie());
		when(movieRepository.findById(uuid)).thenReturn(getOptionalMovie());
		when(showRepository.findAllByShowDateBetweenAndMovie(new Date(1), new Date(2), new Movie())).thenReturn(getOptionalShows());
		assertDoesNotThrow(new Executable() {
            @Override
            public void execute() {
            	showService.getAllByMovie(uuid);                
            }
        });
	}
	
	@Test
	void testGetAllByMovieException() {
		when(movieRepository.findById(uuid)).thenReturn(getOptionalMovie());
		when(movieRepository.findById(uuid)).thenReturn(null);
		assertDoesNotThrow(new Executable() {
            @Override
            public void execute() {
            	showService.getAllByMovie(uuid);                
            }
        });
	}

}
