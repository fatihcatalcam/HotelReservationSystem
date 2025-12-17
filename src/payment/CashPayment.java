package payment;

public class CashPayment implements Payment {

    @Override
    public boolean pay(double amount) {
        System.out.println("Paid " + amount + "₺ in CASH.");
        return true;
    }
}
