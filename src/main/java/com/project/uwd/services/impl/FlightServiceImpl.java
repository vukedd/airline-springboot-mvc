package com.project.uwd.services.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.uwd.models.Flight;
import com.project.uwd.repositories.FlightRepository;
import com.project.uwd.services.FlightService;

@Service
public class FlightServiceImpl implements FlightService{

	@Autowired
	private FlightRepository _flightRepository;
	
	@Override
	public List<Flight> getAllFlights() {
		return _flightRepository.getAllFlights();
	}

	@Override
	public Flight getFlightById(Long id) {
		return _flightRepository.getFlightById(id);
	}

	@Override
	public int deleteFlight(Long id) {
		return _flightRepository.deleteFlight(id);
	}

	@Override
	public int editFlight(Long id, Flight flight) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int createFlight(Flight flight) {
		// TODO Auto-generated method stub
		return _flightRepository.createFlight(flight);
	}

	@Override
	public int cancelFlight(Long id) {
		return _flightRepository.cancelFlight(id);
	}

	@Override
	public List<Flight> searchFlight(String departure, String destination, LocalDate dateOfDeparture, int numberOfSeats, boolean similarFlights) {
		return _flightRepository.searchFlights(departure, destination, dateOfDeparture, numberOfSeats, similarFlights);
	}

	@Override
	public int numberOfAvailableSpotsByFlight(Long flightId) {
		return _flightRepository.numberOfAvailableSpotsByFlight(flightId);
	}

}
