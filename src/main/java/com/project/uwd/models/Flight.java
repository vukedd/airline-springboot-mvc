package com.project.uwd.models;

import java.time.LocalDate;
import java.util.Date;

public class Flight {
	private Long id;
	private LocalDate dateOfDeparture;
	private int duration;
	private double ticketPrice;
	private Airport departure;
	private Airport destination;
	private Airplane airplane;
	
	public Flight(Long id, LocalDate dateOfDeparture, int duration, double ticketPrice, Airport departure,
			Airport destination, Airplane airplane) {
		super();
		this.id = id;
		this.dateOfDeparture = dateOfDeparture;
		this.duration = duration;
		this.ticketPrice = ticketPrice;
		this.departure = departure;
		this.destination = destination;
		this.airplane = airplane;
	}

	public Flight() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDateOfDeparture() {
		return dateOfDeparture;
	}

	public void setDateOfDeparture(LocalDate dateOfDeparture) {
		this.dateOfDeparture = dateOfDeparture;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public double getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public Airport getDeparture() {
		return departure;
	}

	public void setDeparture(Airport departure) {
		this.departure = departure;
	}

	public Airport getDestination() {
		return destination;
	}

	public void setDestination(Airport destination) {
		this.destination = destination;
	}

	public Airplane getAirplane() {
		return airplane;
	}

	public void setAirplane(Airplane airplane) {
		this.airplane = airplane;
	}
	
}
