package com.project.uwd.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.project.uwd.models.Flight;

@Repository
public interface FlightRepository {
	public List<Flight> getAllFlights();
	public Flight getFlightById(Long id);
	public int deleteFlight(Long id);
	public int editFlight(Long id, Flight flight);
	public int createFlight(Flight flight);
	public int cancelFlight(Long id);
	public List<Flight> searchFlights(String departure, String destination, LocalDate dateOfDeparture);
	public int numberOfAvailableSpotsByFlight(Long flightId);
}
