package flightbooking.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
public class Flight {

    private Long id;
    private String destination;
    private LocalDateTime dateTime;
    private int seats;

    @Override
    public String toString() {
        StringBuilder dataBuilder = new StringBuilder();
        appendFieldValue(dataBuilder, id.toString());
        appendFieldValue(dataBuilder, destination);
        appendFieldValue(dataBuilder, dateTime.toString());
        appendFieldValue(dataBuilder, String.valueOf(seats));

        return dataBuilder.toString();
    }

    private void appendFieldValue(StringBuilder dataBuilder, String fieldValue) {
        dataBuilder.append(Objects.requireNonNullElse(fieldValue, "")).append(",");
    }
}
