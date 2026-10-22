package MiniBank.model;

import MiniBank.annotations.Id;
import MiniBank.exception.AccountNotFoundException;
import MiniBank.exception.BankException;
import MiniBank.exception.InsufficientFundsException;
import MiniBank.exception.InvalidAmountException;
import java.io.Serializable;

public abstract class Account implements Comparable<Account>, Transactable, InterestBearing, Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private final String accountNumber;
    private String ownerName;
    private long balance;
    private boolean active;
    private static int nextAccountNumber = 1;

    public Account(String ownerName, long openingBalance) {
        this.accountNumber = String.format("AC%04d", nextAccountNumber++);
        this.ownerName = ownerName;
        this.balance = openingBalance;
        this.active = true;
    }

    public Account(String accountNumber, String ownerName, long balance, boolean active) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
        this.active = active;
    }

    // Abstract methods
    public abstract double interestRate();

    public abstract boolean canWithdraw(long amount);

    // Getters
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public long getBalance() {
        return balance;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public int compareTo(Account other) {
        return this.getAccountNumber().compareTo(other.getAccountNumber());
    }

    // Existing methods
    public void deposit(long amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be greater than zero.");
        }

        balance += amount;
    }

    public boolean withdraw(long amount) throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be greater than zero.");
        }

        if (!canWithdraw(amount)) {
            long shortfall = amount - balance;
            throw new InsufficientFundsException("Insufficient funds. Shortfall: " + shortfall, shortfall);
        }

        balance -= amount;
        return true;
    }

    public void transfer(Account to, long amount) throws BankException {
        try {
            if (to == null) {
                throw new AccountNotFoundException("Destination account was not found.");
            }
            withdraw(amount);
            to.deposit(amount);

        } catch (BankException e) {
            System.out.println("Transfer failed: " + e.getMessage());
            throw e;
        } finally {
            System.out.println("Transfer operation completed.");
        }
    }

    public String toString() {
        return "Account{"
                + "accountNumber='" + accountNumber + '\''
                + ", ownerName='" + ownerName + '\''
                + ", balance=" + balance
                + ", active=" + active
                + '}';
    }

}
