package de.wi2020sebgroup1.cinema.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import de.wi2020sebgroup1.cinema.entities.News;
import de.wi2020sebgroup1.cinema.entities.User;

public interface NewsRepository extends CrudRepository<News, UUID> {
	
	Optional<List<News>> findAllByUser(User user);

}
