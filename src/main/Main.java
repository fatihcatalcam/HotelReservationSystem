package main;

import model.*;
import service.ReservationManager;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Hotel hotel = new Hotel("Hotel");
        ReservationManager manager = new ReservationManager();
        List<Customer> customers = new ArrayList<>();

        initializeRooms(hotel);

        manager.loadCustomersFromCSV(customers);
        manager.loadReservationsFromCSV(hotel, customers);

        ConsoleApp app = new ConsoleApp(hotel, manager, customers);
        app.start();
    }

    private static void initializeRooms(Hotel hotel) {
        hotel.addRoom(new DeluxeRoom(101, 1500, 2));
        hotel.addRoom(new StandardRoom(102, 800, 2));
        hotel.addRoom(new StandardRoom(103, 900, 3));
        hotel.addRoom(new DeluxeRoom(104, 2000, 4));
    }
}
