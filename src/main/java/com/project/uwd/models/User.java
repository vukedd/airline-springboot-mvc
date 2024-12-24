package com.project.uwd.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.project.uwd.models.enums.Role;

public class User {
	private Long id;
	private String username;
	private String password;
	private String email;
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth = null;
	private LocalDate dateOfRegistration = null;
	private Role role;
	private boolean	blocked;
	
	private List<Reservation> reservations;
	private LoyaltyCard loyaltyCard;
	
	public User(String username, String password, String email, String firstName, String lastName,
			LocalDate dateOfBirth, LocalDate dateOfRegistration,Role role) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.dateOfRegistration = dateOfRegistration;
		this.role = role;
		this.reservations = new ArrayList<Reservation>();
	}
	
	public User(Long id, String username, String password, String email, String firstName, String lastName,
			LocalDate dateOfBirth, LocalDate dateOfRegistration, Role role, boolean blocked,
			List<Reservation> reservations, LoyaltyCard loyaltyCard) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.dateOfRegistration = dateOfRegistration;
		this.role = role;
		this.blocked = blocked;
		this.reservations = reservations;
		this.loyaltyCard = loyaltyCard;
	}

	public User(Long id, String username, String password, String email, String firstName, String lastName,
			LocalDate dateOfBirth, LocalDate dateOfRegistration, LoyaltyCard loyaltyCard, Role role) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.dateOfRegistration = dateOfRegistration;
		this.role = role;
		
		this.loyaltyCard = loyaltyCard;
		this.reservations = new ArrayList<Reservation>();
	}

	public User() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public LocalDate getDateOfRegistration() {
		return dateOfRegistration;
	}

	public void setDateOfRegistration(LocalDate dateOfRegistration) {
		this.dateOfRegistration = dateOfRegistration;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public LoyaltyCard getLoyaltyCard() {
		return loyaltyCard;
	}

	public void setLoyaltyCard(LoyaltyCard loyaltyCard) {
		this.loyaltyCard = loyaltyCard;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
	
	public void addReservation(Reservation reservation) {
		this.reservations.add(reservation);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", dateOfBirth=" + dateOfBirth
				+ ", dateOfRegistration=" + dateOfRegistration + ", role=" + role + ", reservations=" + reservations
				+ ", loyaltyCard=" + loyaltyCard + "]";
	}
}
