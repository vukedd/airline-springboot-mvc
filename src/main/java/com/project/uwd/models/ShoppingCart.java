package com.project.uwd.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {
	private Map<Flight, Integer> items;
	private double totalPrice;
	
	public ShoppingCart() {
		super();
		items = new HashMap<Flight, Integer>();
		totalPrice = 0.0;
	}

	public ShoppingCart(Map<Flight, Integer> items) {
		super();
		this.items = items;
	}

	public Map<Flight, Integer> getItems() {
		return items;
	}

	public void setItems(Map<Flight, Integer> items) {
		this.items = items;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void addCartItem(Flight flight, int quantity) { 
		boolean flightTicketExistsInCart = false;
		
		for (Flight flightIter : items.keySet()) {
			if (!flightTicketExistsInCart) {
				if (flightIter.equals(flight)) {
					items.put(flightIter, items.get(flightIter) + quantity);
					flightTicketExistsInCart = true;
				}
			} else
				break;
		}
		
		if (!flightTicketExistsInCart)
			items.put(flight, quantity);
		
		calculateTotalPrice();
	}
	
	public void calculateTotalPrice() {
		double sum = 0.0;
		for (Flight flightIter : items.keySet()) {
			sum += flightIter.getTicketPrice() * items.get(flightIter);
		}
		
		totalPrice = sum;
		return;
	}
	
	public int getTotalNumberOfItems() {
		int sum = 0;
		for (int quantity : items.values()) {
			sum += quantity;
		}
		
		return sum;
	}
	
	public boolean removeCartItemById(Long flightId) {
		boolean isDeleted = false;
		for (Flight flightIter : items.keySet()) {
			if (flightIter.getId() == flightId) {
				items.remove(flightIter);
				isDeleted = true;
				break;
			}
		}
		
		calculateTotalPrice();
		
		return isDeleted;
	}
	
	public Flight getCartItemById(Long flightId) {
		Flight flight = null;
		for (Flight flightIter : this.items.keySet()) {
			if (flightIter.getId() == flightId) {
				flight = flightIter;
				break;
			}
		}
		
		return flight;
	}
}
