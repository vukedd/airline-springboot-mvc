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
	public String getFlights(Model model) {
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
	public String getAddFlightForm(Model model) {
		model.addAttribute("airports", _airportService.getAllAiports());
		model.addAttribute("airplanes", _airplaneService.getAllAirplanes());
		model.addAttribute("flight", new Flight());
		
		return "flight-add";
	}
	
	@PostMapping("/add")
	public String postAddFlight(@ModelAttribute Flight flight) {

		return "flight-add";
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
