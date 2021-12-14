package de.wi2020sebgroup1.cinema.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import de.wi2020sebgroup1.cinema.entities.Movie;
import de.wi2020sebgroup1.cinema.entities.Show;

public interface ShowRepository extends CrudRepository<Show, UUID> {
	
	Optional<List<Show>> findAllByMovie(Movie movie);

}
