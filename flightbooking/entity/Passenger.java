package flightbooking.entity;

import lombok.Data;

import java.util.Objects;

@Data
public class Passenger {

    private Long id;
    private String name;
    private String surname;

    @Override
    public String toString() {
        StringBuilder dataBuilder = new StringBuilder();
        appendFieldValue(dataBuilder, id.toString());
        appendFieldValue(dataBuilder, name);
        appendFieldValue(dataBuilder, surname);

        return dataBuilder.toString();
    }

    private void appendFieldValue(StringBuilder dataBuilder, String fieldValue) {
        dataBuilder.append(Objects.requireNonNullElse(fieldValue, "")).append(",");
    }
}
