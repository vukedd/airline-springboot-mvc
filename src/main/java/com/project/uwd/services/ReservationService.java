package com.project.uwd.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.uwd.models.Reservation;
import com.project.uwd.models.Ticket;
import com.project.uwd.repositories.ReservationRepository;

@Service
public interface ReservationService {
	
	public List<Reservation> getUserReservations(Long id);
	public boolean createReservation(List<Ticket> reservationTickets, int points, double totalPrice, Long userId);
}
