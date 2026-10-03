package MiniBank;

import java.util.Scanner;

import MiniBank.exception.BankException;
import MiniBank.exception.InsufficientFundsException;
import MiniBank.exception.InvalidAmountException;

import MiniBank.model.*;
import MiniBank.util.StatementFormatter;

// Record
record BankInfo(String name, String branch) {
}

// Enum
enum MenuOption {
    OPEN_ACCOUNT, DEPOSIT, WITHDRAW, TRANSFER, SHOW_DETAILS, EXIT
}

public class MiniBank {

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

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Bank information
        BankInfo bank = new BankInfo("MiniBank", "Anand Branch");

        System.out.println("======================================");
        System.out.println(bank);
        System.out.println("======================================");

        // Account array
        Account[] accounts = new Account[25];
        int accountCount = 0;
        boolean running = true;

        /*
         * /
         * // ==========================================
         * // PRACTICAL 3 TESTING
         * // ==========================================
         * 
         * Account account1 = new SavingsAccount("Yash", 5000, 1000);
         * Account account2 = new CurrentAccount("Rahul", 10000, 5000);
         * 
         * // toString()
         * System.out.println("\n===== toString() TEST =====");
         * System.out.println(account1);
         * System.out.println(account2);
         * 
         * // equals()
         * System.out.println("\n===== equals() TEST =====");
         * System.out.println("Are account1 and account2 equal? " +
         * account1.equals(account2));
         * System.out.println("Are account1 and account1 equal? " +
         * account1.equals(account1));
         * 
         * // instanceof
         * System.out.println("\n===== instanceof TEST =====");
         * Object obj = account1;
         * if (obj instanceof Account) {
         * System.out.println("obj is an Account object.");
         * } else {
         * System.out.println("obj is not an Account object.");
         * }
         * 
         * 
         * // Customer and Address test
         * System.out.println("\n===== CUSTOMER TEST =====");
         * Customer customer = new Customer("Yash", "yash@example.com", "9876543210");
         * Customer.Address address = new Customer.Address( "CSPIT Campus", "Anand",
         * "388001");
         * customer.setAddress(address);
         * System.out.println("Customer ID : " + customer.getCustomerId());
         * System.out.println("Name        : " + customer.getName());
         * System.out.println("Email       : " + customer.getEmail());
         * System.out.println("Mobile      : " + customer.getMobile());
         * System.out.println("Address     : " +
         * customer.getAddress().getFullAddress());
         * 
         * // Clone test
         * Customer clonedCustomer = customer.clone();
         * System.out.println("\n===== CLONE TEST =====");
         * System.out.println("Original Customer ID : "+ customer.getCustomerId());
         * System.out.println("Cloned Customer ID   : "+
         * clonedCustomer.getCustomerId());
         * System.out.println("Is clone a different object? " + (customer !=
         * clonedCustomer));
         * 
         * 
         * // Put test accounts into account array
         * accounts[accountCount++] = account1;
         * accounts[accountCount++] = account2;
         */

        /*
         * /
         * // ==========================================
         * // PRACTICAL 4 TEST
         * // ==========================================
         * 
         * System.out.println("\n========== VALIDATOR TEST ==========");
         * 
         * // Mobile
         * System.out.println("Mobile 9876543210 : "+
         * Validator.isValidMobile("9876543210"));
         * System.out.println("Mobile 1234567890 : "+
         * Validator.isValidMobile("1234567890"));
         * 
         * // Email
         * System.out.println("Email yash@gmail.com : "+
         * Validator.isValidEmail("yash@gmail.com"));
         * System.out.println("Email yash@gmail : "+
         * Validator.isValidEmail("yash@gmail"));
         * 
         * // PAN
         * System.out.println("PAN ABCDE1234F : "+ Validator.isValidPan("ABCDE1234F"));
         * System.out.println("PAN ABC1234F : " + Validator.isValidPan("ABC1234F"));
         * 
         * // IFSC
         * System.out.println("IFSC SBIN0001234 : " +
         * Validator.isValidIfsc("SBIN0001234"));
         * System.out.println("IFSC SBI1234 : " + Validator.isValidIfsc("SBI1234"));
         * 
         * // Command test
         * System.out.println("\n========== COMMAND TEST ==========");
         * Command command = CommandParser.parse("DEPOSIT AC0001 500");
         * System.out.println("Transaction Type : " + command.type());
         * System.out.println("Account Number   : " + command.accountNumber());
         * System.out.println("Amount           : " + command.amount());
         * 
         * // Statement test
         * System.out.println("\n========== STATEMENT TEST ==========");
         * Account testAccount = new SavingsAccount("Yash", 1000, 500);
         * System.out.println(StatementFormatter.buildStatement(testAccount));
         */

        // ==========================================
        // PRACTICAL 5 TESTING
        // ==========================================

        // Create three different account types
        Account savings = new SavingsAccount("Yash", 5000, 1000);
        Account current = new CurrentAccount("Rahul", 10000, 5000);
        Account fixedDeposit = new FixedDepositAccount("Jay", 20000);

        // Account array
        Account[] accountTypes = { savings, current, fixedDeposit };
        accounts[accountCount] = savings;
        accountCount++;
        accounts[accountCount] = current;
        accountCount++;
        accounts[accountCount] = fixedDeposit;
        accountCount++;
        // Runtime polymorphism
        System.out.println("\n===== INTEREST RATE TEST =====");
        for (Account acc : accountTypes) {
            System.out.println(acc.getOwnerName() + " -> Interest Rate: " + acc.interestRate() + "%");
        }

        // instanceof pattern matching
        System.out.println("\n===== INSTANCEOF TEST =====");
        if (savings instanceof SavingsAccount sa) {
            System.out.println("Savings Account Minimum Balance: " + sa.getMinBalance());
        }

        // Withdrawal rules
        System.out.println("\n===== WITHDRAWAL TEST =====");
        System.out.println("Savings withdraw 3000: " + savings.canWithdraw(3000));
        System.out.println("Current withdraw 14000: " + current.canWithdraw(14000));
        System.out.println("Fixed Deposit withdraw 1000: " + fixedDeposit.canWithdraw(1000));

        // ==========================================
        // MiniBank MENU
        // ==========================================

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
                    System.out.println("\nSelect Account Type:");
                    System.out.println("1. Savings Account");
                    System.out.println("2. Current Account");
                    System.out.println("3. Fixed Deposit Account");
                    System.out.print("Enter Account Type : ");
                    int accountType = sc.nextInt();
                    sc.nextLine();
                    switch (accountType) {
                        case 1 -> {
                            System.out.print("Enter Minimum Balance : ");
                            long minBalance = sc.nextLong();
                            sc.nextLine();
                            accounts[accountCount] = new SavingsAccount(ownerName, openingBalance, minBalance);
                        }
                        case 2 -> {
                            System.out.print("Enter Overdraft Limit : ");
                            long overdraftLimit = sc.nextLong();
                            sc.nextLine();
                            accounts[accountCount] = new CurrentAccount(ownerName, openingBalance, overdraftLimit);
                        }
                        case 3 -> {
                            accounts[accountCount] = new FixedDepositAccount(ownerName, openingBalance);
                        }
                        default -> {
                            System.out.println("Invalid Account Type!");
                            break;
                        }
                    }

                    if (accounts[accountCount] != null) {
                        System.out.println("\nAccount Created Successfully!");
                        System.out.println("Account Number : " + accounts[accountCount].getAccountNumber());
                        accountCount++;
                    }
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
                    try {
                        account.deposit(amount);
                        System.out.println("Deposit successful.");
                        System.out.println("Current Balance: " + account.getBalance());

                    } catch (InvalidAmountException e) {
                        System.out.println("Invalid Amount: " + e.getMessage());
                    }
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

                    try {
                        boolean success = account.withdraw(amount);

                        if (success) {
                            System.out.println("Withdrawal successful.");
                            System.out.println("Current Balance: " + account.getBalance());
                        }

                    } catch (InsufficientFundsException e) {
                        System.out.println("Withdrawal failed: " + e.getMessage());
                        System.out.println("Shortfall: " + e.getShortfall());

                    } catch (InvalidAmountException e) {
                        System.out.println("Invalid Amount: " + e.getMessage());
                    }
                }

                // 4. TRANSFER
                case TRANSFER -> {
                    if (accountCount < 2) {
                        System.out.println("At least two accounts are required for transfer!");
                        break;
                    }

                    System.out.print("Enter Source Account Number : ");
                    String fromAccountNumber = sc.nextLine();
                    Account fromAccount = findAccount(accounts, accountCount, fromAccountNumber);

                    if (fromAccount == null) {
                        System.out.println("Source account not found!");
                        break;
                    }

                    System.out.print("Enter Destination Account Number : ");
                    String toAccountNumber = sc.nextLine();
                    Account toAccount = findAccount(accounts, accountCount, toAccountNumber);

                    if (toAccount == null) {
                        System.out.println("Destination account not found!");
                        break;
                    }

                    System.out.print("Enter Transfer Amount : ");
                    long amount = sc.nextLong();
                    sc.nextLine();

                    try {
                        fromAccount.transfer(toAccount, amount);
                        System.out.println("Transfer successful.");
                        System.out.println("Source Account Balance: " + fromAccount.getBalance());
                        System.out.println("Destination Account Balance: " + toAccount.getBalance());

                    } catch (InsufficientFundsException e) {
                        System.out.println("Transfer failed: Insufficient funds.");
                        System.out.println("Shortfall: " + e.getShortfall());
                    } catch (InvalidAmountException e) {
                        System.out.println("Transfer failed: " + e.getMessage());
                    } catch (BankException e) {
                        System.out.println("Transfer failed: " + e.getMessage());
                    }
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
                        System.out.println(StatementFormatter.buildStatement(account));
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
}
