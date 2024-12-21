package com.project.uwd.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.uwd.models.Flight;

@Service
public interface FlightService {
	public List<Flight> getAllFlights();
	public Flight getFlightById(Long id);
	public int deleteFlight(Long id);
	public int editFlight(Long id, Flight flight);
	public int createFlight(Flight flight);
}
