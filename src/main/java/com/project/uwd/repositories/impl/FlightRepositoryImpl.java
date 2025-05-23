package com.project.uwd.repositories.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.project.uwd.models.Discount;
import com.project.uwd.models.Flight;
import com.project.uwd.models.Ticket;
import com.project.uwd.repositories.AirplaneRepository;
import com.project.uwd.repositories.AirportRepository;
import com.project.uwd.repositories.DiscountRepository;
import com.project.uwd.repositories.FlightRepository;
import com.project.uwd.repositories.TicketRepository;
import com.project.uwd.repositories.mappers.FlightRowMapper;

import jakarta.annotation.PostConstruct;

@Repository
public class FlightRepositoryImpl implements FlightRepository {

	@Autowired
	private JdbcTemplate _jdbcTemplate;

	@Autowired
	private AirportRepository _airportRepository;

	@Autowired
	private AirplaneRepository _airplaneRepository;

	@Autowired
	private DiscountRepository _discountRepository;

	private FlightRowMapper _flightRowMapper;

	@PostConstruct
	public void init() {
		_flightRowMapper = new FlightRowMapper();
	}

	@Override
	public List<Flight> getAllFlights() {
		String sql = "SELECT * FROM flight WHERE IsCancelled = 0 AND DateOfDeparture > current_date();";
		List<Flight> flights;

		try {
			flights = _jdbcTemplate.query(sql, _flightRowMapper);
		} catch (Exception e) {
			flights = null;
		}

		if (flights != null) {
			for (Flight flight : flights) {
				flight.setAirplane(_airplaneRepository.getAirplaneById(flight.getAirplaneId()));
				flight.setDeparture(_airportRepository.getAirportById(flight.getDepartureId()));
				flight.setDestination(_airportRepository.getAirportById(flight.getDestinationId()));

				Discount discount = _discountRepository.getDiscountByFlightId(flight.getId());
				if (discount != null) {
					flight.setDiscount(discount);
					flight.setOnDiscount(true);
				}
				flight.setAvailableSeats(numberOfAvailableSpotsByFlight(flight.getId()) > 0 ? true : false);
			}
		}

		return flights;
	}

	@Override
	public Flight getFlightById(Long id) {
		String sql = "SELECT * FROM flight WHERE flightId = ?;";
		Flight flight;
		try {
			flight = _jdbcTemplate.queryForObject(sql, _flightRowMapper, id);
		} catch (Exception e) {
			flight = null;
		}

		if (flight != null) {
			flight.setAirplane(_airplaneRepository.getAirplaneById(flight.getAirplaneId()));
			flight.setDeparture(_airportRepository.getAirportById(flight.getDepartureId()));
			flight.setDestination(_airportRepository.getAirportById(flight.getDestinationId()));

			Discount discount = _discountRepository.getDiscountByFlightId(flight.getId());
			if (discount != null) {
				flight.setDiscount(discount);
				flight.setOnDiscount(true);
			}
			flight.setAvailableSeats(numberOfAvailableSpotsByFlight(flight.getId()) > 0 ? true : false);
		}
		return flight;
	}

	@Override
	public int deleteFlight(Long id) {
		String sql = "DELETE FROM flight\r\n" + "WHERE FlightId = ? AND FlightId NOT IN (SELECT FlightId FROM ticket WHERE FlightId IS NOT NULL);";
		int res;
		try {
			res = _jdbcTemplate.update(sql, id);
		} catch (Exception e) {
			res = 0;
		}

		return res;
	}

	@Override
	public int editFlight(Long id, Flight flight) {
		String sql = "UPDATE flight Set DateOfDeparture = ?, Duration = ?, TicketPrice = ? WHERE FlightId = ?;";
		int res;

		try {
			res = _jdbcTemplate.update(sql, LocalDateTime.of(flight.getDateOfDeparture(), flight.getTimeOfDeparture()),
					flight.getDuration(), flight.getTicketPrice(), id);
		} catch (Exception e) {
			res = 0;
		}
		return res;
	}

	@Override
	public int createFlight(Flight flight) {
		String sql = "INSERT INTO flight(DateOfDeparture, Duration, TicketPrice, DepartureId, DestinationId, AirplaneId, IsCancelled) VALUES (?, ?, ?, ?, ?, ?, 0);";
		int res;

		try {
			res = _jdbcTemplate.update(sql, LocalDateTime.of(flight.getDateOfDeparture(), flight.getTimeOfDeparture()),
					flight.getDuration(), flight.getTicketPrice(), flight.getDepartureId(), flight.getDestinationId(),
					flight.getAirplaneId());
		} catch (Exception e) {
			res = 0;
		}

		return res;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean cancelFlight(Long id, String cancelationReason) {
		String sql1 = "UPDATE flight SET IsCancelled = 1 WHERE FlightId = ?;";
		String sql2 = "UPDATE loyaltycard lc\r\n" + "SET lc.points = lc.points + 5\r\n"
				+ "WHERE lc.LoyaltyCardId in (SELECT LoyaltyCardId\r\n" 
				+ "						   FROM user\r\n"
				+ "						   WHERE UserId in (SELECT UserId\r\n"
				+ "											FROM reservation\r\n"
				+ "                                            WHERE ReservationId in (SELECT ReservationId\r\n"
				+ "																	FROM ticket\r\n"
				+ "																	Where FlightId = ?)));";

		String sql3 = "INSERT INTO flightcancelation(CancelationReason, FlightId) VALUES (?, ?);";
		int res1, res2, res3;
		try {

			res1 = _jdbcTemplate.update(sql1, id);
			res2 = _jdbcTemplate.update(sql2, id);
			res3 = _jdbcTemplate.update(sql3, cancelationReason, id);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}

		return true;
	}

	@Override
	public List<Flight> searchFlights(String departure, String destination, LocalDate dateOfDeparture,
			int numberOfSeats, boolean similarFlights) {
		String sql;
		List<Flight> flights = new ArrayList<Flight>();
		String departureChecker = !departure.equals(null) ? departure + "%" : "%";
		String destinationChecker = !destination.equals(null) ? destination + "%" : "%";

		if (dateOfDeparture == null) {
			sql = "SELECT *\r\n" + "FROM flight f\r\n"
					+ "LEFT JOIN airport departure ON departure.AirportId = f.DepartureId\r\n"
					+ "LEFT JOIN location location1 ON departure.LocationId = location1.LocationId\r\n"
					+ "LEFT JOIN airport destination ON destination.AirportId = f.DestinationId\r\n"
					+ "LEFT JOIN location location2 ON destination.LocationId = location2.LocationId\r\n"
					+ "WHERE (departure.AirportCode like ? OR location1.country like ? OR location1.city like ?) AND (destination.AirportCode like ? OR location2.country like ? OR location2.city like ?) AND IsCancelled = 0 AND DateOfDeparture > current_date();";
			try {
				flights = _jdbcTemplate.query(sql, _flightRowMapper, departureChecker, departureChecker,
						departureChecker, destinationChecker, destinationChecker, destinationChecker);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				flights = null;
			}

		} else {
			if (!similarFlights) {
				sql = "SELECT * FROM flight f\r\n"
						+ "LEFT JOIN airport departure ON departure.AirportId = f.DepartureId\r\n"
						+ "LEFT JOIN location location1 ON departure.LocationId = location1.LocationId\r\n"
						+ "LEFT JOIN airport destination ON destination.AirportId = f.DestinationId\r\n"
						+ "LEFT JOIN location location2 ON destination.LocationId = location2.LocationId\r\n"
						+ "WHERE (departure.AirportCode like ? OR location1.country like ? OR location1.city like ?) AND (destination.AirportCode like ? OR location2.country like ? OR location2.city like ?) AND IsCancelled = 0 AND DATE(DateOfDeparture) = ?;";
				try {
					flights = _jdbcTemplate.query(sql, _flightRowMapper, departureChecker, departureChecker,
							departureChecker, destinationChecker, destinationChecker, destinationChecker,
							dateOfDeparture);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					flights = null;
				}
			} else {
				sql = "SELECT *\r\n" + "FROM flight f\r\n"
						+ "LEFT JOIN airport departure ON departure.AirportId = f.DepartureId\r\n"
						+ "LEFT JOIN location location1 ON departure.LocationId = location1.LocationId\r\n"
						+ "LEFT JOIN airport destination ON destination.AirportId = f.DestinationId\r\n"
						+ "LEFT JOIN location location2 ON destination.LocationId = location2.LocationId\r\n"
						+ "WHERE (departure.AirportCode like ? OR location1.country like ? OR location1.city like ?) AND (destination.AirportCode like ? OR location2.country like ? OR location2.city like ?) AND f.isCancelled = 0 AND (DATEDIFF(?, f.dateOfDeparture) <= 2 AND DATEDIFF(?, f.dateOfDeparture) >= -2);;";

				try {
					flights = _jdbcTemplate.query(sql, _flightRowMapper, departureChecker, departureChecker,
							departureChecker, destinationChecker, destinationChecker, destinationChecker,
							dateOfDeparture, dateOfDeparture);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					flights = null;
				}
			}
		}

		List<Flight> flightsWithEnoughSeats = new ArrayList<Flight>();

		if (flights != null) {
			for (Flight flight : flights) {
				flight.setAirplane(_airplaneRepository.getAirplaneById(flight.getAirplaneId()));

				if (numberOfAvailableSpotsByFlight(flight.getId()) - numberOfSeats >= 0) {
					flight.setDeparture(_airportRepository.getAirportById(flight.getDepartureId()));
					flight.setDestination(_airportRepository.getAirportById(flight.getDestinationId()));
					flightsWithEnoughSeats.add(flight);

					Discount discount = _discountRepository.getDiscountByFlightId(flight.getId());
					if (discount != null) {
						flight.setDiscount(discount);
						flight.setOnDiscount(true);
					}
					flight.setAvailableSeats(numberOfAvailableSpotsByFlight(flight.getId()) > 0 ? true : false);
				}

			}
		}
		
		return flightsWithEnoughSeats;
	}

	@Override
	public int numberOfAvailableSpotsByFlight(Long flightId) {
		String sql = "SELECT (airplane.numberOfRows * airplane.numberOfColumns) - COUNT(ticket.ticketId) AS 'Free seats' FROM airplane LEFT JOIN flight ON airplane.airplaneId = flight.airplaneId LEFT JOIN ticket ON flight.flightId = ticket.flightId WHERE flight.flightId = ?;\r\n";
		Integer numberOfFreeSeats;
		try {
			numberOfFreeSeats = _jdbcTemplate.queryForObject(sql, new Object[] { flightId }, Integer.class);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("An error occurred while fetching the number of free seats, flightRepository");
			return -1;
		}

		return numberOfFreeSeats;
	}

	@Override
	public List<Flight> getFlightsOnDiscount() {
		List<Flight> discountedFlights = null;
		String sql = "SELECT *\r\n" + "FROM flight\r\n" + "WHERE FlightId in (SELECT FlightId\r\n"
				+ "				   FROM discount\r\n"
				+ "                   WHERE DiscountValidDate > current_date()) AND IsCancelled = 0 AND DateOfDeparture > current_date();";

		try {
			discountedFlights = _jdbcTemplate.query(sql, _flightRowMapper);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		if (discountedFlights != null) {
			for (Flight flight : discountedFlights) {
				flight.setAirplane(_airplaneRepository.getAirplaneById(flight.getAirplaneId()));
				flight.setDeparture(_airportRepository.getAirportById(flight.getDepartureId()));
				flight.setDestination(_airportRepository.getAirportById(flight.getDestinationId()));
				Discount discount = _discountRepository.getDiscountByFlightId(flight.getId());
				if (discount != null) {
					flight.setDiscount(discount);
					flight.setOnDiscount(true);
				}
			}
		}

		return discountedFlights;
	}

	@Override
	public List<Flight> getWishlistItemsByUserId(Long userId) {
		List<Flight> wishlist = new ArrayList<Flight>();
		String sql = "SELECT f.FlightId, f.DateOfDeparture, f.Duration, f.TicketPrice, f.DepartureId, f.DestinationId, f.AirplaneId, f.IsCancelled\r\n"
				+ "FROM wishlist wl\r\n" + "LEFT JOIN wishlistitem it on wl.WishlistId = it.WishlistId\r\n"
				+ "LEFT JOIN flight f on it.FlightId = f.FlightId\r\n" + "WHERE UserId = ?;";

		try {
			wishlist = _jdbcTemplate.query(sql, _flightRowMapper, userId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Error");
			System.out.println("There are no items in this wishlist!");
		}

		if (wishlist.size() > 0) {
			for (Flight flight : wishlist) {
				flight.setAirplane(_airplaneRepository.getAirplaneById(flight.getAirplaneId()));
				flight.setDeparture(_airportRepository.getAirportById(flight.getDepartureId()));
				flight.setDestination(_airportRepository.getAirportById(flight.getDestinationId()));
				Discount discount = _discountRepository.getDiscountByFlightId(flight.getId());
				if (discount != null) {
					flight.setDiscount(discount);
					flight.setOnDiscount(true);
				}
				flight.setAvailableSeats(numberOfAvailableSpotsByFlight(flight.getId()) > 0 ? true : false);
			}
		}

		return wishlist;
	}

	@Override
	public List<Flight[]> getConnectedFlights(String departure, String destination) {
		List<Flight> flightsToDestination = new ArrayList<Flight>();
		List<Flight> flightsFromDeparture = new ArrayList<Flight>();
		
		String departureChecker = !departure.equals(null) ? departure + "%" : "%";
		String destinationChecker = !destination.equals(null) ? destination + "%" : "%";
		
		String sql1 = "SELECT *\r\n"
				+ "FROM flight f\r\n"
				+ "LEFT JOIN airport departure ON departure.AirportId = f.DepartureId\r\n"
				+ "LEFT JOIN location location1 ON departure.LocationId = location1.LocationId\r\n"
				+ "LEFT JOIN airport destination ON destination.AirportId = f.DestinationId\r\n"
				+ "LEFT JOIN location location2 ON destination.LocationId = location2.LocationId\r\n"
				+ "WHERE (departure.AirportCode like ? OR location1.country like ? OR location1.city like ?) AND IsCancelled = 0 AND DateOfDeparture > current_date();";
		
		String sql2 = "SELECT *\r\n"
				+ "FROM flight f\r\n"
				+ "LEFT JOIN airport departure ON departure.AirportId = f.DepartureId\r\n"
				+ "LEFT JOIN location location1 ON departure.LocationId = location1.LocationId\r\n"
				+ "LEFT JOIN airport destination ON destination.AirportId = f.DestinationId\r\n"
				+ "LEFT JOIN location location2 ON destination.LocationId = location2.LocationId\r\n"
				+ "WHERE (destination.AirportCode like ? OR location2.country like ? OR location2.city like ?) AND IsCancelled = 0 AND DateOfDeparture > current_date();";
		
		try {
			flightsFromDeparture = _jdbcTemplate.query(sql1, _flightRowMapper, departure, departure, departure);
		} catch (Exception e) {
			System.out.println("An error occurred while fetching flights connected to departure!");
		}
		
		try {
			flightsToDestination = _jdbcTemplate.query(sql2, _flightRowMapper, destination, destination, destination);
		} catch (Exception e) {
			System.out.println("An error occurred while fetching flights connected to destination!");
		}
		
		List<Flight[]> connectedFlights = new ArrayList<>();
		connectedFlights = Flight.connectFlights(flightsFromDeparture, flightsToDestination, getAllFlights());
		
//		if (connectedFlights.size() > 0) {
//			for (Flight[] connection : connectedFlights) {
//				System.out.println("-----connection-----");
//				for (Flight flight : connection) {
//					System.out.println(flight);
//				}
//			}
//		}
		
		if (connectedFlights != null) {
			for (Flight[] flights: connectedFlights) {
				for (Flight flight : flights) {
					flight.setAirplane(_airplaneRepository.getAirplaneById(flight.getAirplaneId()));
					flight.setDeparture(_airportRepository.getAirportById(flight.getDepartureId()));
					flight.setDestination(_airportRepository.getAirportById(flight.getDestinationId()));
	
					Discount discount = _discountRepository.getDiscountByFlightId(flight.getId());
					if (discount != null) {
						flight.setDiscount(discount);
						flight.setOnDiscount(true);
					}
					flight.setAvailableSeats(numberOfAvailableSpotsByFlight(flight.getId()) > 0 ? true : false);
				}
			}
		}
		
		return connectedFlights;
	}
	
	

}