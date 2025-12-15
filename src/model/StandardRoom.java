package model;

public class StandardRoom extends Room {

    public StandardRoom(int roomNumber, double basePrice, int capacity) {
        super(roomNumber, basePrice, capacity);
    }

    @Override
    public double calculatePrice() {
        // Standart odalar direkt basePrice
        return basePrice;
    }

    @Override
    public String toString() {
        return "Standard Room #" + roomNumber + " | Capacity: " + capacity + " | Price: " + calculatePrice();
    }
}
