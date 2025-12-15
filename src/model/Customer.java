package model;

public class Customer {

    private String name;
    private String email;
    private String customerId;

    public Customer(String name, String email, String customerId) {
        this.name = name;
        this.email = email;
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCustomerId() {
        return customerId;
    }

    @Override
    public String toString() {
        return "Customer: " + name + " | Email: " + email + " | ID: " + customerId;
    }
}
