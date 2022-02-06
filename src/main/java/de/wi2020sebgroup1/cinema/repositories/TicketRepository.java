package de.wi2020sebgroup1.cinema.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import de.wi2020sebgroup1.cinema.entities.Ticket;
import de.wi2020sebgroup1.cinema.entities.User;

public interface TicketRepository extends CrudRepository<Ticket, UUID> {
	
	Optional<List<Ticket>> findAllByUser(User user);
	
	List<Ticket> findAllByBookingID(UUID bookingID);

}
