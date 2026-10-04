package MiniBank.concurrency;
import MiniBank.model.Account;

public class DeadlockTransfer implements Runnable {

    private final Account from;
    private final Account to;
    private final long amount;

    public DeadlockTransfer(Account from, Account to, long amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    @Override
    public void run() {
        synchronized (from) {
            System.out.println(Thread.currentThread().getName() + " locked " + from.getAccountNumber());

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            synchronized (to) {
                System.out.println(Thread.currentThread().getName() + " locked " + to.getAccountNumber());
                try {
                    from.withdraw(amount);
                    to.deposit(amount);
                    System.out.println(Thread.currentThread().getName() + " transferred ₹" + amount);
                } catch (Exception e) {
                    System.out.println("Transfer failed: " + e.getMessage());
                }
            }
        }
    }
}
