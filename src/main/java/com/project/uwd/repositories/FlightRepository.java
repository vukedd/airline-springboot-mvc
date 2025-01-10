package com.project.uwd.repositories;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.project.uwd.models.Flight;
import com.project.uwd.models.Ticket;

@Repository
public interface FlightRepository {
	public List<Flight> getAllFlights();
	public Flight getFlightById(Long id);
	public int deleteFlight(Long id);
	public int editFlight(Long id, Flight flight);
	public int createFlight(Flight flight);
	public boolean cancelFlight(Long id, String cancelationReason);
	public List<Flight> searchFlights(String departure, String destination, LocalDate dateOfDeparture, int numberOfSeats, boolean similarFlights);
	public int numberOfAvailableSpotsByFlight(Long flightId);
	public List<Flight> getFlightsOnDiscount();
	public List<Flight> getWishlistItemsByUserId(Long userId);
}
