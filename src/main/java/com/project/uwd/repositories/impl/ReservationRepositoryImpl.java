package com.project.uwd.repositories.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.project.uwd.models.Reservation;
import com.project.uwd.models.Ticket;
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
		String sql = "SELECT * FROM reservation WHERE UserId = ? ORDER BY ReservationDate desc;";
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

	@Override
	@Transactional(rollbackFor=Exception.class)
	public boolean createReservation(List<Ticket> reservationTickets, int points, double totalPrice, Long userId) {
		int bonusLoyaltyCardPoints = (int) (totalPrice / 30000);
		
		int rowsAffected1 = 0;
		int rowsAffected2 = 0;
		int rowsAffected3 = 0;
		int rowsAffected4 = 0;
		
		String sql1 = "INSERT INTO reservation(ReservationDate, TotalPrice, UserId) VALUES (current_date(), ?, ?);";
		String sql2 = "INSERT INTO ticket(SeatRow, SeatColumn, PassportNumber, FirstName, LastName, FlightId, ReservationId) VALUES(?, ?, ?, ?, ?, ?, ?);";
		String sql3 = "UPDATE loyaltycard, user\r\n"
				+ "SET loyaltycard.points = loyaltycard.points - ?\r\n"
				+ "WHERE loyaltycard.LoyaltycardId = user.LoyaltyCardId and user.userId = ?;";
		String sql4 = "UPDATE loyaltycard, user\r\n"
				+ "SET loyaltycard.points = loyaltycard.points + ?\r\n"
				+ "WHERE loyaltycard.LoyaltyCardId = user.LoyaltyCardId and user.userId = ?;";
		
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        rowsAffected1 = _jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
                ps.setDouble(1, totalPrice);
                ps.setLong(2, userId);
                return ps;
            }
        }, keyHolder);
        
        Long reservationId = keyHolder.getKey().longValue();
		
        for (Ticket t : reservationTickets) {
        	rowsAffected2 = _jdbcTemplate.update(sql2, t.getRowNumber(), t.getColumnNumber(), t.getPassportNumber(), t.getFirstName(), t.getLastName(), t.getFlightId(), reservationId);
        }
        
        if (points != -1) {
            rowsAffected3 = _jdbcTemplate.update(sql3, points, userId);
            rowsAffected4 = _jdbcTemplate.update(sql4, bonusLoyaltyCardPoints, userId);
            if (rowsAffected1 != 0 && rowsAffected2 != 0 && rowsAffected3 != 0 && rowsAffected4 != 0) {
            	return true;
            }
        }
        
        if (rowsAffected1 != 0 && rowsAffected2 != 0) {
        	return true;
        }
        
		return false;
	}	
}
