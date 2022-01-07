package de.wi2020sebgroup1.cinema.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import de.wi2020sebgroup1.cinema.entities.Review;
import de.wi2020sebgroup1.cinema.entities.User;

public interface ReviewRepository extends CrudRepository<Review, UUID> {
	
	Optional<List<Review>> findAllByUser(User user);

}
