package flightbooking.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface Dao<T> {

    String COMMA_DELIMITER = ",";

    T getById(Long id);

    List<T> getAll();

    void delete(T object);

    T add(T type);

    default List<List<String>> readDataFromFile(String filePath) {
        List<List<String>> records = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                records.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return records;
    }

    default void writeDataToFile(List<T> sampleList, String filePath) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(filePath);
            for (T sample : sampleList) {
                writer.println(sample.toString());
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
