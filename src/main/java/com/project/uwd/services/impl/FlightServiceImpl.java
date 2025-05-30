package com.project.uwd.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.uwd.models.Flight;
import com.project.uwd.models.Ticket;
import com.project.uwd.repositories.FlightRepository;
import com.project.uwd.repositories.TicketRepository;
import com.project.uwd.services.FlightService;

@Service
public class FlightServiceImpl implements FlightService{

	@Autowired
	private FlightRepository _flightRepository;
	
	@Autowired
	private TicketRepository _ticketRepository;
	
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
		return _flightRepository.editFlight(id, flight);
	}

	@Override
	public int createFlight(Flight flight) {
		return _flightRepository.createFlight(flight);
	}

	@Override
	public boolean cancelFlight(Long id, String cancelationReason) {
		return _flightRepository.cancelFlight(id, cancelationReason);
	}

	@Override
	public List<Flight> searchFlight(String departure, String destination, LocalDate dateOfDeparture, int numberOfSeats, boolean similarFlights) {
		return _flightRepository.searchFlights(departure, destination, dateOfDeparture, numberOfSeats, similarFlights);
	}

	@Override
	public int numberOfAvailableSpotsByFlight(Long flightId) {
		return _flightRepository.numberOfAvailableSpotsByFlight(flightId);
	}

	@Override
	public int[][] getTakenSeatsByFlightId(Long flightId) {
		Flight flight = getFlightById(flightId);
		if (flight == null) {
			return null;
		}
		
		int cols = flight.getAirplane().getNumberOfColumns();
		int rows = flight.getAirplane().getNumberOfRows();
		int[][] seats = new int[cols + 1][rows];
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				seats[i][j] = 0;
			}
		}
		
		List<Ticket> tickets = _ticketRepository.getTicketsByFlightId(flightId);
		for (Ticket t : tickets) {
			seats[t.getColumnNumber() - 1][t.getRowNumber() - 1] = 1;
		}
		
		return seats;
	}

	@Override
	public List<Flight> getFlightsOnDiscount() {
		return _flightRepository.getFlightsOnDiscount();
	}

	@Override
	public List<Flight[]> getConnectedFlights(String departure, String destination, LocalDate dateOfDeparture, int numberOfSeats) {
		List<Flight[]> connections = _flightRepository.getConnectedFlights(departure, destination);
		List<Flight[]> validConnections = new ArrayList<>();
		
		if (dateOfDeparture == null)
			dateOfDeparture = LocalDate.now();
		
		for (Flight[] connection : connections) {
			boolean allFlightsAreValid = true;
			if (connection[0].getDateOfDeparture().isAfter(dateOfDeparture)) {
				for (Flight f : connection) {
					if (_flightRepository.numberOfAvailableSpotsByFlight(f.getId()) < numberOfSeats) {
						allFlightsAreValid = false;
					}
				}
				
				if (allFlightsAreValid)
					validConnections.add(connection);
			}
		}
		
		if (validConnections.size() > 0) {
			for (Flight[] connection : validConnections) {
				System.out.println("-----connection-----");
				for (Flight flight : connection) {
					System.out.println(flight);
				}
			}
		}
		
		return validConnections;
	}

}
