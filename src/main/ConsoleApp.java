package main;

import model.*;
import reservation.Reservation;
import service.ReservationManager;
import payment.*;

import java.time.LocalDate;
import java.util.*;

public class ConsoleApp {

    private Scanner input = new Scanner(System.in);
    private Hotel hotel;
    private ReservationManager manager;
    private List<Customer> customers;

    public ConsoleApp(Hotel hotel, ReservationManager manager, List<Customer> customers) {
        this.hotel = hotel;
        this.manager = manager;
        this.customers = customers;
    }

    public void start() {
        while (true) {
            printMenu();
            int choice = readInt();

            switch (choice) {
                case 1 -> listAvailableRooms();
                case 2 -> makeReservation();
                case 3 -> cancelReservation();
                case 4 -> manager.listReservations();
                case 5 -> exit();
                default -> System.out.println("Invalid option!\n");
            }
        }
    }

    // ---------------- MENU ----------------

    private void printMenu() {
        System.out.println("========== HOTEL RESERVATION SYSTEM ==========");
        System.out.println("1. List available rooms");
        System.out.println("2. Make a reservation");
        System.out.println("3. Cancel a reservation");
        System.out.println("4. List all reservations");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }

    // ---------------- FEATURES ----------------

    private void listAvailableRooms() {
        hotel.searchAvailableRooms().forEach(System.out::println);
        System.out.println();
    }

    private void makeReservation() {

        Customer customer = getOrCreateCustomer();

        List<Room> freeRooms = hotel.searchAvailableRooms();
        if (freeRooms.isEmpty()) {
            System.out.println("No rooms available!\n");
            return;
        }

        freeRooms.forEach(r -> System.out.println(r.getRoomNumber() + " - " + r));
        System.out.print("Enter room number: ");
        int roomNumber = readInt();

        Room selectedRoom = freeRooms.stream()
                .filter(r -> r.getRoomNumber() == roomNumber)
                .findFirst()
                .orElse(null);

        if (selectedRoom == null) {
            System.out.println("Invalid room number!\n");
            return;
        }

        LocalDate checkIn = readDate("Check-in date (YYYY-MM-DD): ");
        LocalDate checkOut = readDate("Check-out date (YYYY-MM-DD): ");

        if (!checkOut.isAfter(checkIn)) {
            System.out.println("Check-out must be after check-in!\n");
            return;
        }

        Reservation reservation =
                manager.bookRoom(customer, selectedRoom, checkIn, checkOut);

        if (reservation == null) return;

        processPayment(reservation);
    }

    private void cancelReservation() {
        List<Reservation> all = manager.getReservations();

        if (all.isEmpty()) {
            System.out.println("No reservations found.\n");
            return;
        }

        for (int i = 0; i < all.size(); i++) {
            System.out.println((i + 1) + ". " + all.get(i));
        }

        System.out.print("Choose reservation number: ");
        int index = readInt() - 1;

        if (index < 0 || index >= all.size()) {
            System.out.println("Invalid selection!\n");
            return;
        }

        manager.cancelReservation(all.get(index));
    }

    private void processPayment(Reservation reservation) {
        System.out.println("\nSelect payment method:");
        System.out.println("1. Cash");
        System.out.println("2. Credit Card");

        Payment payment =
                (readInt() == 2) ? new CreditCardPayment() : new CashPayment();

        payment.pay(reservation.getTotalPrice());
        System.out.println("Payment completed.\n");
    }

    // ---------------- HELPERS ----------------

    private Customer getOrCreateCustomer() {

        System.out.print("Customer Name: ");
        String name = input.nextLine();

        System.out.print("Email: ");
        String email = input.nextLine();

        System.out.print("Customer ID: ");
        String id = input.nextLine();

        for (Customer c : customers) {
            if (c.getCustomerId().equals(id)) {
                return c;
            }
        }

        Customer customer = new Customer(name, email, id);
        customers.add(customer);
        manager.writeCustomerToCSV(customer);
        return customer;
    }

    private int readInt() {
        try {
            return Integer.parseInt(input.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }

    private LocalDate readDate(String msg) {
        System.out.print(msg);
        return LocalDate.parse(input.nextLine());
    }

    private void exit() {
        hotel.writeRoomsToCSV();
        input.close();
        System.out.println("Rooms saved. Exiting...");
        System.exit(0);
    }
}
