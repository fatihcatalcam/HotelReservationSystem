package service;

import model.Room;
import model.Customer;
import reservation.Reservation;

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
}
