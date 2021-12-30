package de.wi2020sebgroup1.cinema.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.lang.NonNull;

import de.wi2020sebgroup1.cinema.enums.SeatType;

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
	
	@Column
	@NotNull
	private SeatType type;
	
	public Price(double price, SeatType type) {
		super();
		this.price = price;
		this.type = type;
	}
	
	public Price() {
		
	}
	
	public SeatType getType() {
		return type;
	}
	
	public UUID getId() {
		return id;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setType(SeatType type) {
		this.type = type;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Price other = (Price) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		return true;
	}

}
