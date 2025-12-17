package main;

import model.*;
import reservation.Reservation;
import service.ReservationManager;

import java.time.LocalDate;
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

        // Başlangıç için örnek odalar
        hotel.addRoom(new DeluxeRoom(101, 1500, 2));
        hotel.addRoom(new StandardRoom(102, 800, 2));
        hotel.addRoom(new StandardRoom(103, 900, 3));
        hotel.addRoom(new DeluxeRoom(104, 2000, 4));

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
                    List<Room> availableRooms = hotel.searchAvailableRooms();
                    for (Room room : availableRooms) {
                        System.out.println(room);
                    }
                    System.out.println();
                    break;

                case 2:
                    System.out.println("\n--- Make Reservation ---");

                    // Customer info
                    System.out.print("Customer Name: ");
                    String name = input.nextLine();

                    System.out.print("Email: ");
                    String email = input.nextLine();

                    System.out.print("Customer ID: ");
                    String id = input.nextLine();

                    Customer customer = new Customer(name, email, id);

                    // Select room
                    System.out.println("\nAvailable Rooms:");
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

                    // Dates
                    LocalDate checkIn;
                    LocalDate checkOut;

                    try {
                        System.out.print("Check-in date (YYYY-MM-DD): ");
                        checkIn = LocalDate.parse(input.nextLine());

                        System.out.print("Check-out date (YYYY-MM-DD): ");
                        checkOut = LocalDate.parse(input.nextLine());

                        if (!checkOut.isAfter(checkIn)) {
                            System.out.println("ERROR: Check-out date must be after check-in date!\n");
                            break;
                        }

                    } catch (Exception e) {
                        System.out.println("Invalid date format! Please use YYYY-MM-DD.\n");
                        break;
                    }


                    Reservation reservation = manager.bookRoom(customer, selectedRoom, checkIn, checkOut);
                    
                    if (reservation != null) {
                        System.out.println("Reservation Successful!");
                        System.out.println(reservation);

                        System.out.println("\nSelect payment method:");
                        System.out.println("1. Cash");
                        System.out.println("2. Credit Card");
                        System.out.print("Choice: ");
                        int payChoice;

                        try {
                            payChoice = Integer.parseInt(input.nextLine());
                        } catch (Exception e) {
                            System.out.println("Invalid selection! Defaulting to CASH.");
                            payChoice = 1;
                        }


                        Payment payment = null;

                        if (payChoice == 1) {
                            payment = new CashPayment();
                        } else if (payChoice == 2) {
                            payment = new CreditCardPayment();
                        } else {
                            System.out.println("Invalid payment option! Defaulting to CASH.");
                            payment = new CashPayment();
                        }

                        // Ödeme işlemi burada yapılır
                        boolean success = payment.pay(reservation.getTotalPrice());

                        if (success) {
                            System.out.println("Payment completed successfully.");
                        } else {
                            System.out.println("Payment failed.");
                        }
                    }

                    

                    if (reservation != null) {
                        System.out.println("Reservation Successful!");
                        System.out.println(reservation);
                    }
                    System.out.println();
                    break;

                case 3:
                    System.out.println("\n--- Cancel Reservation ---");

                    List<Reservation> allReservations = manager.getReservations();

                    if (allReservations.isEmpty()) {
                        System.out.println("No reservations to cancel!\n");
                        break;
                    }

                    for (int i = 0; i < allReservations.size(); i++) {
                        System.out.println((i + 1) + ". " + allReservations.get(i));
                    }

                    System.out.print("Choose reservation number to cancel: ");
                    int resIndex = input.nextInt() - 1;

                    if (resIndex < 0 || resIndex >= allReservations.size()) {
                        System.out.println("Invalid choice!\n");
                        break;
                    }

                    manager.cancelReservation(allReservations.get(resIndex));
                    System.out.println();
                    break;

                case 4:
                    System.out.println("\n--- All Reservations ---");
                    manager.listReservations();
                    System.out.println();
                    break;

                case 5:
                    System.out.println("Exit...");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option!\n");
            }
        }
    }
}
