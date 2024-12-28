package com.project.uwd.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;

public class Flight {
	private Long id;
	private LocalDateTime dateTimeOfDeparture;
	private LocalTime timeOfDeparture;
	private LocalDate dateOfDeparture;
	private int duration;
	private double ticketPrice;
	private boolean cancelled;
	
	private Airport departure;
	private Long departureId;
	
	private Airport destination;
	private Long destinationId;
	
	private Airplane airplane;
	private Long airplaneId;
	
	public Flight(Long id, LocalDateTime dateTimeOfDeparture, int duration, double ticketPrice, Airport departure,
			Airport destination, Airplane airplane) {
		super();
		this.id = id;
		this.dateTimeOfDeparture = dateTimeOfDeparture;
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

	public LocalDateTime getDateTimeOfDeparture() {
		return dateTimeOfDeparture;
	}

	public void setDateTimeOfDeparture(LocalDateTime dateTimeOfDeparture) {
		this.dateTimeOfDeparture = dateTimeOfDeparture;
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

	public Long getDepartureId() {
		return departureId;
	}

	public void setDepartureId(Long departureId) {
		this.departureId = departureId;
	}

	public Long getDestinationId() {
		return destinationId;
	}

	public void setDestinationId(Long destinationId) {
		this.destinationId = destinationId;
	}

	public Long getAirplaneId() {
		return airplaneId;
	}

	public void setAirplaneId(Long airplaneId) {
		this.airplaneId = airplaneId;
	}

	public LocalTime getTimeOfDeparture() {
		return timeOfDeparture;
	}

	public void setTimeOfDeparture(LocalTime timeOfDeparture) {
		this.timeOfDeparture = timeOfDeparture;
	}

	public LocalDate getDateOfDeparture() {
		return dateOfDeparture;
	}

	public void setDateOfDeparture(LocalDate dateOfDeparture) {
		this.dateOfDeparture = dateOfDeparture;
	}
	
	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	@Override
	public String toString() {
		return "Flight [id=" + id + ", dateTimeOfDeparture=" + dateTimeOfDeparture + ", timeOfDeparture="
				+ timeOfDeparture + ", dateOfDeparture=" + dateOfDeparture + ", duration=" + duration + ", ticketPrice="
				+ ticketPrice + ", departure=" + departure + ", departureId=" + departureId + ", destination="
				+ destination + ", destinationId=" + destinationId + ", airplane=" + airplane + ", airplaneId="
				+ airplaneId + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(airplane, airplaneId, cancelled, dateOfDeparture, dateTimeOfDeparture, departure,
				departureId, destination, destinationId, duration, id, ticketPrice, timeOfDeparture);
	}

	@Override
	public boolean equals(Object obj) { 
		if (obj instanceof Flight && this.id == ((Flight)obj).id) {
			return true;
		}
		
		return false;
	}
	
	
	
}
