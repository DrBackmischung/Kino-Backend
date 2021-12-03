package de.wi2020sebgroup1.cinema.Entities;

import org.springframework.data.repository.CrudRepository;
import de.wi2020sebgroup1.cinema.Entities.Movie;

public interface MovieRepository extends CrudRepository<Movie, Integer> {

}
