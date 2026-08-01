package MiniBank;

public class Customer {
    
    // Private fields
    private String name;
    private String email;
    private String mobile;
    private final String customerId;

    // Static counter
    private static long customerCounter = 100;

    // Static method to generate Customer ID
    private static String generateCustomerId() {
        customerCounter++;
        return "CUST" + customerCounter;
    }

    // Constructor
    public Customer(String name, String email, String mobile) {
        this.customerId = generateCustomerId();
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }

    // Getter methods
    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }
}
