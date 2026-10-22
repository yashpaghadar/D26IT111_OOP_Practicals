package MiniBank.concurrency;

import MiniBank.model.Account;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TransactionProcessor {
    private final ExecutorService executorService;
    private final ConcurrentHashMap<String, Account> accounts;

    public TransactionProcessor() {
        executorService = Executors.newFixedThreadPool(4);
        accounts = new ConcurrentHashMap<>();
    }

    public void addAccount(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null.");
        }
        accounts.put(account.getAccountNumber(), account);
    }

    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public ConcurrentHashMap<String, Account> getAccounts() {
        return new ConcurrentHashMap<>(accounts);
    }

    public void submit(Runnable task) {
        executorService.execute(task);
    }

    public void stop() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
