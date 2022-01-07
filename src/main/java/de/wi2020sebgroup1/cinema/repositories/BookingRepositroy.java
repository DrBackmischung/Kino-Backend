package de.wi2020sebgroup1.cinema.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import de.wi2020sebgroup1.cinema.entities.Booking;
import de.wi2020sebgroup1.cinema.entities.User;


public interface BookingRepositroy extends CrudRepository<Booking, UUID> {
	
	Optional<List<Booking>> findAllByUser(User user);

}
