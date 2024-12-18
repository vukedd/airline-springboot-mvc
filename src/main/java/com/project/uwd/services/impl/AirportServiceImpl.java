package com.project.uwd.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.uwd.models.Airport;
import com.project.uwd.repositories.AirportRepository;
import com.project.uwd.services.AirportService;

@Service
public class AirportServiceImpl implements AirportService{

	@Autowired
	private AirportRepository _airportRepository;
	
	@Override
	public List<Airport> getAllAiports() {
		return _airportRepository.getAllAiports();
	}

	@Override
	public Airport getAirportById(Long id) {
		return _airportRepository.getAirportById(id);
	}

	@Override
	public int addAirport(Airport airport) {
		return _airportRepository.addAirport(airport);
	}

	@Override
	public int deleteAirport(Long id) {
		return _airportRepository.deleteAirport(id);
	}

	@Override
	public int editAirport(Long id, Airport airport) {
		return _airportRepository.editAirport(id, airport);
	}

}
