package MiniBank;

import MiniBank.concurrency.*;
import MiniBank.exception.*;
import MiniBank.model.*;
import MiniBank.store.AccountStore;
import MiniBank.store.Repository;
import MiniBank.util.*;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.util.Arrays;
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
    EXECUTOR_TRANSACTIONS,
    PRODUCER_CONSUMER,
    DEADLOCK,
    SAVE_ACCOUNTS,
    GENERATE_REPORT,
    SORT_ACCOUNTS,
    EXIT
}

public class MiniBank {

    private static final Path DATA_DIR = Paths.get("MiniBank", "data");
    private static final Path ACCOUNT_FILE = DATA_DIR.resolve("accounts.dat");
    private static final Path LOG_DIR = DATA_DIR.resolve("logs");
    private static final Path TRANSACTION_FILE = LOG_DIR.resolve("transactions.txt");
    private static final Path REPORT_FILE = DATA_DIR.resolve("end-of-day-report.txt");

    // Save accounts using Java Serialization
    private static void saveAccounts(Account[] accounts, int accountCount) {
        try {
            Files.createDirectories(DATA_DIR);
            Account[] savedAccounts = Arrays.copyOf(accounts, accountCount);
            try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(ACCOUNT_FILE))) {
                out.writeObject(savedAccounts);
            }
            System.out.println("Accounts saved successfully to " + ACCOUNT_FILE);
        } catch (IOException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }

    // Load accounts using Java Deserialization
    private static Account[] loadAccounts() {
        if (!Files.exists(ACCOUNT_FILE)) {
            System.out.println("No saved account file found.");
            return new Account[0];
        }
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(ACCOUNT_FILE))) {
            Object data = in.readObject();
            if (data instanceof Account[] loadedAccounts) {
                System.out.println("Accounts loaded successfully.");
                return loadedAccounts;
            }
            System.out.println("Invalid account data.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading accounts: " + e.getMessage());
        }
        return new Account[0];
    }

    // Append a transaction without overwriting previous records
    private static void appendTransaction(String type, String accountNumber, long amount) {
        try {
            Files.createDirectories(LOG_DIR);
            String record = type + "|" + accountNumber + "|" + amount + "|" + LocalDateTime.now()
                    + System.lineSeparator();
            Files.writeString(TRANSACTION_FILE, record,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Error writing transaction log: " + e.getMessage());
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

    private static void runTransactionProcessor(Account account) {
        TransactionProcessor processor = new TransactionProcessor();
        processor.addAccount(account);
        Account sharedAccount = processor.getAccount(account.getAccountNumber());

        // Submit multiple deposit tasks using the account stored in ConcurrentHashMap
        processor.submit(new DepositTask(sharedAccount, 500));
        processor.submit(new DepositTask(sharedAccount, 1000));
        processor.submit(new DepositTask(sharedAccount, 1500));
        processor.submit(new DepositTask(sharedAccount, 2000));

        // Submit multiple withdrawal tasks
        processor.submit(new WithdrawTask(sharedAccount, 300));
        processor.submit(new WithdrawTask(sharedAccount, 400));
        processor.submit(new WithdrawTask(sharedAccount, 500));
        processor.submit(new WithdrawTask(sharedAccount, 600));

        // Wait for all tasks to complete
        processor.stop();
        System.out.println("\nFinal Account Details:");
        System.out.println(account);
    }

    private static void runProducerConsumer(Account account) throws InterruptedException {

        TransactionBuffer buffer = new TransactionBuffer(3);
        Runnable[] transactions = {
            new DepositTask(account, 500),
            new WithdrawTask(account, 200),
            new DepositTask(account, 1000),
            new WithdrawTask(account, 300),
            new DepositTask(account, 1500),
            new WithdrawTask(account, 400)
        };

        Thread producer = new Thread(new TransactionProducer(buffer, transactions), "Producer");
        Thread consumer = new Thread(new TransactionConsumer(buffer, transactions.length), "Consumer");

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        System.out.println("\nProducer-Consumer completed.");
    }

    private static void demonstrateDeadlock(Account accountA, Account accountB) {

        System.out.println("\n--- Deadlock Demonstration ---");
        Thread thread1 = new Thread(new DeadlockTransfer(accountA, accountB, 100), "Deadlock-Thread-1");
        Thread thread2 = new Thread(new DeadlockTransfer(accountB, accountA, 100), "Deadlock-Thread-2");

        thread1.setDaemon(true);
        thread2.setDaemon(true);
        thread1.start();
        thread2.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Thread-1 State: " + thread1.getState());
        System.out.println("Thread-2 State: " + thread2.getState());
        System.out.println("Deadlock demonstration completed.");
    }

    private static void generateReport() {
        long totalDeposits = 0;
        long totalWithdrawals = 0;
        int transactionCount = 0;
        try {
            Files.createDirectories(DATA_DIR);
            Files.createDirectories(LOG_DIR);
            try (DirectoryStream<Path> files = Files.newDirectoryStream(LOG_DIR, "*.txt")) {
                for (Path file : files) {
                    BasicFileAttributes attributes = Files.readAttributes(file, BasicFileAttributes.class);
                    System.out.println("\nLog File: " + file.getFileName());
                    System.out.println("File Size: " + attributes.size() + " bytes");
                    System.out.println("Last Modified: " + attributes.lastModifiedTime());
                    try (BufferedReader reader = Files.newBufferedReader(file)) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            String[] parts = line.split("\\|", 4);
                            if (parts.length < 3) {
                                continue;
                            }
                            try {
                                long amount = Long.parseLong(parts[2]);
                                if ("DEPOSIT".equals(parts[0])) {
                                    totalDeposits += amount;
                                    transactionCount++;
                                } else if ("WITHDRAW".equals(parts[0])) {
                                    totalWithdrawals += amount;
                                    transactionCount++;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Skipping invalid transaction: " + line);
                            }
                        }
                    }
                }
            }
            String report = "========== MINI BANK END-OF-DAY REPORT ==========\n"
                    + "Generated At: " + LocalDateTime.now() + "\n"
                    + "Total Transactions: " + transactionCount + "\n"
                    + "Total Deposits: " + totalDeposits + "\n"
                    + "Total Withdrawals: " + totalWithdrawals + "\n"
                    + "Net Transaction Amount: " + (totalDeposits - totalWithdrawals) + "\n";
            Files.writeString(REPORT_FILE, report,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("\n" + report);
            System.out.println("Report saved to " + REPORT_FILE);
        } catch (IOException e) {
            System.out.println("Error generating report: " + e.getMessage());
        }
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
        AccountStore accountStore = new AccountStore();
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
        // // ==========================================
        // // PRACTICAL 5 TESTING
        // // ==========================================
        // // Create three different account types
        // Account savings = new SavingsAccount("Yash", 5000, 1000);
        // Account current = new CurrentAccount("Rahul", 10000, 5000);
        // Account fixedDeposit = new FixedDepositAccount("Jay", 20000);
        // // Account array
        // Account[] accountTypes = { savings, current, fixedDeposit };
        // accounts[accountCount++] = savings;
        // accounts[accountCount++] = current;
        // accounts[accountCount++] = fixedDeposit;
        // accountStore.add(savings);
        // accountStore.add(current);
        // accountStore.add(fixedDeposit);
        // System.out.println("\n===== PRACTICAL 12: ACCOUNTS SORTED BY BALANCE =====");
        // accountStore.displayAccountsSortedByBalance();
        // // Runtime polymorphism
        // System.out.println("\n===== INTEREST RATE TEST =====");
        // for (Account acc : accountTypes) {
        //     System.out.println(acc.getOwnerName() + " -> Interest Rate: " +
        //             acc.interestRate() + "%");
        // }
        // // instanceof pattern matching
        // System.out.println("\n===== INSTANCEOF TEST =====");
        // if (savings instanceof SavingsAccount sa) {
        //     System.out.println("Savings Account Minimum Balance: " + sa.getMinBalance());
        // }
        // // Withdrawal rules
        // System.out.println("\n===== WITHDRAWAL TEST =====");
        // System.out.println("Savings withdraw 3000: " + savings.canWithdraw(3000));
        // System.out.println("Current withdraw 14000: " + current.canWithdraw(14000));
        // System.out.println("Fixed Deposit withdraw 1000: " +
        //         fixedDeposit.canWithdraw(1000));

        /*
         * // ================= PRACTICAL 9 - THREADING =================
         * 
         * // Create one account
         * Account threadAccount = new SavingsAccount("ThreadUser", 0, 100);
         * // Create 10 workers
         * Thread[] threads = new Thread[10];
         * for (int i = 0; i < threads.length; i++) {
         * AccountWorker worker = new AccountWorker(threadAccount, 1000, 10);
         * threads[i] = new Thread(worker, "Worker-" + (i + 1));
         * System.out.println(threads[i].getName() + " created.");
         * threads[i].start();
         * }
         * 
         * // Wait for all threads to finish
         * for (Thread thread : threads) {
         * try {
         * thread.join();
         * } catch (InterruptedException e) {
         * Thread.currentThread().interrupt();
         * System.out.println("Main thread interrupted.");
         * }
         * }
         * 
         * // Print final balance
         * System.out.println("\n===== RESULT =====");
         * System.out.println("Final Balance: " + threadAccount.getBalance());
         * System.out.println("Expected Balance: 100000");
         */
        // PRACTICAL 13: GENERIC REPOSITORY TEST
        System.out.println("\n===== PRACTICAL 13: GENERIC REPOSITORY =====");

        Repository<Account> accountRepository = new Repository<>();

        Account repoAccount1 = new SavingsAccount("Yash", 5000, 1000);
        Account repoAccount2 = new CurrentAccount("Rahul", 10000, 5000);
        Account repoAccount3 = new FixedDepositAccount("Amit", 15000);

        // Save multiple accounts
        accountRepository.saveAll(repoAccount1, repoAccount2, repoAccount3);
        System.out.println("Accounts saved successfully.");

        // Find account by ID
        String searchId = repoAccount1.getAccountNumber();
        System.out.println("\n===== FIND ACCOUNT BY ID =====");
        System.out.println("Searching for: " + searchId);
        System.out.println("Found Account: " + accountRepository.findById(searchId));

        // Display all accounts
        System.out.println("\n===== ALL REPOSITORY ACCOUNTS =====");
        for (Account account : accountRepository.findAll()) {
            System.out.println(account);
        }

        // Test deletion
        System.out.println("\n===== DELETE ACCOUNT =====");
        System.out.println("Deleted: " + accountRepository.delete(repoAccount3.getAccountNumber()));
        System.out.println("Remaining accounts: " + accountRepository.findAll().size());

        // Test bounded generic method
        Integer[] numbers = {10, 20, 30, 40, 50};
        System.out.println("\n===== BOUNDED GENERIC METHOD =====");
        System.out.println("Average: " + Repository.average(numbers));

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
            System.out.println("6. ExecutorService Transactions");
            System.out.println("7. Producer-Consumer");
            System.out.println("8. Deadlock Demonstration");
            System.out.println("9. Save and Reload Accounts");
            System.out.println("10. Generate End-of-Day Report");
            System.out.println("11. Show Accounts Sorted by Balance");
            System.out.println("0. Exit");
            System.out.print("Enter Choice : ");

            int choice = sc.nextInt();
            sc.nextLine();

            MenuOption option = switch (choice) {
                case 1 ->
                    MenuOption.OPEN_ACCOUNT;
                case 2 ->
                    MenuOption.DEPOSIT;
                case 3 ->
                    MenuOption.WITHDRAW;
                case 4 ->
                    MenuOption.TRANSFER;
                case 5 ->
                    MenuOption.SHOW_DETAILS;
                case 6 ->
                    MenuOption.EXECUTOR_TRANSACTIONS;
                case 7 ->
                    MenuOption.PRODUCER_CONSUMER;
                case 8 ->
                    MenuOption.DEADLOCK;
                case 9 ->
                    MenuOption.SAVE_ACCOUNTS;
                case 10 ->
                    MenuOption.GENERATE_REPORT;
                case 11 ->
                    MenuOption.SORT_ACCOUNTS;
                case 0 ->
                    MenuOption.EXIT;
                default ->
                    null;
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
                        accountStore.add(accounts[accountCount]);
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
                        appendTransaction("DEPOSIT", account.getAccountNumber(), amount);
                        accountStore.addTransaction("DEPOSIT | " + account.getAccountNumber() + " | " + amount + " | " + LocalDateTime.now());
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
                            appendTransaction("WITHDRAW", account.getAccountNumber(), amount);
                            accountStore.addTransaction("WITHDRAW | " + account.getAccountNumber() + " | " + amount + " | " + LocalDateTime.now());
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
                        accountStore.addTransaction("TRANSFER | " + fromAccount.getAccountNumber() + " -> " + toAccount.getAccountNumber() + " | " + amount + " | " + LocalDateTime.now());
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
                }

                case EXECUTOR_TRANSACTIONS -> {
                    if (accountCount == 0) {
                        System.out.println("No accounts available!");
                        break;
                    }

                    Account account = accounts[0];
                    System.out.println("\n===== EXECUTOR SERVICE TEST =====");
                    System.out.println("Using Account: " + account.getAccountNumber());
                    runTransactionProcessor(account);
                }

                case PRODUCER_CONSUMER -> {
                    if (accountCount == 0) {
                        System.out.println("No accounts available!");
                        break;
                    }

                    Account account = accounts[0];
                    System.out.println("\n===== PRODUCER-CONSUMER TEST =====");
                    try {
                        runProducerConsumer(account);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println("Producer-consumer interrupted.");
                    }
                }

                case DEADLOCK -> {
                    if (accountCount < 2) {
                        System.out.println("At least two accounts are required!");
                        break;
                    }

                    Account accountA = accounts[0];
                    Account accountB = accounts[1];
                    demonstrateDeadlock(accountA, accountB);
                }

                case SAVE_ACCOUNTS -> {
                    saveAccounts(accounts, accountCount);
                    Account[] loadedAccounts = loadAccounts();
                    System.out.println("\n===== RELOADED ACCOUNT DETAILS =====");
                    for (Account account : loadedAccounts) {
                        System.out.println(account);
                    }
                    System.out.println("Original Account Count: " + accountCount);
                    System.out.println("Reloaded Account Count: " + loadedAccounts.length);
                }

                case GENERATE_REPORT -> {
                    generateReport();
                    running = false;
                }

                case SORT_ACCOUNTS -> {
                    System.out.println("\n===== ACCOUNTS SORTED BY BALANCE =====");
                    accountStore.displayAccountsSortedByBalance();
                    System.out.println("Total Accounts: " + accountStore.size());
                    System.out.println("\n===== TRANSACTION HISTORY =====");
                    if (accountStore.getTransactionHistory().isEmpty()) {
                        System.out.println("No transactions recorded in this session.");
                    } else {
                        for (String transaction : accountStore.getTransactionHistory()) {
                            System.out.println(transaction);
                        }
                    }
                }

                // 0. EXIT
                case EXIT -> {
                    saveAccounts(accounts, accountCount);
                    System.out.println("Thank You for using MiniBank.");
                    running = false;
                }
            }
        }
    }
}
