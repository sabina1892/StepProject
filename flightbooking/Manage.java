package flightbooking;


import flightbooking.dao.BookingDao;
import flightbooking.dao.FlightDao;
import flightbooking.dao.PassengerDao;
import flightbooking.entity.Passenger;
import flightbooking.service.BookingService;
import flightbooking.service.FlightService;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.parse;


public class Manage {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

    private final FlightService flightService;
    private final BookingService bookingService;

    public Manage() {
        var flightDaoNew = new FlightDao();
        var passengerDaoNew = new PassengerDao();
        this.flightService = new FlightService(flightDaoNew);
        this.bookingService = new BookingService(new BookingDao(flightDaoNew, passengerDaoNew), passengerDaoNew, flightDaoNew);
    }

    public boolean startingApp() {

        System.out.println("WELCOME TO INFORMATION SYSTEM");
        System.out.println("1\t ONLINE-BOARD");
        System.out.println("2\t SHOW THE FLIGHT INFO");
        System.out.println("3\t SEARCH AND BOOK A FLIGHT");
        System.out.println("4\t CANCEL THE BOOKING");
        System.out.println("5\t MY FLIGHTS");
        System.out.println("6\t EXIT");

        System.out.print("Enter command to run: ");
        Scanner in = new Scanner(System.in);
        String command = in.nextLine();
        if (command.equalsIgnoreCase("6")) {
            System.out.println("Application closed...");
            return false;
        } else {
            switch (command) {
                case "1" -> {
                    flightService.getAll().forEach(System.out::println);
                    System.out.println("All flights showed!");
                }
                case "2" -> {
                    System.out.println("Enter the flight's id");
                    Scanner in1 = new Scanner(System.in);
                    Long id = in.nextLong();
                    System.out.println("\n");
                    flightService.getAll().stream().filter(f -> f.getId().equals(id)).collect(Collectors.toList())
                            .forEach(System.out::println);
                    System.out.println("Chosen flight displayed successfully!");
                }
                case "3" -> {
                    System.out.println("Enter the destination: ");
                    String destination = in.nextLine();
                    System.out.println("Enter the date: ");
                    String dateTime = in.nextLine();
                    System.out.println("Enter the number of people: ");
                    int nop = in.nextInt();
                    System.out.println("\n");
                    searchFlights(destination, dateTime, nop);
                    System.out.println("Search result showed!");

                    System.out.println("Enter the fullName:");
                    in.nextLine();  // Consume newline left-over
                    String fullName = in.nextLine();
                    System.out.println("Enter the flightId: ");
                    Long flightId = in.nextLong();
                    List<Passenger> passengers = new ArrayList<>();
                    System.out.println("Enter the all name and surnames: ");
                    in.nextLine();  // Consume newline left-over
                    while (in.hasNext()) {
                        var pas = new Passenger();
                        String[] nameAndSurname = in.nextLine().split(" ");
                        pas.setName(nameAndSurname[0]);
                        pas.setSurname(nameAndSurname[1]);
                        passengers.add(pas);
                        if (passengers.size() == nop) {
                            break;
                        }
                    }
                    book(fullName, flightId, passengers);
                    System.out.println("Successfully booked");

                }
                case "4" -> {
                    System.out.println("Enter the book id: ");
                    Long bookId = in.nextLong();
                    bookingService.cancelBook(bookId);
                    System.out.println("Successfully canceled book");
                }
                case "5" -> {
                    System.out.println("Enter your full name pls: ");
                    String fullName = in.nextLine();
                    bookingService.getUsersBookings(fullName).forEach(System.out::println);
                }
                default -> System.out.println("Command not exist, try again please!");
            }
        }
        return true;
    }

    private void searchFlights(String destination, String dateTime, int nop) {
        flightService.search(destination,
                parse(dateTime, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)),
                nop).forEach(System.out::println);
    }

    private void book(String bookername, Long flightId, List<Passenger> passengerList) {
        bookingService.bookFlight(bookername, flightId, passengerList);
    }
}