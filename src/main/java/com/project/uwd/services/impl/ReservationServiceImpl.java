package com.project.uwd.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.uwd.models.Reservation;
import com.project.uwd.models.Ticket;
import com.project.uwd.repositories.ReservationRepository;
import com.project.uwd.repositories.TicketRepository;
import com.project.uwd.services.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService{

	@Autowired
	private ReservationRepository _reservationRepository;
	
	@Autowired
	private TicketRepository _ticketRepository;
	
	@Override
	public List<Reservation> getUserReservations(Long id) {
		return _reservationRepository.getUserReservations(id);
	}

	@Override
	public boolean createReservation(List<Ticket> reservationTickets, int points, double totalPrice, Long userId) {
		for (Ticket t : reservationTickets) {
			boolean isSeatAlreadyTaken = _ticketRepository.isSeatAlreadyTaken(t);
			if (!isSeatAlreadyTaken)
				return false;
		}
		return _reservationRepository.createReservation(reservationTickets, points, totalPrice, userId);
	}
	
}
