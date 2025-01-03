package com.project.uwd.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.uwd.models.LoyaltyCard;

public class LoyaltyCardRowMapper implements RowMapper<LoyaltyCard>{

	@Override
	public LoyaltyCard mapRow(ResultSet rs, int rowNum) throws SQLException {
		LoyaltyCard loyaltyCard = new LoyaltyCard();
		loyaltyCard.setId(rs.getLong(1));
		loyaltyCard.setPoints(rs.getDouble(2));
		return loyaltyCard;
	}

}
