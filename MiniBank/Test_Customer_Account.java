package MiniBank;

public class Test_Customer_Account {

    public static void main(String[] args) {

        // Create Customers
        Customer c1 = new Customer("Yash", "yash@gmail.com", "9876543210");
        Customer c2 = new Customer("Rahul", "rahul@gmail.com", "9988776655");
        Customer c3 = new Customer("Amit", "amit@gmail.com", "9123456789");

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

        System.out.println("\n========== ACCOUNT DETAILS ==========");

        Account[] accounts = new Account[3];
        accounts[0] = new Account(c1.getName(), 5000);
        accounts[1] = new Account(c2.getName());
        accounts[2] = new Account(c3.getName(), 10000);

        System.out.println("Transaction of "+accounts[0].getAccountNumber()+" Account.");
        long amt = 2000;
        accounts[0].Deposit(amt);
        System.out.println("Deposit "+amt+".");
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
    }
}