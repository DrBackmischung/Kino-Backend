package de.wi2020sebgroup1.cinema.helper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="response")
public class ResponseEntity {
	
	@Id
	@Column
	@NotNull
	private int id;
	
	@Column
	@NotNull
	private String name;
	
	@Column
	@NotNull
	private String description;

	public ResponseEntity(@NotNull int id, @NotNull String name, @NotNull String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
