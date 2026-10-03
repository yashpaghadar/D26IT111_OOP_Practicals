package DiscountEngine;

@FunctionalInterface
public interface DiscountRule {
    double apply(double price);
}