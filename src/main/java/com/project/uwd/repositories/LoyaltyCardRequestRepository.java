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
	public boolean isLoyaltyCardRequestAlreadySent(Long userId);
	public List<LoyaltyCardRequest> getLoyaltyCardRequests();
	public LoyaltyCardRequest getLoyaltyCardRequestById(Long requestId);
	public boolean approveLoyaltyCardRequest(Long requestId, Long userId);
	public boolean denyLoyaltyCardRequest(Long requestId);
}
