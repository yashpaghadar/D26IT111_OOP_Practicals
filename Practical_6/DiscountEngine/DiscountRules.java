package DiscountEngine;

public class DiscountRules {

    public static DiscountRule percentage(double percent) {
        return price -> price - (price * percent / 100);
    }

    public static DiscountRule flat(double amount) {
        return price -> Math.max(0, price - amount);
    }

    public static DiscountRule noDiscount() {
        return price -> price;
    }
}