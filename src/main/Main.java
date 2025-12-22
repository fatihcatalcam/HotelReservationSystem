package main;

import model.*;
import reservation.Reservation;
import service.ReservationManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import payment.Payment;
import payment.CashPayment;
import payment.CreditCardPayment;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        Hotel hotel = new Hotel("Hotel");
        ReservationManager manager = new ReservationManager();

        List<Customer> customers = new ArrayList<>();

        // Başlangıç odaları
        hotel.addRoom(new DeluxeRoom(101, 1500, 2));
        hotel.addRoom(new StandardRoom(102, 800, 2));
        hotel.addRoom(new StandardRoom(103, 900, 3));
        hotel.addRoom(new DeluxeRoom(104, 2000, 4));

        // CSV'den rezervasyonları yükle (odalar eklendikten sonra!)
        manager.loadReservationsFromCSV(hotel, customers);
        System.out.println("CSV reservations loaded.");

        while (true) {
            System.out.println("========== HOTEL RESERVATION SYSTEM ==========");
            System.out.println("1. List available rooms");
            System.out.println("2. Make a reservation");
            System.out.println("3. Cancel a reservation");
            System.out.println("4. List all reservations");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.\n");
                continue;
            }

            switch (choice) {

                case 1:
                    System.out.println("\nAvailable Rooms:");
                    for (Room room : hotel.searchAvailableRooms()) {
                        System.out.println(room);
                    }
                    System.out.println();
                    break;

                case 2:
                    System.out.println("\n--- Make Reservation ---");

                    System.out.print("Customer Name: ");
                    String name = input.nextLine();

                    System.out.print("Email: ");
                    String email = input.nextLine();

                    System.out.print("Customer ID: ");
                    String id = input.nextLine();

                    Customer customer = new Customer(name, email, id);
                    customers.add(customer);

                    List<Room> freeRooms = hotel.searchAvailableRooms();
                    if (freeRooms.isEmpty()) {
                        System.out.println("No rooms available!\n");
                        break;
                    }

                    for (Room r : freeRooms) {
                        System.out.println(r.getRoomNumber() + " - " + r);
                    }

                    System.out.print("Enter room number: ");
                    int roomNumber;
                    try {
                        roomNumber = Integer.parseInt(input.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid room number!\n");
                        break;
                    }

                    Room selectedRoom = null;
                    for (Room r : freeRooms) {
                        if (r.getRoomNumber() == roomNumber) {
                            selectedRoom = r;
                            break;
                        }
                    }

                    if (selectedRoom == null) {
                        System.out.println("Invalid room number!\n");
                        break;
                    }

                    LocalDate checkIn;
                    LocalDate checkOut;
                    try {
                        System.out.print("Check-in date (YYYY-MM-DD): ");
                        checkIn = LocalDate.parse(input.nextLine());

                        System.out.print("Check-out date (YYYY-MM-DD): ");
                        checkOut = LocalDate.parse(input.nextLine());

                        if (!checkOut.isAfter(checkIn)) {
                            System.out.println("Check-out must be after check-in!\n");
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid date format!\n");
                        break;
                    }

                    Reservation reservation =
                            manager.bookRoom(customer, selectedRoom, checkIn, checkOut);

                    if (reservation == null) break;

                    System.out.println(reservation);

                    System.out.println("\nSelect payment method:");
                    System.out.println("1. Cash");
                    System.out.println("2. Credit Card");
                    System.out.print("Choice: ");

                    int payChoice;
                    try {
                        payChoice = Integer.parseInt(input.nextLine());
                    } catch (Exception e) {
                        payChoice = 1;
                    }

                    Payment payment =
                            (payChoice == 2) ? new CreditCardPayment() : new CashPayment();

                    payment.pay(reservation.getTotalPrice());
                    System.out.println("Payment completed.\n");
                    break;

                case 3:
                    System.out.println("\n--- Cancel Reservation ---");

                    List<Reservation> all = manager.getReservations();
                    if (all.isEmpty()) {
                        System.out.println("No reservations found.\n");
                        break;
                    }

                    for (int i = 0; i < all.size(); i++) {
                        System.out.println((i + 1) + ". " + all.get(i));
                    }

                    System.out.print("Choose reservation number: ");
                    int index;
                    try {
                        index = Integer.parseInt(input.nextLine()) - 1;
                    } catch (Exception e) {
                        System.out.println("Invalid input!\n");
                        break;
                    }

                    if (index < 0 || index >= all.size()) {
                        System.out.println("Invalid selection!\n");
                        break;
                    }

                    manager.cancelReservation(all.get(index));
                    System.out.println();
                    break;

                case 4:
                    System.out.println("\n--- All Reservations ---");
                    manager.listReservations();
                    System.out.println();
                    break;

                case 5:
                    hotel.writeRoomsToCSV();
                    System.out.println("Rooms saved. Exiting...");
                    input.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option!\n");
            }
        }
    }
}
