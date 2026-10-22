package MiniBank.service;

import MiniBank.exception.*;
import MiniBank.model.Account;
import MiniBank.store.AccountStore;
import MiniBank.store.Repository;
import MiniBank.util.StatementFormatter;
import MiniBank.util.Validator;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class BankService {
    private final Repository<Account> repository;
    private final AccountStore accountStore;

    public BankService(Repository<Account> repository, AccountStore accountStore) {
        if (repository == null || accountStore == null) {
            throw new IllegalArgumentException("Repository and AccountStore cannot be null.");
        }
        this.repository = repository;
        this.accountStore = accountStore;
        for (Account account : accountStore.getAll()) {
            this.repository.save(account);
        }
    }

    public Account openAccount(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null.");
        }
        if (account.getOwnerName() == null || account.getOwnerName().isBlank()) {
            throw new IllegalArgumentException("Account holder name cannot be empty.");
        }
        if (account.getBalance() < 0) {
            throw new IllegalArgumentException("Opening balance cannot be negative.");
        }
        if (findAccount(account.getAccountNumber()) != null) {
            throw new IllegalArgumentException("Account already exists.");
        }
        repository.save(account);
        accountStore.add(account);
        accountStore.addTransaction("OPEN_ACCOUNT | " + account.getAccountNumber() + " | "
                         + account.getBalance() + " | " + LocalDateTime.now());
        return account;
    }

    public Account deposit(String accountNumber, long amount) throws BankException {
        Account account = requireAccount(accountNumber);
        account.deposit(amount);
        recordTransaction("DEPOSIT", accountNumber, amount);
        return account;
    }

    public Account withdraw(String accountNumber, long amount) throws BankException {
        Account account = requireAccount(accountNumber);
        account.withdraw(amount);
        recordTransaction("WITHDRAW", accountNumber, amount);
        return account;
    }

    public void transfer(String fromAccountNumber, String toAccountNumber, long amount) 
                        throws BankException {
        if (fromAccountNumber == null || fromAccountNumber.equals(toAccountNumber)) {
            throw new IllegalArgumentException("Source and destination accounts must be different.");
        }
        Account source = requireAccount(fromAccountNumber);
        Account destination = requireAccount(toAccountNumber);
        source.transfer(destination, amount);
        accountStore.addTransaction("TRANSFER | " + fromAccountNumber + " -> " + toAccountNumber + " | " 
                        + amount + " | " + LocalDateTime.now());
    }

    public String statement(String accountNumber) throws BankException {
        Account account = requireAccount(accountNumber);
        return StatementFormatter.buildStatement(account);
    }

    public ArrayList<Account> listAccounts() {
        return accountStore.getAll();
    }

    private Account findAccount(String accountNumber) {
        if (accountNumber == null || accountNumber.isBlank()) {
            return null;
        }
        Account account = repository.findById(accountNumber);
        return account != null ? account : accountStore.get(accountNumber);
    }

    private Account requireAccount(String accountNumber) throws AccountNotFoundException {
        Account account = findAccount(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException("Account not found: " + accountNumber);
        }
        return account;
    }

    private void recordTransaction(String type, String accountNumber, long amount) {
        accountStore.addTransaction(type + " | " + accountNumber + " | " + amount + " | " + LocalDateTime.now());
    }
}
