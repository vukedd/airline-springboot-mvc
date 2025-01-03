package com.project.uwd.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.uwd.models.LoyaltyCardRequest;

@Service
public interface LoyaltyCardRequestService {
	
	public boolean createLoyaltyCardRequest(Long userId);
	public List<LoyaltyCardRequest> getLoyaltyCardRequestsById(Long userId);
}
