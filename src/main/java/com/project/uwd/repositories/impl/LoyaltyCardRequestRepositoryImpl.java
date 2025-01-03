package com.project.uwd.repositories.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.uwd.models.LoyaltyCardRequest;
import com.project.uwd.models.enums.Status;
import com.project.uwd.repositories.LoyaltyCardRequestRepository;
import com.project.uwd.repositories.mappers.LoyaltyCardRequestMapper;

import jakarta.annotation.PostConstruct;

@Repository
public class LoyaltyCardRequestRepositoryImpl implements LoyaltyCardRequestRepository{

	@Autowired
	JdbcTemplate _jdbcTemplate;
	
	private LoyaltyCardRequestMapper _rowMapper;
	@PostConstruct
	public void init() {
		_rowMapper = new LoyaltyCardRequestMapper();
	}
	
	@Override
	public boolean createLoyaltyCardRequest(Long userId) {
		int rowsAffected = 0;
		boolean successfullyAdded = false;
		
		String sql = "INSERT INTO LoyaltyCardRequest(UserId, Status) VALUES (?, ?)";
		try {
			rowsAffected = _jdbcTemplate.update(sql, userId, Status.InProcess.ordinal());
		} catch (Exception e) {
			System.out.println("Error while submiting request!");
		}
		
		if (rowsAffected > 0)
			successfullyAdded = true;
		
		return successfullyAdded;
	}

	@Override
	public List<LoyaltyCardRequest> getLoyaltyCardRequestsById(Long userId) {
		List<LoyaltyCardRequest> loyaltyCardRequests = null;
		
		String sql = "SELECT * FROM LoyaltyCardRequest WHERE UserId = ?;";
		try {
			loyaltyCardRequests = _jdbcTemplate.query(sql, _rowMapper, userId);
		} catch (Exception e) {
			System.out.println("Erorr while obtaining loyalty card requests!");
		}
		
		return loyaltyCardRequests;
	}

}
