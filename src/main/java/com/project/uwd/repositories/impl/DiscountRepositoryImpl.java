package com.project.uwd.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.uwd.models.Discount;
import com.project.uwd.repositories.DiscountRepository;
import com.project.uwd.repositories.mappers.DiscountRowMapper;

import jakarta.annotation.PostConstruct;

@Repository
public class DiscountRepositoryImpl implements DiscountRepository {

	@Autowired
	private JdbcTemplate _jdbcTemplate;
	
	private DiscountRowMapper _discountRowMapper;
	
	@PostConstruct
	private void init() {
		_discountRowMapper = new DiscountRowMapper();
	}
	
	@Override
	public boolean createDiscount(Discount discount) {
		String sql = "INSERT INTO Discount(DiscountPercentage, DiscountValidDate, FlightId) VALUES(?, ?, ?);";
		
		try {
			_jdbcTemplate.update(sql, discount.getDiscountPercentage(), discount.getValidUntill(), discount.getDiscountedFlightId());
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}

	@Override
	public Discount getDiscountByFlightId(Long flightId) {
		String sql = "SELECT * FROM Discount WHERE FlightId = ? AND DiscountValidDate > current_date();";
		Discount discount = null;
		try {
			discount = _jdbcTemplate.queryForObject(sql, _discountRowMapper, flightId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return discount;
	}
	
}
