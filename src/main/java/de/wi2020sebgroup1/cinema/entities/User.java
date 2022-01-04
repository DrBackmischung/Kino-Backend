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

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.lang.NonNull;

@Entity
@Table(name="User")
public class User {
	
	@Id
	@Column(columnDefinition= "VARBINARY(16)")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column
	@NonNull
	private String username;
	
	@Column
	@NonNull
	private String name;
	
	@Column	
	@NonNull
	private String firstName;
	
	@Column
	@NonNull
	private String email;
	
	@Column
	@NonNull
	private String password;
	
	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;
	
	@Column
	@NonNull
	private String street;
	
	@Column
	@NonNull
	private String number;

	@ManyToOne(cascade= CascadeType.ALL ,fetch=FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "city_id", referencedColumnName = "id")
	private City city;
	
	@Column
	private String payPalMail;

	@ManyToOne(cascade= CascadeType.ALL ,fetch=FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "paypalaccount_id", referencedColumnName = "id")
	private CreditCard creditCard;
	
	public User() {
		
	}
	
	public User(String username, String name, String firstName, String email, String password, Role role,
			String street, String number, City city, String payPalMail, CreditCard creditCard) {
		super();
		this.username = username;
		this.name = name;
		this.firstName = firstName;
		this.email = email;
		this.password = password;
		this.role = role;
		this.street = street;
		this.number = number;
		this.city = city;
		this.payPalMail = payPalMail;
		this.creditCard = creditCard;
	}
	
	public User(String username, String name, String firstName, String email, String password,
			String street, String number, City city, String payPalMail, CreditCard creditCard) {
		super();
		this.username = username;
		this.name = name;
		this.firstName = firstName;
		this.email = email;
		this.password = password;
		this.role = new Role("Default User", "user");
		this.street = street;
		this.number = number;
		this.city = city;
		this.payPalMail = payPalMail;
		this.creditCard = creditCard;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String userName) {
		this.username = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public UUID getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getEmail() {
		return email;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getPayPalMail() {
		return payPalMail;
	}

	public void setPayPalMail(String payPalMail) {
		this.payPalMail = payPalMail;
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((creditCard == null) ? 0 : creditCard.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((payPalMail == null) ? 0 : payPalMail.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		User other = (User) obj;
		if (email != other.email)
			return false;
		if (firstName != other.firstName)
			return false;
		if (id != other.id)
			return false;
		if (name != other.name)
			return false;
		if (password != other.password)
			return false;
		if (role != other.role)
			return false;
		if (userName != other.userName)
			return false;
		return true;
	}
}
