package com.project.uwd.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.project.uwd.models.Reservation;

@Repository
public interface ReservationRepository {
	public List<Reservation> getUserReservations(Long id);
}
