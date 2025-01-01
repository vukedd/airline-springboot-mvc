package com.project.uwd.repositories.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.uwd.models.Reservation;
import com.project.uwd.repositories.ReservationRepository;
import com.project.uwd.repositories.TicketRepository;
import com.project.uwd.repositories.mappers.ReservationRowMapper;

import jakarta.annotation.PostConstruct;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository{

	@Autowired
	private JdbcTemplate _jdbcTemplate;
	
	@Autowired
	private TicketRepository _ticketRepository;
	
	private ReservationRowMapper _rowMapper;
	
	@PostConstruct
	public void init() {
		_rowMapper = new ReservationRowMapper();
	}
	
	@Override
	public List<Reservation> getUserReservations(Long id) {
		String sql = "SELECT * FROM Reservation WHERE UserId = ?;";
		List<Reservation> reservations = null;
		
		try {
			reservations = _jdbcTemplate.query(sql, _rowMapper, id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		if (reservations != null) {
			for (Reservation reservation : reservations) {
				reservation.setTickets(_ticketRepository.getTicketsByReservationId(reservation.getId()));
			}
		}
		
		return reservations;
	}
	
}
