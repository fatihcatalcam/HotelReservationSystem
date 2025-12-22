import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import model.*;

import java.util.List;

public class HotelTest {

    @Test
    void testAddRoomAndGetRooms() {
        Hotel hotel = new Hotel("Test Hotel");

        Room room1 = new StandardRoom(101, 800, 2);
        Room room2 = new DeluxeRoom(102, 1500, 3);

        hotel.addRoom(room1);
        hotel.addRoom(room2);

        assertEquals(2, hotel.getRooms().size());
    }

    @Test
    void testSearchAvailableRooms() {
        Hotel hotel = new Hotel("Test Hotel");

        Room room1 = new StandardRoom(101, 800, 2);
        Room room2 = new DeluxeRoom(102, 1500, 3);

        room2.setReserved(true);

        hotel.addRoom(room1);
        hotel.addRoom(room2);

        List<Room> availableRooms = hotel.searchAvailableRooms();

        assertEquals(1, availableRooms.size());
        assertEquals(101, availableRooms.get(0).getRoomNumber());
    }
}
