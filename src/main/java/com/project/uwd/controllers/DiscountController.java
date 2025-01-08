package com.project.uwd.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.uwd.models.Discount;
import com.project.uwd.models.Flight;
import com.project.uwd.services.DiscountService;
import com.project.uwd.services.FlightService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/discount")
public class DiscountController {
	
	@Autowired
	private DiscountService _discountService;
	
	@Autowired
	private FlightService _flightService;
	
	@PostMapping("/create")
	@ResponseBody
	public double createDiscount(@RequestParam Long flightId, @RequestParam int discountPercentage, @RequestParam LocalDate endDate, HttpSession session) {
		Flight flight = _flightService.getFlightById(flightId);
		
		Discount discount = new Discount();
		discount.setDiscountedFlightId(flightId);
		discount.setDiscountPercentage(discountPercentage);
		discount.setValidUntill(endDate);
		boolean discountAddedSuccessfully = _discountService.createDiscount(discount); 
		
		if (discountAddedSuccessfully)
			return flight.getTicketPrice() * (1 - (discountPercentage / 100.0));
		
		return 0;
	}
}
