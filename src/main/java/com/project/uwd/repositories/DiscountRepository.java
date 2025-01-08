package com.project.uwd.repositories;

import org.springframework.stereotype.Repository;

import com.project.uwd.models.Discount;

@Repository
public interface DiscountRepository {
	public boolean createDiscount(Discount discount);
	public Discount getDiscountByFlightId(Long flightId);
}
