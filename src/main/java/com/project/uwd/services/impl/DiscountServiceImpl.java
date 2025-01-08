package com.project.uwd.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.uwd.models.Discount;
import com.project.uwd.repositories.DiscountRepository;
import com.project.uwd.services.DiscountService;

@Service
public class DiscountServiceImpl implements DiscountService{

	@Autowired
	private DiscountRepository _discountRepository;
	
	@Override
	public boolean createDiscount(Discount discount) {
		return _discountRepository.createDiscount(discount);
	}

}
