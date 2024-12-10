package com.project.uwd.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.project.uwd.models.Location;
import com.project.uwd.repositories.LocationRepository;
import com.project.uwd.services.LocationService;

@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	@Qualifier("LocationRepositoryDBImpl")
	private LocationRepository _locationRepository;
	
	@Override
	public Location getLocation(Long id) {
		return _locationRepository.getLocation(id);
	}

	@Override
	public List<Location> getLocations() {
		return _locationRepository.getLocations();
	}

	@Override
	public int deleteLocation(Long id) {
		return _locationRepository.deleteLocation(id);
	}

	@Override
	public int addLocation(Location location) {
		return _locationRepository.addLocation(location);
	}

	@Override
	public int editLocation(Long id, Location editLocation) {
		return _locationRepository.editLocation(id, editLocation);
	}
	
}
