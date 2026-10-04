package Practical_10;

public class DeadlockDemo {

    static class BankAccount {
        private final String name;

        BankAccount(String name) {
            this.name = name;
        }
    }

    public static void main(String[] args) {

        BankAccount accountA = new BankAccount("Account A");
        BankAccount accountB = new BankAccount("Account B");

        // Thread 1 locks A first, then tries to lock B
        Thread thread1 = new Thread(() -> {

            synchronized (accountA) {
                System.out.println("Thread 1 locked Account A");

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                System.out.println("Thread 1 waiting for Account B");

                synchronized (accountB) {
                    System.out.println("Thread 1 locked Account B");
                }
            }

        }, "Thread-1");

        // Thread 2 locks B first, then tries to lock A
        Thread thread2 = new Thread(() -> {

            synchronized (accountB) {
                System.out.println("Thread 2 locked Account B");

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                System.out.println("Thread 2 waiting for Account A");

                synchronized (accountA) {
                    System.out.println("Thread 2 locked Account A");
                }
            }

        }, "Thread-2");

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Program completed.");
    }
}