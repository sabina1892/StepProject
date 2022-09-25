package flightbooking.entity;

import lombok.Data;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
public class Booking {

    private Long id;
    private String bookerName;
    private List<Passenger> passengers;
    private List<Flight> flights;

    @Override
    public String toString() {
        StringBuilder dataBuilder = new StringBuilder();
        appendFieldValue(dataBuilder, id.toString());
        appendFieldValue(dataBuilder, bookerName);
        appendFieldValue(dataBuilder, passengers.stream()
                .map(p -> p.getId().toString())
                .collect(Collectors.joining("-")));
        appendFieldValue(dataBuilder, flights.stream()
                .map(f -> f.getId().toString())
                .collect(Collectors.joining("-")));

        return dataBuilder.toString();
    }

    private void appendFieldValue(StringBuilder dataBuilder, String fieldValue) {
        dataBuilder.append(Objects.requireNonNullElse(fieldValue, "")).append(",");
    }

}
