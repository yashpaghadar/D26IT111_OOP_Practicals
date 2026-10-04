package MiniBank.concurrency;
import MiniBank.model.Account;

public class DepositTask implements Runnable {
    private final Account account;
    private final long amount;
    public DepositTask(Account account, long amount) {
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void run() {
        try {
            account.deposit(amount);
            System.out.println(Thread.currentThread().getName() + " deposited " + amount
                            + " into " + account.getAccountNumber()
            );

        } catch (Exception e) {
            System.out.println(Thread.currentThread().getName()
                            + " deposit failed: " + e.getMessage()
            );
        }
    }
}
