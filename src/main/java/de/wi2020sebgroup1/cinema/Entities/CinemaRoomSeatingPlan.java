package de.wi2020sebgroup1.cinema.Entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="cinemaRoomSeatingPlan")
public class CinemaRoomSeatingPlan {
	
	@Id
	@Column(columnDefinition= "VARBINARY(16)")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column
	@NotNull
	private int seats;
	
	@OneToOne
	@JoinColumn(name = "cinemaRoom_id", referencedColumnName = "id")
	private CinemaRoom cinemaRoom;

}
