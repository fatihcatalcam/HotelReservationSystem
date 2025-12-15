package reservation;

import model.Customer;
import model.Room;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reservation {

    private Customer customer;
    private Room room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private double totalPrice;

    public Reservation(Customer customer, Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;

        this.totalPrice = calculateTotalPrice();
    }

    private double calculateTotalPrice() {
        long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        if (nights < 1) nights = 1; // Minimum 1 gece

        return room.calculatePrice() * nights;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "Reservation for " + customer.getName() +
                "\nRoom: " + room.toString() +
                "\nCheck-in: " + checkInDate +
                "\nCheck-out: " + checkOutDate +
                "\nTotal Price: " + totalPrice + "₺";
    }
}
