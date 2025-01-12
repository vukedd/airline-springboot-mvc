package com.project.uwd.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.project.uwd.models.Location;

@Repository
public interface LocationRepository {
	List<Location> getLocations();
	Location getLocation(Long id);
	int deleteLocation(Long id);
	int addLocation(Location location, MultipartFile locationImage);
	int editLocation(Long id, Location location);
}
