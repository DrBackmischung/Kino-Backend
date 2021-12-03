package de.wi2020sebgroup1.cinema.repositories;

import org.springframework.data.repository.CrudRepository;

import de.wi2020sebgroup1.cinema.entities.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, Integer> {

}
