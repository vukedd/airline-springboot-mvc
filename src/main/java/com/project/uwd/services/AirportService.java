package com.project.uwd.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.project.uwd.models.Airport;

@Service
public interface AirportService {
	public List<Airport> getAllAiports();
	public Airport getAirportById(Long id);
	public int addAirport(Airport airport);
	public int deleteAirport(Long id);
	public int editAirport(Long id, Airport airport);
}
