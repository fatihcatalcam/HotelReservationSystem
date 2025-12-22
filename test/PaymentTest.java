import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import payment.CashPayment;
import payment.CreditCardPayment;
import payment.Payment;

public class PaymentTest {

    @Test
    void testCashPayment() {
        Payment payment = new CashPayment();
        assertTrue(payment.pay(1000));
    }

    @Test
    void testCreditCardPayment() {
        Payment payment = new CreditCardPayment();
        assertTrue(payment.pay(2000));
    }
}
