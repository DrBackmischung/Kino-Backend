package de.wi2020sebgroup1.cinema.entities;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name="cinema")
public class Cinema {
	
	@Id
	@Column(columnDefinition= "VARBINARY(16)")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column
	@NotNull
	private String name;
	
	@Column
	@NotNull
	private String street;
	
	@Column
	@NotNull
	private String number;
	
	@Column
	@NotNull
	private int cinemaRooms;
	
	@Column
	@NotNull
	private int stories;

	@ManyToOne(cascade= CascadeType.ALL ,fetch=FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "city", referencedColumnName = "id")
	private City city;
	
	public Cinema(@NotNull String name, @NotNull String street, @NotNull String number,
			@NotNull int cinemaRooms, @NotNull int stories, City city) {
		super();
		this.name = name;
		this.street = street;
		this.number = number;
		this.cinemaRooms = cinemaRooms;
		this.stories = stories;
		this.city = city;
	}
	
	public City getCity_id() {
		return city;
	}
	
	public int getCinemaRooms() {
		return cinemaRooms;
	}
	
	public UUID getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getStreet() {
		return street;
	}
	
	public String getNumber() {
		return number;
	}
	
	public City getCity() {
		return city;
	}
	
	public int getStories() {
		return stories;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	public void setCinemaRooms(int cinemaRooms) {
		this.cinemaRooms = cinemaRooms;
	}
	
	public void setCity(City city) {
		this.city = city;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public void setStories(int stories) {
		this.stories = stories;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cinemaRooms;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + stories;
		result = prime * result + ((street == null) ? 0 : street.hashCode());
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
		Cinema other = (Cinema) obj;
		if (cinemaRooms != other.cinemaRooms)
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (stories != other.stories)
			return false;
		if (street == null) {
			if (other.street != null)
				return false;
		} else if (!street.equals(other.street))
			return false;
		return true;
	}
}
