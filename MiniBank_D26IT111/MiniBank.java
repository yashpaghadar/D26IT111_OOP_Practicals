package MiniBank_D26IT111;

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
    EXIT
}

public class MiniBank {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Print Bank Header
        BankInfo bank = new BankInfo("MiniBank", "Anand Branch");
        System.out.println("================================");
        System.out.println(bank);
        System.out.println("================================");
        boolean running = true;

        while (running) {

            // Display Menu
            System.out.println("\n===== MINI BANK MENU =====");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            // Convert number into MenuOption
            MenuOption option = switch (choice) {
                case 1 -> MenuOption.OPEN_ACCOUNT;
                case 2 -> MenuOption.DEPOSIT;
                case 3 -> MenuOption.WITHDRAW;
                case 4 -> MenuOption.TRANSFER;
                case 5 -> MenuOption.EXIT;
                default -> null;
            };

            if (option == null) {
                System.out.println("Invalid Choice! Please try again.");
                continue;
            }
            switch (option) {
                case OPEN_ACCOUNT ->
                    System.out.println("Open Account - To be implemented in a later lab.");

                case DEPOSIT ->
                    System.out.println("Deposit - To be implemented in a later lab.");

                case WITHDRAW ->
                    System.out.println("Withdraw - To be implemented in a later lab.");

                case TRANSFER ->
                    System.out.println("Transfer - To be implemented in a later lab.");

                case EXIT -> {
                    System.out.println("Thank you for using MiniBank!");
                    running=false;
                }
            }
        }
    }
}
