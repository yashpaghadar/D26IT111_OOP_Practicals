package DiscountEngine;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DiscountEngine {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Double> prices = new ArrayList<>();
        System.out.print("Enter number of prices: ");
        int n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            System.out.print("Enter price " + (i + 1) + ": ");
            prices.add(sc.nextDouble());
        }

        System.out.println("\nChoose Discount Rule:");
        System.out.println("1. 10% Discount");
        System.out.println("2. 20% Discount");
        System.out.println("3. Flat 50 Discount");
        System.out.println("4. No Discount");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();

        DiscountRule rule;

        switch (choice) {
            case 1 -> rule = DiscountRules.percentage(10);
            case 2 -> rule = DiscountRules.percentage(20);
            case 3 -> rule = DiscountRules.flat(50);
            case 4 -> rule = DiscountRules.noDiscount();
            default -> {
                System.out.println("Invalid choice.");
                sc.close();
                return;
            }
        }

        System.out.println("\nOriginal Price -> Final Price");
        for (double price : prices) {
            double finalPrice = rule.apply(price);
            System.out.printf(
                    "%.2f -> %.2f%n",
                    price,
                    finalPrice
            );
        }
    }
}