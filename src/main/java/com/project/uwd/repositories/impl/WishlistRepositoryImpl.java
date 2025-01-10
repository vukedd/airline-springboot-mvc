package com.project.uwd.repositories.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.uwd.models.Flight;
import com.project.uwd.models.Wishlist;
import com.project.uwd.repositories.FlightRepository;
import com.project.uwd.repositories.WishlistRepository;
import com.project.uwd.repositories.mappers.WishlistRowMapper;

import jakarta.annotation.PostConstruct;

@Repository
public class WishlistRepositoryImpl implements WishlistRepository {
	
	@Autowired
	private JdbcTemplate _jdbcTemplate;
	
	@Autowired
	private FlightRepository _flightRepository;
	
	private WishlistRowMapper _wishlistRowMapper;
	
	@PostConstruct
	public void init() {
		_wishlistRowMapper = new WishlistRowMapper();
	}
	
	@Override
	public boolean addWishlistItem(Long flightId, Long userId) {
		String sql = "INSERT INTO WishlistItem(FlightId, WishlistId) VALUES (?, (SELECT WishlistId\r\n"
				+ "FROM Wishlist\r\n"
				+ "WHERE UserId = ?));";
		
		try {
			_jdbcTemplate.update(sql, flightId, userId);
		}
		catch (Exception e) {
			System.out.println("An error occurred while adding wishlist item!");
			System.out.println(e.getMessage());
			return false;
		}
		
		return true;
	}

	@Override
	public boolean removeWishlistItemById(Long userId, Long flightId) {
		String sql = "DELETE FROM WishlistItem WHERE WishlistId = (SELECT WishlistId FROM Wishlist WHERE UserId = ?) AND FlightId = ?;";
		
		try {
			_jdbcTemplate.update(sql, userId, flightId);
		} catch (Exception e) {
			System.out.println("An error occurred while removing item from wishlist!");
			return false;
		}
		
		return true;
	}

	@Override
	public Wishlist getWishlistByUserId(Long userId) {
		Wishlist wishlist = null;
		String sql = "SELECT *\r\n"
					+ "FROM Wishlist\r\n"
					+ "WHERE UserId = ?;";
		
		try {
			wishlist = _jdbcTemplate.queryForObject(sql, _wishlistRowMapper, userId);
		} catch (Exception e) {
			return null;
		}
		
		if (wishlist != null) {
			wishlist.setItems(_flightRepository.getWishlistItemsByUserId(userId));
			for (Flight flight : wishlist.getItems()) {
				System.out.println(flight.getId());
			}
		}
		
		return wishlist;
	}

	@Override
	public boolean createWishlist(Long userId) {
		String sql = "INSERT INTO Wishlist(userId) VALUES (?);";
		int res = 0;
		
		try {
			res = _jdbcTemplate.update(sql, userId);
			System.out.println("created");
		} catch (Exception e) {
			res = 0;
		}
	
		return res != 0;
	}

}
