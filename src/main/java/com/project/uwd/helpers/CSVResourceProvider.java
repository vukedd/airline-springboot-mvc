package com.project.uwd.helpers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.project.uwd.models.Location;
import com.project.uwd.models.enums.Continent;

public class CSVResourceProvider {
	private static CSVResourceProvider instance;
	private Map<Long, Location> locations;
	private Long lastId = 0L;
	
	private CSVResourceProvider() throws URISyntaxException, IOException {
		locations = new HashMap<Long,Location>();
		
		Path path = Paths.get((getClass().getClassLoader().getResource("Location.csv").toURI()));
		List<String> data = Files.readAllLines(path);
		for (String token : data) {
			String[] locationData = token.split(",");
			Long id = Long.parseLong(locationData[0]);
			String city = locationData[1];
			String country = locationData[2];
			Continent continent = Continent.values()[Integer.parseInt(locationData[3])];
			
			if (id > lastId) {
				lastId = id;
			}
			locations.put(id, new Location(id, city, country,continent));
			
		}
	}
	
	private void saveData() throws URISyntaxException, IOException {
		Path path = Paths.get((getClass().getClassLoader().getResource("Location.csv").toURI()));
		List<String> locationsData = new ArrayList<String>();
		for (Location location : locations.values()) {
			locationsData.add(location.getId() + "," + location.getCity() + "," + location.getCountry() + "," + location.getContinent().ordinal());
		}
		
		Files.write(path, locationsData);
		System.out.println("saved");
	}

	
	public static CSVResourceProvider getInstance() throws URISyntaxException, IOException {
		if (instance == null) {
			synchronized (CSVResourceProvider.class){
				if (instance == null) {
					instance = new CSVResourceProvider();
				}
			}
		}
		
		return instance;
	}
	
	public List<Location> getAllLocations() {
		return new ArrayList(locations.values());
	}
	
	public Location getLocation(Long id) {
		if (locations.containsKey(id)) 
		{
			return locations.get(id);
		}

		return null;
	}
	
	public Location deleteLocation(Long id) throws URISyntaxException, IOException {
		if (locations.containsKey(id)) 
		{
			Location deletedLocation = locations.get(id);
			locations.remove(id);
			saveData();
			return deletedLocation;
		}
		
		return null;
	}
	
	public void addLocation(Location location) throws URISyntaxException, IOException {
		if (location.getId() == null) {
			location.setId(++lastId);
		}
		
		locations.put(lastId, location);
		saveData();
	}
	
	public Location editLocation(Long id, Location editLocation) throws URISyntaxException, IOException {
		Location location = getLocation(id);
		if (location == null)
			return null;
		
		location.setCity(editLocation.getCity());
		location.setCountry(editLocation.getCountry());
		location.setContinent(editLocation.getContinent());
		saveData();
		return location;
	}
}
