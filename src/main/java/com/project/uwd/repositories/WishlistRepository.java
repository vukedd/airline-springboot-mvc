package com.project.uwd.repositories;

import org.springframework.stereotype.Repository;
import com.project.uwd.models.Wishlist;

@Repository
public interface WishlistRepository {
	
	public boolean addWishlistItem(Long flightId, Long userId);
	public boolean removeWishlistItemById(Long userId, Long FlightId);
	public Wishlist getWishlistByUserId(Long userId);
	public boolean createWishlist(Long userId);
}
