package model;

import java.util.ArrayList;
import java.util.List;

public class Hotel {

    private String name;
    private List<Room> rooms;

    public Hotel(String name) {
        this.name = name;
        this.rooms = new ArrayList<>();
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Room> searchAvailableRooms() {
        List<Room> availableRooms = new ArrayList<>();

        for (Room room : rooms) {
            if (!room.isReserved()) {
                availableRooms.add(room);
            }
        }

        return availableRooms;
    }

    @Override
    public String toString() {
        return "Hotel: " + name + " | Total Rooms: " + rooms.size();
    }
}
