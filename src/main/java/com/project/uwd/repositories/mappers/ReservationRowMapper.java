package com.project.uwd.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.uwd.models.Reservation;

public class ReservationRowMapper implements RowMapper<Reservation>{

	@Override
	public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
		Reservation reservation = new Reservation();
		reservation.setId(rs.getLong(1));
		reservation.setDate(rs.getDate(2).toLocalDate());
		reservation.setTotalPrice(rs.getDouble(3));
		return reservation;
	}

}
