package de.wi2020sebgroup1.cinema.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="movie")
public class Movie {
	
	@Id
	@Column(columnDefinition= "VARBINARY(16)")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column
	@NotNull
	private String titel;
	
	@Column
	@NotNull
	private String language;
	
	@Column
	@NotNull
	private double duration;
	
	@Column
	@NotNull
	private String director;
	
	@Column
	@NotNull
	private String description;
	
	@Column
	@NotNull
	private String pictureLink;
	
	//ADD KINOSAAL
	
	public String getDirector() {
		return director;
	}
	
	public double getDuration() {
		return duration;
	}
	
	public UUID getId() {
		return id;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public String getTitel() {
		return titel;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getPictureLink() {
		return pictureLink;
	}
	
	public void setDirector(String director) {
		this.director = director;
	}
	
	public void setDuration(double duration) {
		this.duration = duration;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setTitel(String titel) {
		this.titel = titel;
	}
	
	public void setPictureLink(String pictureLink) {
		this.pictureLink = pictureLink;
	}

}
