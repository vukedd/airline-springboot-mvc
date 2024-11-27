package com.project.uwd.models;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Reservation {
	private Long id;
	private LocalDate date;
	private double totalPrice;
	private List<Ticket> tickets;
	
	public Reservation(Long id, LocalDate date, double totalPrice, List<Ticket> tickets) {
		super();
		this.id = id;
		this.date = date;
		this.totalPrice = totalPrice;
		this.tickets = tickets;
	}

	public Reservation() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}
	
}
