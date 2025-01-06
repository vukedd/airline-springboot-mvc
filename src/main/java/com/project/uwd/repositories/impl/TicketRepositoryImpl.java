package com.project.uwd.repositories.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.uwd.models.Ticket;
import com.project.uwd.repositories.FlightRepository;
import com.project.uwd.repositories.TicketRepository;
import com.project.uwd.repositories.mappers.TicketRowMapper;

import jakarta.annotation.PostConstruct;

@Repository
public class TicketRepositoryImpl implements TicketRepository{
	
	@Autowired
	private JdbcTemplate _jdbcTemplate;
	
	@Autowired
	private FlightRepository _flightRepository;
	
	private TicketRowMapper _ticketRowMapper;
	
	@PostConstruct
	public void init() {
		_ticketRowMapper = new TicketRowMapper();
	}

	@Override
	public List<Ticket> getTicketsByReservationId(Long reservationId) {
		String sql = "SELECT * FROM Ticket WHERE ReservationId = ?;";
		List<Ticket> tickets = null;
		
		try {
			tickets = _jdbcTemplate.query(sql, _ticketRowMapper, reservationId);
		} catch (Exception e) {
			tickets = null;
		}
		
		if (tickets != null) {
			for (Ticket t : tickets) {
				t.setFlight(_flightRepository.getFlightById(t.getFlightId()));
			}
		}
		
		return tickets;
	}
	
	public List<Ticket> getTicketsByFlightId(Long flightId) {
		List<Ticket> tickets = new ArrayList<>();
		String sql = "SELECT * \r\n"
				+ "FROM airline2024.ticket\r\n"
				+ "WHERE FlightId = ?;";
		
		try {
			tickets = _jdbcTemplate.query(sql, _ticketRowMapper, flightId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return tickets;
	}
}
