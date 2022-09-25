package flightbooking.mapper;


import flightbooking.entity.Flight;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.parse;

public class FlightMapper {

    private static final String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm";

    public List<Flight> mapToEntity(List<List<String>> rows) {
        List<Flight> flights = new ArrayList<>();

        rows
                .forEach(row -> {
                    Flight flight = new Flight();
                    flight.setId(Long.parseLong(row.get(0)));
                    flight.setDestination(row.get(1));
                    flight.setDateTime(parse(row.get(2), DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
                    flight.setSeats(Integer.parseInt(row.get(3)));
                    flights.add(flight);
                });

        return flights;
    }

}
