package de.wi2020sebgroup1.cinema.entities;

import java.sql.Date;
import java.sql.Time;
import java.util.Objects;
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
@Table(name="review")
public class Review {
	
	@Id
	@Column(columnDefinition= "VARBINARY(16)")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column
	@NotNull
	private Date date;
	
	@Column
	@NotNull
	private Time time;
	
	@Column
	@NotNull
	private String header;
	
	@Column
	@NotNull
	private String content;
	
	@Column
	@NotNull
	private int rating;
	
	@ManyToOne(cascade= CascadeType.ALL ,fetch=FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "movie_id", referencedColumnName = "id")
	private Movie movie;
	
	@ManyToOne(cascade= CascadeType.ALL ,fetch=FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	
	public Review() {
		
	}

	public Review(@NotNull Date date, @NotNull Time time, @NotNull String header, @NotNull String content, Movie movie,
			User user, @NotNull int rating) {
		super();
		this.date = date;
		this.time = time;
		this.header = header;
		this.content = content;
		this.movie = movie;
		this.user = user;
		this.rating = rating;
	}

	public Review(@NotNull Date date, @NotNull Time time, @NotNull String header, @NotNull String content, Movie movie,
			User user, int rating) {
		super();
		this.date = date;
		this.time = time;
		this.header = header;
		this.content = content;
		this.movie = movie;
		this.user = user;
		this.rating = rating;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public int getRating() {
		return rating;
	}
	
	public void setRating(int rating) {
		this.rating = rating;
	}

	@Override
	public int hashCode() {
		return Objects.hash(content, date, header, id, movie, time, user, rating);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Review other = (Review) obj;
		if (content != other.content)
			return false;
		if (date != other.date)
			return false;
		if (header != other.header)
			return false;
		if (id != other.id)
			return false;
		if (movie != other.movie)
			return false;
		if (time != other.time)
			return false;
		if (user != other.user)
			return false;
		if (rating != other.rating)
			return false;
		return true;
	}

}
