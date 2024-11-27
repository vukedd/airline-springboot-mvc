package com.project.uwd.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.uwd.models.Location;

@Service
public interface LocationService {

	List<Location> getLocations();
	Location getLocation(Long id);
	Location deleteLocation(Long id);
	void addLocation(Location location);
	Location editLocation(Long id, Location editLocation);
}
