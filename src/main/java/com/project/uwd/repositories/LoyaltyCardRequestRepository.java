package com.project.uwd.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.uwd.models.LoyaltyCardRequest;

import jakarta.annotation.PostConstruct;

@Repository
public interface LoyaltyCardRequestRepository {
	
	public boolean createLoyaltyCardRequest(Long userId);
	public List<LoyaltyCardRequest> getLoyaltyCardRequestsById(Long userId);
}
