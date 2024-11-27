package com.project.uwd.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.project.uwd.models.Location;

@Repository
public interface LocationRepository {
	List<Location> getLocations();
	Location getLocation(Long id);
	Location deleteLocation(Long id);
	void addLocation(Location location);
	Location editLocation(Long id, Location location);
}
