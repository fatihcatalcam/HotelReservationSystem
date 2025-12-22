import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import model.*;
import service.ReservationManager;

import java.time.LocalDate;

public class ReservationManagerTest {

    @Test
    void testBookRoomSuccess() {
        ReservationManager manager = new ReservationManager();
        Customer customer = new Customer("Ayse", "ayse@mail.com", "C2");
        Room room = new StandardRoom(103, 900, 2);

        var reservation = manager.bookRoom(
                customer,
                room,
                LocalDate.now(),
                LocalDate.now().plusDays(2)
        );

        assertNotNull(reservation);
        assertTrue(room.isReserved());
        assertEquals(1, manager.getReservations().size());
    }

    @Test
    void testCancelReservation() {
        ReservationManager manager = new ReservationManager();
        Customer customer = new Customer("Mehmet", "mehmet@mail.com", "C3");
        Room room = new DeluxeRoom(201, 2000, 3);

        var reservation = manager.bookRoom(
                customer,
                room,
                LocalDate.now(),
                LocalDate.now().plusDays(1)
        );

        boolean result = manager.cancelReservation(reservation);

        assertTrue(result);
        assertFalse(room.isReserved());
        assertEquals(0, manager.getReservations().size());
    }
}
