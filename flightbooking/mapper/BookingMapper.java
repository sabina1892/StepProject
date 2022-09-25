package flightbooking.mapper;

import flightbooking.entity.Booking;
import flightbooking.entity.Flight;
import flightbooking.entity.Passenger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BookingMapper {

    public List<Booking> mapToEntity(List<List<String>> rows, List<Flight> flights, List<Passenger> passengers) {
        List<Booking> bookings = new ArrayList<>();

        rows
                .forEach(row -> {
                    Booking booking = new Booking();
                    booking.setId(Long.parseLong(row.get(0)));
                    booking.setBookerName(row.get(1));
                    String[] passengerIds = row.get(2).split("-");
                    String[] flightIds = row.get(3).split("-");
                    booking.setPassengers(passengers
                            .stream()
                            .filter(passenger -> Arrays.stream(passengerIds)
                                    .anyMatch(s -> s.equals(String.valueOf(passenger.getId()))))
                            .collect(Collectors.toList()));
                    booking.setFlights(flights
                            .stream()
                            .filter(flight -> Arrays.stream(flightIds)
                                    .anyMatch(s -> s.equals(String.valueOf(flight.getId()))))
                            .collect(Collectors.toList()));
                    bookings.add(booking);
                });
        return bookings;
    }

}