package com.project.uwd.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.uwd.models.Airplane;
import com.project.uwd.repositories.AirplaneRepository;
import com.project.uwd.services.AirplaneService;

@Service
public class AirplaneServiceImpl implements AirplaneService{

	@Autowired
	private AirplaneRepository _airplaneRepository;
	
	@Override
	public List<Airplane> getAllAirplanes() {
		return _airplaneRepository.getAllAirplanes();
	}

	@Override
	public Airplane getAirplaneById(Long id) {
		return _airplaneRepository.getAirplaneById(id);
	}

	@Override
	public int addAirplane(Airplane airplane) {
		return _airplaneRepository.addAirplane(airplane);
	}

	@Override
	public int editAirplane(Long id, Airplane airplane) {
		return _airplaneRepository.editAirplane(id, airplane);
	}

	@Override
	public int deleteAirplaneById(Long id) {
		return _airplaneRepository.deleteAirplaneById(id);
	}

	@Override
	public List<Airplane> getAvailableAirplanes() {
		// TODO Auto-generated method stub
		return _airplaneRepository.getAvailableAirplanes();
	}

}
