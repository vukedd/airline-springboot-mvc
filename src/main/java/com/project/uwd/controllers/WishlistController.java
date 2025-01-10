package com.project.uwd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.uwd.models.Flight;
import com.project.uwd.models.User;
import com.project.uwd.services.FlightService;
import com.project.uwd.services.WishlistService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/wishlist")
public class WishlistController {
	
	@Autowired
	private WishlistService _wishlistService;
	
	@Autowired
	private FlightService _flightService;
	
	@PostMapping("/add")
	@ResponseBody
	public boolean addWishlistItem(@RequestParam Long userId, @RequestParam Long flightId, HttpSession session) {
		boolean successfullyAdded = _wishlistService.addWishlistItem(flightId, userId);
		if (successfullyAdded) {
			User user = (User)session.getAttribute("loggedIn");
			
			Flight wishlistItem = _flightService.getFlightById(flightId);
			if (wishlistItem != null) {
				user.getWishlist().getItems().add(wishlistItem);
				session.setAttribute("loggedIn", user);
			}
		}
		return successfullyAdded;
	}
	
	@PostMapping("/remove")
	@ResponseBody
	public boolean removeWishlistItem(@RequestParam Long userId, @RequestParam Long flightId, HttpSession session) {
		boolean successfullyRemoved = _wishlistService.removeWishlistItem(userId, flightId);
		if (successfullyRemoved) {
			User user = (User)session.getAttribute("loggedIn");
			
			Flight wishlistItem = _flightService.getFlightById(flightId);
			if (wishlistItem != null) {
				user.getWishlist().removeWishlistItem(wishlistItem);
				session.setAttribute("loggedIn", user);
			}
		}
		
		return successfullyRemoved;
	}
 }
