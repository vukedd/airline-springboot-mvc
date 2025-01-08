package com.project.uwd.models;

import java.time.LocalDate;

public class Discount {
	private Long id;
	private double discountPercentage;
	private LocalDate validUntill;
	private Flight discountedFlight;
	private Long discountedFlightId;
	
	public Discount(Long id, double discountPercentage, LocalDate validUntill, Flight discountedFlight) {
		super();
		this.id = id;
		this.discountPercentage = discountPercentage;
		this.validUntill = validUntill;
		this.discountedFlight = discountedFlight;
	}

	public Discount() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public LocalDate getValidUntill() {
		return validUntill;
	}

	public void setValidUntill(LocalDate validUntill) {
		this.validUntill = validUntill;
	}

	public Flight getDiscountedFlight() {
		return discountedFlight;
	}

	public void setDiscountedFlight(Flight discountedFlight) {
		this.discountedFlight = discountedFlight;
	}

	public Long getDiscountedFlightId() {
		return discountedFlightId;
	}

	public void setDiscountedFlightId(Long discountedFlightId) {
		this.discountedFlightId = discountedFlightId;
	}
}
