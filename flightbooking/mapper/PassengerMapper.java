package flightbooking.mapper;

import flightbooking.entity.Passenger;

import java.util.ArrayList;
import java.util.List;

public class PassengerMapper {

    public List<Passenger> mapToEntity(List<List<String>> rows) {
        List<Passenger> passengers = new ArrayList<>();

        rows
                .forEach(row -> {
                    Passenger passenger = new Passenger();
                    passenger.setId(Long.parseLong(row.get(0)));
                    passenger.setName(row.get(1));
                    passenger.setSurname(row.get(2));
                    passengers.add(passenger);
                });

        return passengers;
    }

}
