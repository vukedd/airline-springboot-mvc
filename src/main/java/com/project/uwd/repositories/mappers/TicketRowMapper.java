package com.project.uwd.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.uwd.models.Ticket;

public class TicketRowMapper implements RowMapper<Ticket>{

	@Override
	public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
		Ticket ticket = new Ticket();
		ticket.setId(rs.getLong(1));
		ticket.setRowNumber(rs.getInt(2));
		ticket.setColumnNumber(rs.getInt(3));
		ticket.setPassportNumber(rs.getString(4));
		ticket.setFirstName(rs.getString(5));
		ticket.setLastName(rs.getString(6));
		ticket.setFlightId(rs.getLong(7));
		
		return ticket;
	}

}
