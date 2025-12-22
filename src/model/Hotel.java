package model;

import java.util.ArrayList;
import java.util.List;

import utils.CSVUtil;

public class Hotel {

    private String name;
    private List<Room> rooms;

    public Hotel(String name) {
        this.name = name;
        this.rooms = new ArrayList<>();
    }

    /* ---------------- BASIC METHODS ---------------- */

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

    public void listAllRooms() {
        if (rooms.isEmpty()) {
            System.out.println("No rooms found.");
            return;
        }

        for (Room room : rooms) {
            System.out.println(room);
        }
    }

    /* ---------------- CSV WRITE ---------------- */

    public void writeRoomsToCSV() {
        String filePath = "rooms.csv";
        List<String> lines = new ArrayList<>();

        for (Room room : rooms) {
            String type = room instanceof DeluxeRoom ? "DELUXE" : "STANDARD";

            String line =
                    room.getRoomNumber() + "," +
                    room.getBasePrice() + "," +
                    room.getCapacity() + "," +
                    type + "," +
                    room.isReserved();

            lines.add(line);
        }

        CSVUtil.writeAll(filePath, lines);
    }

    /* ---------------- CSV LOAD ---------------- */

    public void loadRoomsFromCSV() {
        String filePath = "rooms.csv";
        List<String[]> rows = CSVUtil.readAll(filePath);

        for (String[] row : rows) {
            if (row.length < 5) continue;

            int roomNumber = Integer.parseInt(row[0]);
            double price = Double.parseDouble(row[1]);
            int capacity = Integer.parseInt(row[2]);
            String type = row[3];
            boolean isReserved = Boolean.parseBoolean(row[4]);

            Room room;

            if (type.equalsIgnoreCase("DELUXE")) {
                room = new DeluxeRoom(roomNumber, price, capacity);
            } else {
                room = new StandardRoom(roomNumber, price, capacity);
            }

            room.setReserved(isReserved);
            rooms.add(room);
        }
    }

    @Override
    public String toString() {
        return "Hotel: " + name + " | Total Rooms: " + rooms.size();
    }
}
