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
	
	@Column
	private int FSK;
	
	//ADD KINOSAAL
	
	public Movie() {
		
	}
	
	public Movie(@NotNull String titel, @NotNull String language, @NotNull double duration, @NotNull String director,
			@NotNull String description, @NotNull String pictureLink, @NotNull int FSK) {
		super();
		this.titel = titel;
		this.language = language;
		this.duration = duration;
		this.director = director;
		this.description = description;
		this.pictureLink = pictureLink;
		this.FSK = FSK;
	}

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
	
	public int getFSK() {
		return FSK;
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
	
	public void setFSK(int fSK) {
		FSK = fSK;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((director == null) ? 0 : director.hashCode());
		long temp;
		temp = Double.doubleToLongBits(duration);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((language == null) ? 0 : language.hashCode());
		result = prime * result + ((pictureLink == null) ? 0 : pictureLink.hashCode());
		result = prime * result + ((titel == null) ? 0 : titel.hashCode());
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
		Movie other = (Movie) obj;
		if (description != other.description)
			return false;
		if (director != other.director)
			return false;
		if (duration != other.duration)
			return false;
		if (id != other.id)
			return false;
		if (language != other.language)
			return false;
		if (pictureLink != other.pictureLink)
			return false;
		if (titel != other.titel)
			return false;
		return true;
	}

}
