package com.project.uwd.models;

public class Airport {
	private Long id;
	private int code;
	private Location location;
	
	public Airport(Long id, int code, Location location) {
		super();
		this.id = id;
		this.code = code;
		this.location = location;
	}

	public Airport() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
}
