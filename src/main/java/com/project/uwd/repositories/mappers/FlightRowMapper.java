package com.project.uwd.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.uwd.models.Flight;

public class FlightRowMapper implements RowMapper<Flight>{

	@Override
	public Flight mapRow(ResultSet rs, int rowNum) throws SQLException {
		Flight flight = new Flight();
		flight.setId(rs.getLong(1));
		flight.setDateTimeOfDeparture(rs.getTimestamp(2).toLocalDateTime());
		flight.setDateOfDeparture(flight.getDateTimeOfDeparture().toLocalDate());
		flight.setTimeOfDeparture(flight.getDateTimeOfDeparture().toLocalTime());
		flight.setDuration(rs.getInt(3));
		flight.setTicketPrice(rs.getDouble(4));
		flight.setDepartureId(rs.getLong(5));
		flight.setDestinationId(rs.getLong(6));
		flight.setAirplaneId(rs.getLong(7));
		
		return flight;
	}

}
