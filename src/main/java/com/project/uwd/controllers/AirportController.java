package com.project.uwd.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.uwd.models.Airport;
import com.project.uwd.models.Location;
import com.project.uwd.models.User;
import com.project.uwd.models.enums.Role;
import com.project.uwd.services.AirportService;
import com.project.uwd.services.LocationService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/airport")
public class AirportController {
	
	@Autowired
	private AirportService _airportService;
	
	@Autowired
	private LocationService _locationService;
	
	@GetMapping("/")
	public String getAllAirport(@RequestParam(required=false) String actionStatus, Model model) {
		List<Airport> airports = _airportService.getAllAiports();
		model.addAttribute("airports", airports);
		if (actionStatus != null) {
			if (actionStatus.equals("airportAdded"))
				model.addAttribute("actionStatus", "airportAdded");
			else if (actionStatus.equals("airportEdited"))
				model.addAttribute("actionStatus", "airportEdited");
			else if (actionStatus.equals("airportDeleted"))
				model.addAttribute("actionStatus", "airportDeleted");
			else if (actionStatus.equals("airportDeleteError")) 
				model.addAttribute("actionStatus", "airportDeleteError");
			else if (actionStatus.equals("airportEditError")) 
				model.addAttribute("actionStatus", "airportEditError");
			else if (actionStatus.equals("airportAddError")) 
				model.addAttribute("actionStatus", "airportAddError");
		}
		
		return "airports";
	}
	
	@GetMapping("/details")
	public String getAirportDetails(@RequestParam Long id, Model model) {
		Airport airport = _airportService.getAirportById(id);
		model.addAttribute("airport", airport);
		
		String currentLocation = "/airport/details?id=" + id;
		model.addAttribute("currentLocation", currentLocation);
		model.addAttribute("idparam", "1");
		
		return "airport-details";
	}
	
	@GetMapping("/delete")
	public String deleteAirport(@RequestParam Long id, HttpSession session) {
		User user = (User) session.getAttribute("loggedIn");
		if (user != null && user.getRole().compareTo(Role.Admin) == 0) {
			int res = _airportService.deleteAirport(id);
			if (res != 1) {
				return "redirect:/airport/?actionStatus=airportDeleteError";
			}
			return "redirect:/airport/?actionStatus=airportDeleted";
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/add")
	public String getAddAirport(@RequestParam(required=false) String code, HttpSession session, Model model) {
		User user = (User) session.getAttribute("loggedIn");
		if (user != null && user.getRole().compareTo(Role.Admin) == 0) {

			List<Location> locations = _locationService.getLocations();
			model.addAttribute("locations", locations);
			
			if (session.getAttribute("airport") == null) {
				model.addAttribute("airport", new Airport());
			} else {
				model.addAttribute("airport", session.getAttribute("airport"));
			}
			
			if (code != null) {
				model.addAttribute("code", code);
			}
			return "airport-add";
		}
		
		return "redirect:/";
	}
	
	@PostMapping("/add")
	public String postAddAirport(@ModelAttribute Airport airport, HttpSession session) {
		StringBuilder queryParameters = new StringBuilder();
		
		if (airport.getCode().length() < 2 || airport.getCode().length() > 5) {
			if (queryParameters.length() > 0) {
				queryParameters.append("&code=failure");
			} else {
				queryParameters.append("?code=failure");
			}
		}
		
		if (queryParameters.length() > 0) {
			session.setAttribute("airport", airport);
			return "redirect:/airport/add" + queryParameters.toString();
		}
		
		session.removeAttribute("airport");
		int res = _airportService.addAirport(airport);
		
		if (res != 1)
			return "redirect:/airport/?actionStatus=airportAddError";
		
		return "redirect:/airport/?actionStatus=airportAdded";
	}
	
	@GetMapping("/edit")
	public String getEditAirport(@RequestParam Long id, @RequestParam(required=false) String code, HttpSession session, Model model) {
		User user = (User) session.getAttribute("loggedIn");
		if (user != null && user.getRole().compareTo(Role.Admin) == 0) {
			List<Location> locations = _locationService.getLocations();
			model.addAttribute("locations", locations);
			
			Airport airport = _airportService.getAirportById(id);
			model.addAttribute("airport", airport);
			
			if (code != null) {
				model.addAttribute("code", code);
			}
			
			String currentLocation = "/airport/edit?id=" + id;
			model.addAttribute("currentLocation", currentLocation);
			model.addAttribute("idparam", "1");
			
			return "airport-edit";
		}
		
		return "redirect:/";
	}
	
	@PostMapping("/edit")
	public String postEditAirport(@RequestParam Long id, @ModelAttribute Airport airport, HttpSession session) {
		StringBuilder queryParameters = new StringBuilder();
		
		if (airport.getCode().length() < 2 || airport.getCode().length() > 5) {
			queryParameters.append("&code=failure");
		}
		
		if (queryParameters.length() > 0) {
			return "redirect:/airport/edit?id=" + id + queryParameters.toString();
		}
		
		int res = _airportService.editAirport(id, airport);
		if (res != 1) {
			return "redirect:/airport/?actionStatus=airportEditError";
		}
		
		return "redirect:/airport/?actionStatus=airportEdited";
	}
}
