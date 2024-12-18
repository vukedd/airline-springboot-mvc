package com.project.uwd.models;

public class Airport {
	private Long id;
	private String code;
	private Location location;
	private Long locationId;
	
	public Airport(Long id, String code, Location location) {
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	@Override
	public String toString() {
		return "Airport [id=" + id + ", code=" + code + ", location=" + location.getCity() + ", locationId=" + locationId + "]";
	}
}
