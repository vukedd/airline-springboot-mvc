package com.project.uwd.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.uwd.models.Wishlist;
import com.project.uwd.repositories.WishlistRepository;
import com.project.uwd.services.WishlistService;

@Service
public class WishlistServiceImpl implements WishlistService {
	
	@Autowired
	private WishlistRepository _wishlistRepository;

	@Override
	public Wishlist getWishlistByUserId(Long userId) {
		Wishlist wishlist = _wishlistRepository.getWishlistByUserId(userId);
		
		if (wishlist == null) {
			boolean wishlistCreated = _wishlistRepository.createWishlist(userId);
			if (wishlistCreated) {
				System.out.println("wishlist initialized!");
				return new Wishlist();
			}
		}
		
		return wishlist;
	}

	@Override
	public boolean addWishlistItem(Long flightId, Long userId) {
		return _wishlistRepository.addWishlistItem(flightId, userId);
	}

	@Override
	public boolean removeWishlistItem(Long userId, Long flightId) {
		return _wishlistRepository.removeWishlistItemById(userId, flightId);
	}
	
}
