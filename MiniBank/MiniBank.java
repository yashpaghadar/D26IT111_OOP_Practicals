package MiniBank;

import java.util.Scanner;

// Record
record BankInfo(String name, String branch) {
}

// Enum
enum MenuOption {
    OPEN_ACCOUNT,
    DEPOSIT,
    WITHDRAW,
    TRANSFER,
    SHOW_DETAILS,
    EXIT
}

public class MiniBank {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // Bank information
        BankInfo bank = new BankInfo("MiniBank", "Anand Branch");

        System.out.println("======================================");
        System.out.println(bank);
        System.out.println("======================================");

        // Account array
        Account[] accounts = new Account[10];

        // Number of accounts currently created
        int accountCount = 0;
        boolean running = true;

        while (running) {

            System.out.println("\n===== MINI BANK MENU =====");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Show Account Details");
            System.out.println("6. Exit");

            System.out.print("Enter Choice : ");

            int choice = sc.nextInt();
            sc.nextLine();

            MenuOption option = switch (choice) {

                case 1 -> MenuOption.OPEN_ACCOUNT;
                case 2 -> MenuOption.DEPOSIT;
                case 3 -> MenuOption.WITHDRAW;
                case 4 -> MenuOption.TRANSFER;
                case 5 -> MenuOption.SHOW_DETAILS;
                case 6 -> MenuOption.EXIT;

                default -> null;
            };

            if (option == null) {
                System.out.println("Invalid Choice!");
                continue;
            }

            switch (option) {
                // 1. OPEN ACCOUNT
                case OPEN_ACCOUNT -> {
                    if (accountCount >= accounts.length) {
                        System.out.println("Account limit reached!");
                        break;
                    }

                    System.out.print("Enter Owner Name : ");
                    String ownerName = sc.nextLine();

                    System.out.print("Enter Opening Balance : ");
                    long openingBalance = sc.nextLong();
                    sc.nextLine();

                    accounts[accountCount] = new Account(ownerName, openingBalance);
                    System.out.println("\nAccount Created Successfully!");
                    System.out.println("Account Number : " + accounts[accountCount].getAccountNumber());
                    accountCount++;
                }

                // 2. DEPOSIT
                case DEPOSIT -> {

                    if (accountCount == 0) {
                        System.out.println("No accounts available!");
                        break;
                    }

                    System.out.print("Enter Account Number : ");
                    String accountNumber = sc.nextLine();
                    Account account = findAccount(accounts, accountCount, accountNumber);
                    if (account == null) {
                        System.out.println("Account not found!");
                        break;
                    }

                    System.out.print("Enter Deposit Amount : ");
                    long amount = sc.nextLong();
                    sc.nextLine();
                    account.deposit(amount);

                    System.out.println("Amount Deposited Successfully!");
                    System.out.println("Current Balance : " + account.getBalance());
                }

                // 3. WITHDRAW
                case WITHDRAW -> {
                    if (accountCount == 0) {
                        System.out.println("No accounts available!");
                        break;
                    }

                    System.out.print("Enter Account Number : ");
                    String accountNumber = sc.nextLine();
                    Account account = findAccount(accounts, accountCount, accountNumber);
                    if (account == null) {
                        System.out.println("Account not found!");
                        break;
                    }

                    System.out.print("Enter Withdrawal Amount : ");
                    long amount = sc.nextLong();
                    sc.nextLine();

                    boolean success = account.withdraw(amount);
                    if (success) {
                        System.out.println("Amount Withdrawn Successfully!");
                        System.out.println("Current Balance : "+ account.getBalance());
                    } else {
                        System.out.println("Withdrawal Failed! Insufficient Balance.");
                    }
                }

                // 4. TRANSFER
                case TRANSFER -> {
                    System.out.println("Transfer - To be implemented in later lab.");
                }

                // 5. SHOW ACCOUNT DETAILS
                case SHOW_DETAILS -> {
                    if (accountCount == 0) {
                        System.out.println("No accounts available!");
                        break;
                    }

                    System.out.println("\n===== ACCOUNT DETAILS =====");
                    for (int i = 0; i < accountCount; i++) {
                        Account account = accounts[i];
                        System.out.println("Account Number : " + account.getAccountNumber());
                        System.out.println("Owner Name     : " + account.getOwnerName());
                        System.out.println("Balance        : " + account.getBalance());
                        System.out.println("Active         : " + account.isActive());
                        System.out.println("----------------------------");
                    }
                    running = false;
                }

                // 6. EXIT
                case EXIT -> {
                    System.out.println("Thank You for using MiniBank.");
                    running = false;
                }
            }
        }
    }

    // Find account using account number
    private static Account findAccount(
            Account[] accounts,
            int accountCount,
            String accountNumber) {

        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].getAccountNumber().equals(accountNumber)) {
                return accounts[i];
            }
        }
        return null;
    }

}
