import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import model.*;
import reservation.Reservation;

import java.time.LocalDate;

public class ReservationTest {

    @Test
    void testReservationTotalPrice() {
        Customer customer = new Customer("Ali", "ali@mail.com", "C1");
        Room room = new StandardRoom(101, 1000, 2);

        LocalDate checkIn = LocalDate.of(2025, 1, 1);
        LocalDate checkOut = LocalDate.of(2025, 1, 4);

        Reservation reservation = new Reservation(customer, room, checkIn, checkOut);
        assertEquals(3000, reservation.getTotalPrice());
    }
}
