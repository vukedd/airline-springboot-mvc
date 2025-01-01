package com.project.uwd.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.uwd.models.Reservation;
import com.project.uwd.repositories.ReservationRepository;
import com.project.uwd.services.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService{

	@Autowired
	private ReservationRepository _reservationRepository;
	
	@Override
	public List<Reservation> getUserReservations(Long id) {
		return _reservationRepository.getUserReservations(id);
	}
	
}
