package payment;

public class CreditCardPayment implements Payment {

    @Override
    public boolean pay(double amount) {
        System.out.println("Paid " + amount + "₺ by CREDIT CARD.");
        return true;
    }
}
