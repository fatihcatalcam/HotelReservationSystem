package model;

public class DeluxeRoom extends Room {

    public DeluxeRoom(int roomNumber, double basePrice, int capacity) {
        super(roomNumber, basePrice, capacity);
    }

    @Override
    public double calculatePrice() {
        // Deluxe odalarda %30 ekstra ücret
        return basePrice * 1.30;
    }

    @Override
    public String toString() {
        return "Deluxe Room #" + roomNumber + " | Capacity: " + capacity + " | Price: " + calculatePrice();
    }
}
