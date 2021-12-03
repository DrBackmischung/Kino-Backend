package de.wi2020sebgroup1.cinema.repositories;

import org.springframework.data.repository.CrudRepository;

import de.wi2020sebgroup1.cinema.entities.User;

public interface UserRepository extends CrudRepository<User, Integer>{
	

}
