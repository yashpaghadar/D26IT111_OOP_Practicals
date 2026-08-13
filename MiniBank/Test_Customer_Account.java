package MiniBank;

public class Test_Customer_Account {

    public static void main(String[] args) {

        // Create Customers
        Customer c1 = new Customer("Yash", "yash@gmail.com", "9876543210");
        Customer c2 = new Customer("Rahul", "rahul@gmail.com", "9988776655");
        Customer c3 = new Customer("Amit", "amit@gmail.com", "9123456789");

        Customer.Address address = new Customer.Address("123 Main Road", "Anand", "388001");
        Customer c4 = new Customer("Yash", "yash@gmail.com", "9876543210", address);

        System.out.println("========== CUSTOMER DETAILS ==========");
        System.out.println("Customer 1");
        System.out.println("ID     : " + c1.getCustomerId());
        System.out.println("Name   : " + c1.getName());
        System.out.println("Email  : " + c1.getEmail());
        System.out.println("Mobile : " + c1.getMobile());

        System.out.println();
        System.out.println("Customer 2");
        System.out.println("ID     : " + c2.getCustomerId());
        System.out.println("Name   : " + c2.getName());
        System.out.println("Email  : " + c2.getEmail());
        System.out.println("Mobile : " + c2.getMobile());

        System.out.println();
        System.out.println("Customer 3");
        System.out.println("ID     : " + c3.getCustomerId());
        System.out.println("Name   : " + c3.getName());
        System.out.println("Email  : " + c3.getEmail());
        System.out.println("Mobile : " + c3.getMobile());

        System.out.println();
        System.out.println("Customer 4");
        System.out.println("ID     : " + c4.getCustomerId());
        System.out.println("Name   : " + c4.getName());
        System.out.println("Email  : " + c4.getEmail());
        System.out.println("Mobile : " + c4.getMobile());
        System.out.println("Address: " + c4.getAddress());
        System.out.println("\n========== ACCOUNT DETAILS ==========");

        Account[] accounts = new Account[4];
        accounts[0] = new Account(c1.getName(), 5000);
        accounts[1] = new Account(c2.getName());
        accounts[2] = new Account(c3.getName(), 10000);
        accounts[3] = new Account(c4.getName(), 10000);

        System.out.println("Transaction of " + accounts[0].getAccountNumber() + " Account.");
        long amt = 2000;
        accounts[0].Deposit(amt);
        System.out.println("Deposit " + amt + ".");
        System.out.println("Balance : " + accounts[0].getBalance());

        boolean status1 = accounts[0].Withdraw(3000);
        System.out.println("Withdraw 3000 : " + status1);
        System.out.println("Balance : " + accounts[0].getBalance());

        boolean status2 = accounts[0].Withdraw(10000);
        System.out.println("Withdraw 10000 : " + status2);
        System.out.println("Balance : " + accounts[0].getBalance());

        System.out.println("\n========== ALL ACCOUNTS ==========");
        for (Account acc : accounts) {
            System.out.println("----------------------------");
            System.out.println("Account No : " + acc.getAccountNumber());
            System.out.println("Owner      : " + acc.getOwnerName());
            System.out.println("Balance    : " + acc.getBalance());
            System.out.println("Active     : " + acc.isActive());
        }

        System.out.println("\n========== toString() TEST ==========");
        System.out.println(accounts[0].toString());
        System.out.println(accounts[1].toString());
        System.out.println(accounts[2].toString());
        System.out.println(accounts[3].toString());

        System.out.println("\n========== equals() TEST ==========");
        Account testAccount1 = accounts[0];

        // Same account object
        System.out.println("accounts[0].equals(testAccount1) : " + accounts[0].equals(testAccount1));

        System.out.println("\n========== instanceof TEST ==========");
        Object obj = accounts[0];
        if (obj instanceof Account) {
            System.out.println("obj is an Account object.");
        } else {
            System.out.println("obj is not an Account object.");
        }

        System.out.println("\n========== ADDRESS TEST ==========\nAccount - 4");
        System.out.println("Address Line : " + c4.getAddress().getLine());
        System.out.println("City         : " + c4.getAddress().getCity());
        System.out.println("Pincode      : " + c4.getAddress().getPincode());

        System.out.println("\n========== CLONE TEST ==========");
        Customer clonedCustomer = c4.clone();
        System.out.println("Original Customer ID : " + c4.getCustomerId());
        System.out.println("Cloned Customer ID   : " + clonedCustomer.getCustomerId());
        System.out.println("Same object? " + (c4 == clonedCustomer));
        System.out.println("Clone successful? " + (c4.getName().equals(clonedCustomer.getName())));
    }
}