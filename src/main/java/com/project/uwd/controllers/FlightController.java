package com.project.uwd.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.uwd.models.Flight;
import com.project.uwd.services.AirplaneService;
import com.project.uwd.services.AirportService;
import com.project.uwd.services.FlightService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/flight")
public class FlightController {
	
	@Autowired
	private FlightService _flightService;
	
	@Autowired
	private AirportService _airportService;
	
	@Autowired
	private AirplaneService _airplaneService;
	
	@GetMapping("/")
	public String getFlights(@RequestParam(required=false) String actionStatus, Model model) {
		if (actionStatus != null) {
			if (actionStatus.equals("flightAdded")) {
				model.addAttribute("actionStatus", "flightAdded");
			}
		}
		
		model.addAttribute("flights", _flightService.getAllFlights());
		return "flights";
	}
	
	@GetMapping("/details")
	public String getFlightDetails(@RequestParam Long id, Model model) {
		model.addAttribute("flight", _flightService.getFlightById(id));
		model.addAttribute("currentLocation", "/flight/details?id=" + id);
		return "flight-details";
	}
	
	@GetMapping("/add")
	public String getAddFlightForm(@RequestParam(required=false) String depDest, @RequestParam(required=false) String departureDate, @RequestParam(required=false) String flightDuration, @RequestParam(required=false) String ticketPrice, HttpSession session, Model model) {
		model.addAttribute("airports", _airportService.getAllAiports());
		model.addAttribute("airplanes", _airplaneService.getAllAirplanes());
		
		if (depDest != null) {
			model.addAttribute("depDest", depDest);
		}
		
		if (departureDate != null) {
			model.addAttribute("departureDate", departureDate);
		}
		
		if (flightDuration != null) {
			model.addAttribute("flightDuration", flightDuration);
		}
		
		if (ticketPrice != null) {
			model.addAttribute("ticketPrice", ticketPrice);
		}
		
		if (session.getAttribute("flight") == null)
			model.addAttribute("flight", new Flight());
		else
			model.addAttribute("flight", session.getAttribute("flight"));
		
		
		
		return "flight-add";
	}
	
	@PostMapping("/add")
	public String postAddFlight(@ModelAttribute Flight flight, HttpSession session) {
		StringBuilder queryParameters = new StringBuilder();
		
		if (flight.getDepartureId() == flight.getDestinationId()) {
			if (queryParameters.length() > 0) {
				queryParameters.append("&depDest=failure");
			} else {
				queryParameters.append("?depDest=failure");
			}
		}
		
		if (flight.getDateOfDeparture().isBefore(LocalDate.now()) || flight.getDateOfDeparture() == LocalDate.now()) {
			if (queryParameters.length() > 0) {
				queryParameters.append("&departureDate=failure");
			} else {
				queryParameters.append("?departureDate=failure");
			}
		}
		
		if (flight.getDuration() <= 0) {
			if (queryParameters.length() > 0) {
				queryParameters.append("&flightDuration=failure");
			} else {
				queryParameters.append("?flightDuration=failure");
			}
		}
		
		if (flight.getTicketPrice() <= 0) {
			if (queryParameters.length() > 0) {
				queryParameters.append("&ticketPrice=failure");
			} else {
				queryParameters.append("?ticketPrice=failure");
			}
		}
		
		if (queryParameters.length() > 0) {
			session.setAttribute("flight", flight);
			return "redirect:/flight/add" + queryParameters.toString();
		}
		
		int res = _flightService.createFlight(flight);
		session.removeAttribute("flight");
		if (res == 0) {
			return "redirect:/flight/?actionStatus=flightAddError";
		}
		
		return "redirect:/flight/?actionStatus=flightAdded";
	}
	
//	boolean timeDifference = LocalDateTime.of(date, time).isAfter(LocalDateTime.now());
//	if (timeDifference) {
//		int flightTime = 0;
//		int currentTime = 0;
//		
//		flightTime += (60 * time.getHour()) + time.getMinute();
//		System.out.println(flightTime);
//		currentTime += (60 * LocalTime.now().getHour()) + LocalTime.now().getMinute();
//		System.out.println(currentTime);
//
//		if (flightTime - currentTime > 60) {
//			System.out.println("Let je otkazan!");
//		} else {
//			System.out.println("Let je nemoguce otkazati!");
//		}
//	}
}
