package com.project.uwd.models;

public class Ticket {
	private Long id;
	private int seatNumber;
	private String passportNumber;
	private String firstName;
	private String lastName;
	private Flight flight;
	
	public Ticket(Long id, int seatNumber, String passportNumber, String firstName, String lastName, Flight flight) {
		super();
		this.id = id;
		this.seatNumber = seatNumber;
		this.passportNumber = passportNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.flight = flight;
	}

	public Ticket() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
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

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}
	
}
