package com.project.uwd.models;

import com.project.uwd.models.enums.Status;

public class LoyaltyCardRequest {
	public Long id;
	public User requestedBy;
	public Long requestedById;
	public Status status;
	
	public LoyaltyCardRequest(Long id, User requestedBy, Status status) {
		super();
		this.id = id;
		this.requestedBy = requestedBy;
		this.status = status;
	}

	public LoyaltyCardRequest() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(User requestedBy) {
		this.requestedBy = requestedBy;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Long getRequestedById() {
		return requestedById;
	}

	public void setRequestedById(Long requestedById) {
		this.requestedById = requestedById;
	}
}
