package com.project.uwd.controllers;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.uwd.models.ShoppingCart;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class ShoppingCartController {
	
	@GetMapping
	public String getShoppingCart() {
		
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
}