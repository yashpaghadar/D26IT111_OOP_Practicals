package MiniBank.store;

import MiniBank.model.Account;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class AccountStore {
    private final HashMap<String, Account> accounts = new HashMap<>();
    private final ArrayList<String> transactionHistory = new ArrayList<>();

    public void add(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null.");
        }
        accounts.put(account.getAccountNumber(), account);
    }

    public Account get(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public ArrayList<Account> getAll() {
        return new ArrayList<>(accounts.values());
    }

    public void addTransaction(String transaction) {
        transactionHistory.add(transaction);
    }

    public ArrayList<String> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }

    public ArrayList<Account> sortedByBalance() {
        ArrayList<Account> sortedAccounts = getAll();
        sortedAccounts.sort(Comparator.comparingLong(Account::getBalance));
        return sortedAccounts;
    }

    public void displayAccountsSortedByBalance() {
        for (Account account : sortedByBalance()) {
            System.out.println(account);
        }
    }

    public int size() {
        return accounts.size();
    }
}
