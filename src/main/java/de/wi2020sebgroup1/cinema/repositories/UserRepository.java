package de.wi2020sebgroup1.cinema.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import de.wi2020sebgroup1.cinema.entities.User;

public interface UserRepository extends CrudRepository<User, UUID>{
	
	Optional<User> findByUsername(String username);

}
