package com.project.uwd.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.uwd.models.Location;

@Service
public interface LocationService {

	List<Location> getLocations();
	Location getLocation(Long id);
	int deleteLocation(Long id);
	int addLocation(Location location);
	int editLocation(Long id, Location editLocation);
}
