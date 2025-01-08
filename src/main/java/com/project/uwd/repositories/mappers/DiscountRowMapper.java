package com.project.uwd.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.uwd.models.Discount;

public class DiscountRowMapper implements RowMapper<Discount>{

	@Override
	public Discount mapRow(ResultSet rs, int rowNum) throws SQLException {
		Discount discount = new Discount();
		discount.setId(rs.getLong(1));
		discount.setDiscountPercentage(rs.getDouble(2));
		discount.setValidUntill(rs.getDate(3).toLocalDate());
		discount.setDiscountedFlightId(rs.getLong(4));
		return discount;
	}

}
