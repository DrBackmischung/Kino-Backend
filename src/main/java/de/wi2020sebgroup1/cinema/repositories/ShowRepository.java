package de.wi2020sebgroup1.cinema.repositories;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import de.wi2020sebgroup1.cinema.entities.Movie;
import de.wi2020sebgroup1.cinema.entities.Show;

public interface ShowRepository extends CrudRepository<Show, UUID> {
	
	Optional<List<Show>> findAllByMovie(Movie movie);
	
	//@Query(value= "SELECT * FROM vorstellung v WHERE show_date BETWEEN :currentDate AND :endDate")
	//Optional<List<Show>> findAllBetweenDates(@Param("currentDate")LocalDate currentDate, @Param("endDate")LocalDate endDate);
	
	Optional<List<Show>> findAllByShowDateBetweenAndMovie(Date current, Date next, Movie movie);
}
