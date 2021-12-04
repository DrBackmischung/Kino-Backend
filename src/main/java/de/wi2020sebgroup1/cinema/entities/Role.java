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
@Table(name="Role")
public class Role {
	
	@Id
	@Column(columnDefinition= "VARBINARY(16)")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column
	@NonNull
	private String description;
	
	@Column
	@NonNull
	private String authorization;
	
	public Role() {}
	
	public Role(String description, String authorization) {
		super();
		this.description = description;
		this.authorization = authorization;
	}

	public UUID getId() {
		return id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getAutorization() {
		return authorization;
	}
	
	public void setID(UUID id) {
		this.id = id;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

}
