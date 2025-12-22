import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import model.StandardRoom;
import model.DeluxeRoom;

public class RoomTest {

    @Test
    void testStandardRoomBasePrice() {
        StandardRoom room = new StandardRoom(101, 1000, 2);
        assertEquals(1000, room.getBasePrice());
    }

    @Test
    void testDeluxeRoomCapacity() {
        DeluxeRoom room = new DeluxeRoom(201, 2000, 4);
        assertEquals(4, room.getCapacity());
    }

    @Test
    void testRoomReservationStatus() {
        StandardRoom room = new StandardRoom(102, 800, 2);
        assertFalse(room.isReserved());
        room.setReserved(true);
        assertTrue(room.isReserved());
    }
}
