package com.project.uwd.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.uwd.models.Airplane;

@Service
public interface AirplaneService {
	public List<Airplane> getAllAirplanes();
	public Airplane getAirplaneById(Long id);
	public int addAirplane(Airplane airplane);
	public int editAirplane(Long id, Airplane airplane);
	public int deleteAirplaneById(Long id);
	public List<Airplane> getAvailableAirplanes();
}
