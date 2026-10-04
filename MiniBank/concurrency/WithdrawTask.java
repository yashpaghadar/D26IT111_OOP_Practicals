package MiniBank.concurrency;
import MiniBank.model.Account;

public class WithdrawTask implements Runnable {
    private final Account account;
    private final long amount;
    public WithdrawTask(Account account, long amount) {
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void run() {
        try {
            account.withdraw(amount);
            System.out.println(Thread.currentThread().getName()+ " withdrew " + amount + " from " + account.getAccountNumber());

        } catch (Exception e) {
            System.out.println(Thread.currentThread().getName() + " withdrawal failed: " + e.getMessage());
        }
    }
}
