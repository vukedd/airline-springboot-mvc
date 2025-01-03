package com.project.uwd.repositories.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.project.uwd.models.LoyaltyCardRequest;
import com.project.uwd.models.enums.Status;
import com.project.uwd.repositories.LoyaltyCardRequestRepository;
import com.project.uwd.repositories.UserRepository;
import com.project.uwd.repositories.mappers.LoyaltyCardRequestMapper;

import jakarta.annotation.PostConstruct;

@Repository
public class LoyaltyCardRequestRepositoryImpl implements LoyaltyCardRequestRepository{

	@Autowired
	JdbcTemplate _jdbcTemplate;
	
	@Autowired
	private UserRepository _userRepository;
	
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
		if (!isLoyaltyCardRequestAlreadySent(userId)) {
			try {
				rowsAffected = _jdbcTemplate.update(sql, userId, Status.InProcess.ordinal());
			} catch (Exception e) {
				System.out.println("Error while submiting request!");
			}
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

	@Override
	public boolean isLoyaltyCardRequestAlreadySent(Long userId) {
		int countOfUnproccessedRequests = 0;
		
		String sql = "SELECT COUNT(*) FROM LoyaltyCardRequest WHERE UserId = ? and Status = 2;";
		try {
			countOfUnproccessedRequests = _jdbcTemplate.queryForObject(sql, Integer.class, userId);
		} catch (Exception e) {
			System.out.println("Loyalty card request validation was unsuccessfull!");
		}
		
		if (countOfUnproccessedRequests == 0) {
			return false;
		}
		
		return true;
	}

	@Override
	public List<LoyaltyCardRequest> getLoyaltyCardRequests() {
		List<LoyaltyCardRequest> requests = null;
		String sql = "SELECT * FROM LoyaltyCardRequest WHERE Status = 2;";
		
		try {
			requests = _jdbcTemplate.query(sql, _rowMapper);
		} catch (Exception e) {
			System.out.println("An error occurred while fetching loyalty card requests!");
		}
		
		if (requests != null) {
			for (LoyaltyCardRequest request : requests) {
				request.setRequestedBy(_userRepository.getUserById(request.getRequestedById()));
			}
		}
		
		return requests;
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public boolean approveLoyaltyCardRequest(Long requestId, Long userId) {
		String sql1 = "UPDATE LoyaltyCardRequest SET Status = 0 WHERE RequestId = ?;";
		String sql2 = "INSERT INTO LoyaltyCard(points) VALUES(5);";
		String sql3 = "UPDATE User SET LoyaltyCardId = ? WHERE UserId = ?;";
		
		int rowsAffected1 = 0;
		int rowsAffected2 = 0;
		int rowsAffected3 = 0;

		
		try {
			rowsAffected1 = _jdbcTemplate.update(sql1, requestId);
			
	        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
	        rowsAffected2 = _jdbcTemplate.update(new PreparedStatementCreator() {
	            @Override
	            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	                return con.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
	            }
	        }, keyHolder);
	        
	        Long loyaltyCardId = keyHolder.getKey().longValue();
	        rowsAffected3 = _jdbcTemplate.update(sql3, loyaltyCardId, userId);
			
		} catch (Exception e) {
			System.out.println("An error occurred while approving loyalty card request!");
		}
		
		return (rowsAffected1 != 0 && rowsAffected2 != 0 && rowsAffected3 != 0);
	}

	@Override
	public LoyaltyCardRequest getLoyaltyCardRequestById(Long requestId) {
		LoyaltyCardRequest cardReq = null;
		String sql = "SELECT * FROM LoyaltyCardRequest WHERE RequestId = ?;";
		
		try {
			cardReq = _jdbcTemplate.queryForObject(sql, _rowMapper, requestId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return cardReq;
	}

	@Override
	public boolean denyLoyaltyCardRequest(Long requestId) {
		int rowsAffected = 0;
		String sql = "UPDATE LoyaltyCardRequest SET Status = 1 WHERE RequestId = ?;";
		
		try {
			rowsAffected = _jdbcTemplate.update(sql, requestId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		if (rowsAffected == 0)
			return false;
		
		return true;
	}
	
	
}
