package flightbooking.dao;

import flightbooking.entity.Booking;
import flightbooking.mapper.BookingMapper;

import java.util.Comparator;
import java.util.List;

public class BookingDao implements Dao<Booking> {

    private static final String FILE_PATH = "C:\\Users\\hp\\IdeaProjects\\lesson1\\src\\main\\resources\\database\\bookings.csv";
    private final BookingMapper bookingMapper;
    private final FlightDao flightDao;
    private final PassengerDao passengerDao;
    private List<Booking> bookings;

    public BookingDao(FlightDao flightDao, PassengerDao passengerDao) {
        this.flightDao = flightDao;
        this.passengerDao = passengerDao;
        bookingMapper = new BookingMapper();
        bookings = loadAllData();
    }

    @Override
    public Booking getById(Long id) {
        return bookings.stream()
                .filter(booking -> id.equals(booking.getId()))
                .findFirst().orElse(null);
    }

    @Override
    public List<Booking> getAll() {
        bookings = loadAllData();
        return bookings;
    }

    @Override
    public void delete(Booking chosenBook) {
        bookings.remove(chosenBook);
        writeDataToFile(bookings, FILE_PATH);
    }

    @Override
    public Booking add(Booking booking) {
        Long lastId = getAll().stream().max(Comparator.comparing(Booking::getId)).orElseThrow().getId();
        booking.setId(lastId + 1);
        bookings.add(booking);
        writeDataToFile(bookings, FILE_PATH);
        return booking;
    }

    private List<Booking> loadAllData() {
        var rawList = readDataFromFile(FILE_PATH);
        return bookingMapper.mapToEntity(rawList, flightDao.getAll(), passengerDao.getAll());
    }

}
