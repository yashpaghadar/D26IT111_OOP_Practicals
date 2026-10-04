package Practical_10;
public class DeadlockFixed {
    static class BankAccount {
        private final String name;
        BankAccount(String name) {
            this.name = name;
        }
    }

    // Always lock accounts in the same order
    static void transfer(BankAccount first, BankAccount second) {
        synchronized (first) {
            System.out.println(Thread.currentThread().getName() + " locked Account A");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            synchronized (second) {
                System.out.println(Thread.currentThread().getName() + " locked Account B");
                System.out.println(Thread.currentThread().getName() + " completed transfer"
                );
            }
        }
    }

    public static void main(String[] args) {
        BankAccount accountA = new BankAccount("Account A");
        BankAccount accountB = new BankAccount("Account B");
        Thread thread1 = new Thread(() -> {
            transfer(accountA, accountB);
        }, "Thread-1");

        Thread thread2 = new Thread(() -> {
            transfer(accountA, accountB);
        }, "Thread-2");

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Program completed without deadlock.");
    }
}