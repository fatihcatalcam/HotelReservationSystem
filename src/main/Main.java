package main;

import model.*;
import reservation.Reservation;
import service.ReservationManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        Hotel hotel = new Hotel("GPT Hotel");
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

            int choice = input.nextInt();
            input.nextLine(); // buffer temizliği

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
                    int roomNumber = input.nextInt();
                    input.nextLine();

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
                    System.out.print("Check-in date (YYYY-MM-DD): ");
                    LocalDate checkIn = LocalDate.parse(input.nextLine());

                    System.out.print("Check-out date (YYYY-MM-DD): ");
                    LocalDate checkOut = LocalDate.parse(input.nextLine());

                    Reservation reservation = manager.bookRoom(customer, selectedRoom, checkIn, checkOut);

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
