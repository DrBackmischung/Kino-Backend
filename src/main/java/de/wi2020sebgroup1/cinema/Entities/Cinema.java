package de.wi2020sebgroup1.cinema.Entities;

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
import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	private int cinemaBalls;
	
	@Column
	@NotNull
	private int stories;
	
	@ManyToOne(cascade= CascadeType.ALL ,fetch=FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "city", referencedColumnName = "id")
	private City city;
	
	public City getCity_id() {
		return city;
	}
	
	public int getCinemaBalls() {
		return cinemaBalls;
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
	
	public void setCinemaBalls(int cinemaBalls) {
		this.cinemaBalls = cinemaBalls;
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
}
