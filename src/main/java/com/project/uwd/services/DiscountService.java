package com.project.uwd.services;

import org.springframework.stereotype.Service;

import com.project.uwd.models.Discount;

@Service
public interface DiscountService {
	public boolean createDiscount(Discount discount);
}
