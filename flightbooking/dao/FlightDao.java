package flightbooking.dao;

import flightbooking.entity.Flight;
import flightbooking.mapper.FlightMapper;

import java.util.List;

public class FlightDao implements Dao<Flight> {

    private static final String FILE_PATH = "C:\\Users\\hp\\IdeaProjects\\lesson1\\src\\main\\resources\\database\\flights.csv";
    private final FlightMapper flightMapper;
    private List<Flight> flights;

    public FlightDao() {
        flightMapper = new FlightMapper();
        flights = loadAllData();
    }

    @Override
    public Flight getById(Long id) {
        return flights.stream()
                .filter(flight -> id.equals(flight.getId()))
                .findFirst().orElse(null);
    }

    @Override
    public List<Flight> getAll() {
        flights = loadAllData();
        return flights;
    }

    @Override
    public void delete(Flight chosenFlight) {
        flights.remove(chosenFlight);
        writeDataToFile(flights, FILE_PATH);
    }

    @Override
    public Flight add(Flight flight) {
        flights.add(flight);
        writeDataToFile(flights, FILE_PATH);
        return flight;
    }

    private List<Flight> loadAllData() {
        return flightMapper.mapToEntity(readDataFromFile(FILE_PATH));
    }

}
