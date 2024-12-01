package com.project.uwd.helpers;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.project.uwd.models.Location;
import com.project.uwd.models.LoyaltyCard;
import com.project.uwd.models.User;
import com.project.uwd.models.enums.Continent;
import com.project.uwd.models.enums.Role;

public class CSVResourceProvider {
	private static CSVResourceProvider instance;
	private Map<Long, Location> locations;
	private Map<Long, User> users;
	private Long locationLastId = 0L;
	private Long userLastId = 0L;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");

	private CSVResourceProvider() throws URISyntaxException, IOException {
		locations = new HashMap<Long, Location>();
		users = new HashMap<Long, User>();

		loadLocations();
		loadUsers();
	}
	
	public static CSVResourceProvider getInstance() throws URISyntaxException, IOException {
		if (instance == null) {
			synchronized (CSVResourceProvider.class) {
				if (instance == null) {
					instance = new CSVResourceProvider();
				}
			}
		}

		return instance;
	}

	private void loadLocations() throws IOException, URISyntaxException {
		Path path = Paths.get((getClass().getClassLoader().getResource("Location.csv").toURI()));
		System.out.println(path);
		List<String> data = Files.readAllLines(path);
		for (String token : data) {
			String[] locationData = token.split(",");
			Long id = Long.parseLong(locationData[0]);
			String city = locationData[1];
			String country = locationData[2];
			Continent continent = Continent.values()[Integer.parseInt(locationData[3])];

			if (id > locationLastId) {
				locationLastId = id;
			}
			locations.put(id, new Location(id, city, country, continent));
		}
	}

	private void loadUsers() throws URISyntaxException, IOException {
		Path path = Paths.get((getClass().getClassLoader().getResource("Users.csv").toURI()));
		List<String> data = Files.readAllLines(path);
		for (String token : data) {
			System.out.println(token);
			String[] userData = token.split(",");
			Long id = Long.parseLong(userData[0]);
			String username = userData[1];
			String password = userData[2];
			String firstName = userData[3];
			String lastName = userData[4];
			String email = userData[5];
			LocalDate dateOfBirth = LocalDate.parse(userData[6], formatter);
			LocalDate dateOfRegistration = LocalDate.parse(userData[7], formatter);
			Role role = Role.values()[Integer.parseInt(userData[8])];
			LoyaltyCard loyaltyCard = null;
			if (Integer.parseInt(userData[9]) != 0) {

			}

			if (id > userLastId) {
				userLastId = id;
			}

			users.put(id, new User(id, username, password, email, firstName, lastName, dateOfBirth, dateOfRegistration,
					loyaltyCard, role));
		}
	}

	private void saveLocationData() throws URISyntaxException, IOException {
		Path path = Paths.get((getClass().getClassLoader().getResource("Location.csv").toURI()));
		List<String> locationsData = new ArrayList<String>();
		for (Location location : locations.values()) {
			System.out.println(location);
			locationsData.add(location.getId() + "," + location.getCity() + "," + location.getCountry() + ","
					+ location.getContinent().ordinal());
		}
		
		Files.write(path, locationsData);
	}

	private void saveUserData() throws URISyntaxException, IOException {
		Path path = Paths.get((getClass().getClassLoader().getResource("Users.csv").toURI()));
		System.out.println(path);
		List<String> userData = new ArrayList<String>();
		for (User u : users.values()) {
			System.out.println(u);
			userData.add(u.getId() + "," + u.getUsername() + "," + u.getPassword() + "," + u.getFirstName() + ","
					+ u.getLastName() + "," + u.getEmail() + "," + u.getDateOfBirth().format(formatter).toString() + ","
					+ u.getDateOfRegistration().format(formatter).toString() + "," + u.getRole().ordinal() + "," + 0);
		}
		
		for (String user : userData) {
			System.out.println(user);
		}
		Files.write(path, userData);
	}

	public void addUser(User user) throws URISyntaxException, IOException {
		if (user.getId() == null) {
			user.setId(++userLastId);
		}

		if (user.getRole() == null) {
			user.setRole(Role.Tourist);
		}

		if (user.getDateOfRegistration() == null) {
			user.setDateOfRegistration(LocalDate.now());
		}
		
		users.put(user.getId(), user);
		saveUserData();
	}

	public List<User> getAllUsers() {
		return new ArrayList(users.values());
	}

	public List<Location> getAllLocations() {
		return new ArrayList(locations.values());
	}

	public Location getLocation(Long id) {
		if (locations.containsKey(id)) {
			return locations.get(id);
		}

		return null;
	}

	public Location deleteLocation(Long id) throws URISyntaxException, IOException {
		if (locations.containsKey(id)) {
			Location deletedLocation = locations.get(id);
			locations.remove(id);
			saveLocationData();
			return deletedLocation;
		}

		return null;
	}

	public void addLocation(Location location) throws URISyntaxException, IOException {
		if (location.getId() == null) {
			location.setId(++locationLastId);
		}

		locations.put(locationLastId, location);
		saveLocationData();
	}

	public Location editLocation(Long id, Location editLocation) throws URISyntaxException, IOException {
		Location location = getLocation(id);
		if (location == null)
			return null;

		location.setCity(editLocation.getCity());
		location.setCountry(editLocation.getCountry());
		location.setContinent(editLocation.getContinent());
		saveLocationData();
		return location;
	}
}
