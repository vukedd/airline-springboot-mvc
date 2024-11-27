package com.project.uwd.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.uwd.helpers.CSVResourceProvider;
import com.project.uwd.models.Location;
import com.project.uwd.models.enums.Continent;
import com.project.uwd.services.LocationService;
import com.project.uwd.services.impl.LocationServiceImpl;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/location")
public class LocationController {
	
	@Autowired
	private LocationService _locationService;
	
	@PostConstruct
	private void init() {
		System.out.println("start");
	}
	
	@GetMapping("/")
	@ResponseBody
	public String getLocations(@RequestParam(value="status", defaultValue="") String status) {
		List<Location> locations = _locationService.getLocations();
		String retval = "<!DOCTYPE html>\r\n"
				+ "<html lang=\"en\">\r\n"
				+ "<head>\r\n"
				+ "    <meta charset=\"UTF-8\">\r\n"
				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
				+ "    <title>Document</title>\r\n"
				+ "</head>\r\n"
				+ "<body>\r\n";
		
		if (!status.equals("")) {
			switch (status) {
				case "error":
					retval += "<p style='color:red'>Error while deleting the location</p>";
					break;
				case "success":
					retval += "<p style='color:darkGreen'>Location successfully deleted</p>";
					break;
				case "added":
					retval += "<p style='color:darkGreen'>Location successfully added</p>";
					break;
			}
		}
		
		for (Location location : locations) {
			retval += "<a href='/location/details?id=" + location.getId() + "'>" + location.getCity() + "|" + location.getContinent() + "</a><br>";
		}
		
		retval += "<a href='/location/add'>Add new location</a>"
				+ "</body>\r\n"
				+ "</html>";
		
		return retval;
		
	}
	
	@GetMapping("/details")
	@ResponseBody
	public String getLocation(@RequestParam("id") Long id, @RequestParam(name="status", defaultValue="") String status) {
		Location location = _locationService.getLocation(id);
		String retval = "<!DOCTYPE html>\r\n"
				+ "<html lang=\"en\">\r\n"
				+ "<head>\r\n"
				+ "    <meta charset=\"UTF-8\">\r\n"
				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
				+ "    <title>Document</title>\r\n"
				+ "</head>\r\n"
				+ "<body>\r\n";
		
		if (!status.equals("")) {
			switch (status) {
				case "error":
					retval += "<p style='color:red'>Error while editing the location</p>";
					break;
				case "success":
					retval += "<p style='color:darkGreen'>Location successfully edited</p>";
					break;
			}
		}
		
		if (location != null) {
			retval += "<p>" + location.getCity() + "|" + location.getContinent() + "</p>"
					+ "<a href='/location/delete?id=" + location.getId() + "'>Delete</a><br>"
					+ "<a href='/location/edit?id=" + location.getId() + "'>Edit</a><br>"
					+ "<a href='/location/'>Show all locations</a>";
		} else {
			retval += "<p>Location not found<p>";
		}
		
		retval += "</body>\r\n"
				+ "</html>";
		
		return retval;
	}
	
	@GetMapping("/delete")
	public void deleteLocation(@RequestParam("id") Long id, HttpServletResponse response) throws IOException {
		Location location = _locationService.getLocation(id);
		if (location == null) {
			response.sendRedirect("/location/?status=error");
			return;
		}
		
		Location locationForDelete = _locationService.deleteLocation(id);
		if (locationForDelete == null) {
			response.sendRedirect("/location/?status=error");
			return;
		}
		
		response.sendRedirect("/location/?status=success");
		return;
	}
	
	@GetMapping("/add")
	@ResponseBody
	public String getAddLocation(@RequestParam(name="status", defaultValue="") String status) {
		Continent[] continents = Continent.values();
		String retval = "<!DOCTYPE html>\r\n"
				+ "<html lang=\"en\">\r\n"
				+ "<head>\r\n"
				+ "    <meta charset=\"UTF-8\">\r\n"
				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
				+ "    <title>Document</title>\r\n"
				+ "</head>\r\n"
				+ "<body>\r\n"
				+ "    <h1>Add new location</h1>\r\n";
		
		if (status.equals("error")) {
			retval += "<p style='color:red'>Error while creating location</p>";
		}
		
		retval += "    <form method='Post' action='/location/add'>\r\n"
				+ "        <label>City:</label><br>\r\n"
				+ "        <input type=\"text\" name=\"city\"><br>\r\n"
				+ "        <label>Country:</label><br>\r\n"
				+ "        <input type=\"text\" name=\"country\"><br>\r\n"
				+ "        <label>Continent:</label><br>\r\n"
				+ "        <select name=\"continent\">\r\n";
		
		for (Continent continent : continents) {
			retval += "            <option value='" + continent + "'>" + continent.name() + "</option>\r\n";
		}
		retval += "        </select><br>\r\n"
				+ "   	   <button type='submit'>Add location</button>"
				+ "   </form>\r\n"
				+ "</body>\r\n"
				+ "</html>";
		
		return retval;
	}
	
	@PostMapping("/add")
	public void postAddLocation(@ModelAttribute Location location, HttpServletResponse response,BindingResult result) throws IOException {
		if (result.hasErrors()) {
			response.sendRedirect("/location/add?status=error");
			return;
		}
		
		_locationService.addLocation(location);
		
		response.sendRedirect("/location/?status=added");
		return;
	}
	
	@GetMapping("/edit")
	public void getEditLocation(@RequestParam(name="status", defaultValue="") String status, @RequestParam(name="id") Long id, HttpServletResponse response) throws IOException {
		Location location = _locationService.getLocation(id);
		if (location == null) {
			response.sendRedirect("/location/");
			return;
		}
		PrintWriter out = response.getWriter();
		Continent[] continents = Continent.values();
		out.write("<!DOCTYPE html>\r\n"
				+ "<html lang=\"en\">\r\n"
				+ "<head>\r\n"
				+ "    <meta charset=\"UTF-8\">\r\n"
				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
				+ "    <title>Document</title>\r\n"
				+ "</head>\r\n"
				+ "<body>\r\n"
				+ "    <h1>Add new location</h1>\r\n");
		
		if (status.equals("error")) {
			out.write("<p style='color:red'>Error while creating location</p>");
		}
		
		out.write("    <form method='Post' action='/location/edit?id=" + location.getId() + "'>\r\n"
				+ "        <label>City:</label><br>\r\n"
				+ "        <input type=\"text\" value='" + location.getCity() + "' name=\"city\"><br>\r\n"
				+ "        <label>Country:</label><br>\r\n"
				+ "        <input type=\"text\" name=\"country\" value='" + location.getCountry() + "'><br>\r\n"
				+ "        <label>Continent:</label><br>\r\n"
				+ "        <select name=\"continent\">\r\n");
		
		for (Continent continent : continents) {
			if (continent.equals(location.getContinent())) {
				out.write("            <option selected value='" + continent + "'>" + continent + "</option>\r\n");
			}
			out.write("            <option value='" + continent + "'>" + continent + "</option>\r\n");
		}
		out.write("        </select><br>\r\n"
				+ "   	   <button type='submit'>Save changes</button>"
				+ "   </form>\r\n"
				+ "</body>\r\n"
				+ "</html>");
		
		return;
	}
	
	@PostMapping("/edit")
	public void postEditLocation(@RequestParam Long id, @ModelAttribute Location location, HttpServletResponse response, BindingResult result) throws IOException {
		if (result.hasErrors()) {
			response.sendRedirect("/location/edit?id=" + id);
		}
		
		Location editResult = _locationService.editLocation(id, location);
		if (editResult == null) {
			response.sendRedirect("/location/?status=error");
			return;
		}
		
		response.sendRedirect("/location/details?id=" + editResult.getId() + "&status=success");
		return;
	}
}
