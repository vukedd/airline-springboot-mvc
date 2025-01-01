package com.project.uwd.models;

public class Ticket {
	private Long id;
	private int columnNumber;
	private int rowNumber;
	private String passportNumber;
	private String firstName;
	private String lastName;
	
	private Flight flight;
	private Long flightId;
	
	public Ticket() {
		super();
	}
	
	public Ticket(Long id, int columnNumber, int rowNumber, String passportNumber, String firstName, String lastName,
			Flight flight) {
		super();
		this.id = id;
		this.columnNumber = columnNumber;
		this.rowNumber = rowNumber;
		this.passportNumber = passportNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.flight = flight;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public int getColumnNumber() {
		return columnNumber;
	}
	
	public void setColumnNumber(int columnNumber) {
		this.columnNumber = columnNumber;
	}
	
	public int getRowNumber() {
		return rowNumber;
	}
	
	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
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

	public Long getFlightId() {
		return flightId;
	}

	public void setFlightId(Long flightId) {
		this.flightId = flightId;
	}
}
