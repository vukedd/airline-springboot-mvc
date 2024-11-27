package com.project.uwd.repositories.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.project.uwd.helpers.CSVResourceProvider;
import com.project.uwd.models.Location;
import com.project.uwd.repositories.LocationRepository;

@Repository
public class LocationRepositoryImpl implements LocationRepository {
	
	public Location getLocation(Long id) {
		Location location = null;
		try {
			location = CSVResourceProvider.getInstance().getLocation(id);
		} catch (URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return location;
	}

	@Override
	public List<Location> getLocations() {
		List<Location> result = null;
		try {
			result = CSVResourceProvider.getInstance().getAllLocations();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Location deleteLocation(Long id) {
		Location deletedlocation = null;
		try {
			deletedlocation = CSVResourceProvider.getInstance().deleteLocation(id);
		} catch (URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return deletedlocation;
	}

	@Override
	public void addLocation(Location location) {
		try {
			CSVResourceProvider.getInstance().addLocation(location);
		} catch (URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Location editLocation(Long id, Location editLocation) {
		Location location = null;
		try {
			location = CSVResourceProvider.getInstance().editLocation(id, editLocation);
		} catch (URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return location;
	}
	
}
