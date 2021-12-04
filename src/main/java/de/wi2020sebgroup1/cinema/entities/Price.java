package de.wi2020sebgroup1.cinema.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

@Entity
@Table(name="price")
public class Price {
	
	@Id
	@Column(columnDefinition= "VARBINARY(16)")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column
	@NonNull
	private double price;
	
	public Price() {}
	
	public Price(double price) {
		super();
		this.price = price;
	}

	public UUID getId() {
		return id;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}

}
