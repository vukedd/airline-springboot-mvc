package com.project.uwd.models;

public class FlightCancelation {
	private Long id;
	private Flight cancelledFlight;
	private String cancellationReason;
	
	public FlightCancelation() {
		super();
	}

	public FlightCancelation(Long id, Flight flightCancelled, String cancellationReason) {
		super();
		this.id = id;
		this.cancelledFlight = flightCancelled;
		this.cancellationReason = cancellationReason;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Flight getFlightCancelled() {
		return cancelledFlight;
	}

	public void setFlightCancelled(Flight flightCancelled) {
		this.cancelledFlight = flightCancelled;
	}

	public String getCancellationReason() {
		return cancellationReason;
	}

	public void setCancellationReason(String cancellationReason) {
		this.cancellationReason = cancellationReason;
	}
	
}
