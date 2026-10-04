package MiniBank.concurrency;

public class TransactionProducer implements Runnable {
    private final TransactionBuffer buffer;
    private final Runnable[] transactions;
    public TransactionProducer(TransactionBuffer buffer, Runnable[] transactions) {
        this.buffer = buffer;
        this.transactions = transactions;
    }

    @Override
    public void run() {
        try {
            for (Runnable transaction : transactions) {
                buffer.put(transaction);
                System.out.println("Produced transaction by " + Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
