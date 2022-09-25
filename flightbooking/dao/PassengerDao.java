package flightbooking.dao;

import flightbooking.entity.Passenger;
import flightbooking.mapper.PassengerMapper;

import java.util.Comparator;
import java.util.List;

public class PassengerDao implements Dao<Passenger> {

    private static final String FILE_PATH = "C:\\Users\\hp\\IdeaProjects\\lesson1\\src\\main\\resources\\database\\passengers.csv";
    private final PassengerMapper passengerMapper;
    private List<Passenger> passengers;

    public PassengerDao() {
        passengerMapper = new PassengerMapper();
        passengers = loadAllData();
    }

    @Override
    public Passenger getById(Long id) {
        return passengers.stream()
                .filter(passenger -> id.equals(passenger.getId()))
                .findFirst().orElse(null);
    }

    @Override
    public List<Passenger> getAll() {
        passengers = loadAllData();
        return passengers;
    }

    @Override
    public void delete(Passenger passenger) {
        passengers.remove(passenger);
        writeDataToFile(passengers, FILE_PATH);
    }

    @Override
    public Passenger add(Passenger passenger) {
        Long lastId = getAll().stream().max(Comparator.comparing(Passenger::getId)).orElseThrow().getId();
        passenger.setId(lastId + 1);
        passengers.add(passenger);
        writeDataToFile(passengers, FILE_PATH);
        return passenger;
    }

    private List<Passenger> loadAllData() {
        return passengerMapper.mapToEntity(readDataFromFile(FILE_PATH));
    }

}
