package com.project.uwd.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.project.uwd.models.Airplane;

@Repository
public interface AirplaneRepository {
	public List<Airplane> getAllAirplanes();
	public Airplane getAirplaneById(Long id);
	public int addAirplane(Airplane airplane);
	public int editAirplane(Long id, Airplane airplane);
	public int deleteAirplaneById(Long id);
	public List<Airplane> getAvailableAirplanes();
}
