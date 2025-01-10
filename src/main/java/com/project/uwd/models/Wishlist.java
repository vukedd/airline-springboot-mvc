package com.project.uwd.models;

import java.util.ArrayList;
import java.util.List;

public class Wishlist {
	private Long id;
	private List<Flight> items;
	private Long userId;
	
	public Wishlist(Long id, List<Flight> items) {
		super();
		this.id = id;
		this.items = items;
	}
	
	public Wishlist() {
		items = new ArrayList<>();
	}
	
	public void removeWishlistItem(Flight flight) {
		for (Flight f : items) {
			if (f.getId().equals(flight.getId())) {
				items.remove(f);
				break;
			}
		}
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public List<Flight> getItems() {
		return items;
	}
	
	public void setItems(List<Flight> items) {
		this.items = items;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
