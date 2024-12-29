package com.project.uwd.controllers;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.uwd.models.Flight;
import com.project.uwd.models.ShoppingCart;
import com.project.uwd.services.FlightService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class ShoppingCartController {
	
	@Autowired
	FlightService _flightService;
	
	@GetMapping
	public String getShoppingCart(@RequestParam(required=false) String status, Model model) {
		if (status != null && status.equals("error")) {
			model.addAttribute("status", status);
		}
		
		return "shopping-cart";
	}
	
	@GetMapping("/remove")
	public String removeCartItem(@RequestParam(required=false) Long id, HttpSession session) {
		if (id == null) {
			return "redirect:/";
		}
		
		boolean isRemoved = false;
		
		if (session.getAttribute("ShoppingCart") != null) {
			ShoppingCart cart = (ShoppingCart)session.getAttribute("ShoppingCart");
			isRemoved = cart.removeCartItemById(id);
			if (cart.getTotalNumberOfItems() == 0)
				session.removeAttribute("ShoppingCart");
				session.setAttribute("cartSize", cart.getTotalNumberOfItems());
				session.setAttribute("FlightTicketTracker", new HashMap<Long, Integer>());
		}
		
		if (isRemoved) {
			return "redirect:/cart";
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/decrement")
	public String decrementCartItemQuantity(@RequestParam Long id, HttpSession session) {
		 if (session.getAttribute("ShoppingCart") != null) {
			 ShoppingCart cart = (ShoppingCart)session.getAttribute("ShoppingCart");
			 Flight flightCartItem = cart.getCartItemById(id);
			 if (flightCartItem != null && cart.getItems().get(flightCartItem) != null) {
				 int quantity = cart.getItems().get(flightCartItem);
				 if (quantity - 1 < 1) {
					 return "redirect:/cart/remove?id=" + id;
				 } else {
					 cart.getItems().put(flightCartItem, quantity - 1);
					 cart.calculateTotalPrice();
					 session.setAttribute("ShoppingCart", cart);
				 }
				 
				 return "redirect:/cart";
			 }
		 }
		 
		 return "redirect:/cart?status=error";
	}
	
	@GetMapping("/increment")
	public String incrementCartItemQuantity(@RequestParam Long id, HttpSession session) {
		 if (session.getAttribute("ShoppingCart") != null) {
			 ShoppingCart cart = (ShoppingCart)session.getAttribute("ShoppingCart");
			 Flight flightCartItem = cart.getCartItemById(id);
			 if (flightCartItem != null && cart.getItems().get(flightCartItem) != null) {
				 int quantity = cart.getItems().get(flightCartItem);
				 if (_flightService.numberOfAvailableSpotsByFlight(id) > quantity) {
					 cart.addCartItem(flightCartItem, 1);
					 session.setAttribute("ShoppingCart", cart);
					 return "redirect:/cart";
				 }
			 }
		 }
		 
		 return "redirect:/cart?status=error";
	}
	
}