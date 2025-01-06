package com.project.uwd.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.uwd.models.Ticket;

@Repository
public interface TicketRepository {
	
	public List<Ticket> getTicketsByReservationId(Long reservationId);
	public List<Ticket> getTicketsByFlightId(Long flightId);
}
