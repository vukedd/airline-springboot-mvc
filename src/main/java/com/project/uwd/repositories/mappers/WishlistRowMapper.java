package com.project.uwd.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.uwd.models.Wishlist;

public class WishlistRowMapper implements RowMapper<Wishlist>{

	@Override
	public Wishlist mapRow(ResultSet rs, int rowNum) throws SQLException {
		Wishlist wishlist = new Wishlist();
		wishlist.setId(rs.getLong(1));
		wishlist.setUserId(rs.getLong(2));
		
		return wishlist;
	}

}
