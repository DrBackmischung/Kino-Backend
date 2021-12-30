package de.wi2020sebgroup1.cinema.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import de.wi2020sebgroup1.cinema.entities.Price;
import de.wi2020sebgroup1.cinema.enums.SeatType;

public interface PriceRepository extends CrudRepository<Price, UUID> {
	
	public Optional<Price> findByType(SeatType type);

}
