package com.project.uwd.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.project.uwd.models.Airport;

@Repository
public interface AirportRepository {
	public List<Airport> getAllAiports();
	public Airport getAirportById(Long id);
	public int addAirport(Airport airport);
	public int deleteAirport(Long id);
	public int editAirport(Long id, Airport airport);
}
