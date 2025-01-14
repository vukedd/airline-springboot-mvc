package com.project.uwd.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.project.uwd.models.Reservation;
import com.project.uwd.models.Ticket;

@Repository
public interface ReservationRepository {
	public List<Reservation> getUserReservations(Long id);
	public boolean createReservation(List<Ticket> reservationTickets, int points, double totalPrice, Long userId);
}
