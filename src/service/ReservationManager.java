package service;

import model.Room;
import model.Customer;
import reservation.Reservation;
import model.Hotel;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationManager {

    private List<Reservation> reservations;
    
    
    
    public ReservationManager() {
        this.reservations = new ArrayList<>();
    }

    public Reservation bookRoom(Customer customer, Room room, LocalDate checkIn, LocalDate checkOut) {

    	if (room.isReserved()) {
    	    System.out.println("❌ Reservation failed: Room #" + room.getRoomNumber() + " is already booked.");
    	    return null;
    	}


        Reservation reservation = new Reservation(customer, room, checkIn, checkOut);
        reservations.add(reservation);
        room.setReserved(true);
        
        writeReservationToCSV(reservation);

        
        System.out.println("✅ Reservation created successfully for " + customer.getName());
        return reservation;
    }

    public boolean cancelReservation(Reservation reservation) {

        if (!reservations.contains(reservation)) {
        	System.out.println("❌ Error: Reservation could not be found.");
            return false;
        }

        reservation.getRoom().setReserved(false);
        reservations.remove(reservation);

        System.out.println("🗑️ Reservation has been cancelled.");
        return true;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void listReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            for (Reservation r : reservations) {
                System.out.println(r);
                System.out.println("-------------------");
            }
        }
    }
    public void writeReservationToCSV(Reservation reservation) {
        String filePath = "reservations.csv";

        String line = reservation.getCustomer().getCustomerId() + "," +
                reservation.getRoom().getRoomNumber() + "," +
                reservation.getCheckInDate() + "," +
                reservation.getCheckOutDate() + "," +
                reservation.getTotalPrice();

        utils.CSVUtil.appendLine(filePath, line);
    }
    public void loadReservationsFromCSV(Hotel hotel, List<Customer> customers) {
        String filePath = "reservations.csv";

        List<String[]> rows = utils.CSVUtil.readAll(filePath);

        for (String[] row : rows) {
            if (row.length < 5) continue;

            String customerId = row[0];
            int roomNumber = Integer.parseInt(row[1]);
            LocalDate checkIn = LocalDate.parse(row[2]);
            LocalDate checkOut = LocalDate.parse(row[3]);

            Customer customer = findCustomerById(customers, customerId);
            Room room = findRoomByNumber(hotel, roomNumber);

            // ⬇️ KRİTİK DÜZELTME BURADA
            if (room != null) {

                if (customer == null) {
                    customer = new Customer(
                            "Unknown",
                            "unknown@mail.com",
                            customerId
                    );
                    customers.add(customer);
                }

                Reservation reservation =
                        new Reservation(customer, room, checkIn, checkOut);

                reservations.add(reservation);
                room.setReserved(true);
            }
        }
    }

    public void writeCustomerToCSV(Customer customer) {
        String filePath = "customers.csv";

        String line =
                customer.getCustomerId() + "," +
                customer.getName() + "," +
                customer.getEmail();

        utils.CSVUtil.appendLine(filePath, line);
    }

    public void loadCustomersFromCSV(List<Customer> customers) {
        String filePath = "customers.csv";

        List<String[]> rows = utils.CSVUtil.readAll(filePath);

        for (String[] row : rows) {
            if (row.length < 3) continue;

            String id = row[0];
            String name = row[1];
            String email = row[2];

            // Aynı customer tekrar eklenmesin
            boolean exists = false;
            for (Customer c : customers) {
                if (c.getCustomerId().equals(id)) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                customers.add(new Customer(name, email, id));
            }
        }
    }

    private Customer findCustomerById(List<Customer> customers, String id) {
        for (Customer c : customers) {
            if (c.getCustomerId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    private Room findRoomByNumber(Hotel hotel, int roomNumber) {
        for (Room r : hotel.getRooms()) {
            if (r.getRoomNumber() == roomNumber) {
                return r;
            }
        }
        return null;
    }

}
