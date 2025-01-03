package com.project.uwd.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.uwd.models.LoyaltyCardRequest;
import com.project.uwd.models.enums.Status;

public class LoyaltyCardRequestMapper implements RowMapper<LoyaltyCardRequest>{

	@Override
	public LoyaltyCardRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
		LoyaltyCardRequest loyaltyCardRequest = new LoyaltyCardRequest();
		loyaltyCardRequest.setId(rs.getLong(1));
		loyaltyCardRequest.setStatus(Status.values()[rs.getInt(2)]);
		loyaltyCardRequest.setRequestedById(rs.getLong(3));
		return loyaltyCardRequest;
	}

}
