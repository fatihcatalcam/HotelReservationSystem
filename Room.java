package model;

public abstract class Room {

    protected int roomNumber;
    protected double basePrice;
    protected int capacity;
    protected boolean isReserved;

    public Room(int roomNumber, double basePrice, int capacity) {
        this.roomNumber = roomNumber;
        this.basePrice = basePrice;
        this.capacity = capacity;
        this.isReserved = false;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        this.isReserved = reserved;
    }

    // Polymorphism için override edilecek:
    public abstract double calculatePrice();

    @Override
    public String toString() {
        return "Room #" + roomNumber + " | Capacity: " + capacity + " | Base Price: " + basePrice;
    }
}
