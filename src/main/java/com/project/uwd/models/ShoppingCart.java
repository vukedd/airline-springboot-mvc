package com.project.uwd.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {
	private List<Ticket> items;
	private double totalPrice;
	
	public ShoppingCart() {
		super();
		items = new ArrayList<Ticket>();
		totalPrice = 0.0;
	}

	public ShoppingCart(ArrayList<Ticket> items) {
		super();
		this.items = items;
	}


	public List<Ticket> getItems() {
		return items;
	}

	public void setItems(List<Ticket> items) {
		this.items = items;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void addCartItem(Ticket ticket) { 
		items.add(ticket);
		calculateTotalPrice();
	}
	
	public void calculateTotalPrice() {
		double sum = 0.0;
		for (Ticket ticket : items) {
			if (ticket.getFlight().isOnDiscount() == true) {
				sum += (ticket.getFlight().getTicketPrice() * (1 - (ticket.getFlight().getDiscount().getDiscountPercentage() / 100.0)));
			} else {
				sum += ticket.getFlight().getTicketPrice();
			}
		}
		
		totalPrice = sum;
		return;
	}
	
	public int getTotalNumberOfItems() {
		return items.size();
	}
	
	public boolean removeCartItemById(Long ticketId) {
		for (Ticket ticket : items) {
			if (ticket.getId() == ticketId) {
				items.remove(ticket);
				calculateTotalPrice();
				return true;
			}
		}
		
		return false;
	}
	
	public Ticket getCartItemById(Long ticketId) {
		Ticket ticket = null;
		for (Ticket ticketIter : items) {
			if (ticketIter.getId() == ticketId) {
				return ticketIter;
			}
		}
		
		return ticket;
	}
}
