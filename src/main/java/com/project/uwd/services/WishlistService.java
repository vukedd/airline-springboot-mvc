package com.project.uwd.services;

import org.springframework.stereotype.Service;

import com.project.uwd.models.Wishlist;

@Service
public interface WishlistService {
	public Wishlist getWishlistByUserId(Long userId);
	public boolean addWishlistItem(Long flightId, Long userId);
	public boolean removeWishlistItem(Long userId, Long flightId);
}
