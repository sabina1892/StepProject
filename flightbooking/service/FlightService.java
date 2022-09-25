package flightbooking.service;

import flightbooking.dao.FlightDao;
import flightbooking.entity.Flight;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class FlightService {

    private final FlightDao flightDao;

    public FlightService(FlightDao flightDao) {
        this.flightDao = flightDao;
    }

    public List<Flight> getAll() {
        return flightDao.getAll();
    }

    public List<Flight> search(String destination, LocalDateTime dateTime, int numberOfPeople) {
        return flightDao.getAll().stream()
                .filter(a -> a.getDestination().equals(destination) &&
                        a.getSeats() > numberOfPeople &&
                        a.getDateTime().equals(dateTime))
                .collect(Collectors.toList());
    }


}
