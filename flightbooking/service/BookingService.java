package flightbooking.service;

import flightbooking.dao.BookingDao;
import flightbooking.dao.FlightDao;
import flightbooking.dao.PassengerDao;
import flightbooking.entity.Booking;
import flightbooking.entity.Passenger;

import java.util.List;
import java.util.stream.Collectors;

public class BookingService {

    private final BookingDao bookingDao;
    private final PassengerDao passengerDao;
    private final FlightDao flightDao;

    public BookingService(BookingDao bookingDao, PassengerDao passengerDao, FlightDao flightDao) {
        this.bookingDao = bookingDao;
        this.passengerDao = passengerDao;
        this.flightDao = flightDao;
    }

    public void bookFlight(String bookerName, Long flightId, List<Passenger> passengers) {
        var flights = List.of(flightDao.getById(flightId));
        var newPassengers = passengers.stream()
                .map(passengerDao::add)
                .collect(Collectors.toList());

        Booking booking = new Booking();
        booking.setBookerName(bookerName);
        booking.setFlights(flights);
        booking.setPassengers(newPassengers);
        if (passengers.size() < flights.get(0).getSeats()) {
            bookingDao.add(booking);
        } else {
            throw new RuntimeException("There is no enough seat");
        }
    }

    public void cancelBook(Long bookId) {
        var chosenBook = bookingDao.getAll().stream()
                .filter(booking -> bookId.equals(booking.getId()))
                .findAny().orElse(null);
        chosenBook.getPassengers()
                .forEach(passengerDao::delete);
        bookingDao.delete(chosenBook);
    }

    public List<Booking> getUsersBookings(String fullName) {
        return bookingDao.getAll().stream()
                .filter(b -> b.getBookerName().equals(fullName))
                .collect(Collectors.toList());
    }
}
