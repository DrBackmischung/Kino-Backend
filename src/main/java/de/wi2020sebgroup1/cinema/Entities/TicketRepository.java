package de.wi2020sebgroup1.cinema.Entities;

import org.springframework.data.repository.CrudRepository;
import de.wi2020sebgroup1.cinema.Entities.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, Integer> {

}
