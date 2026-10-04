package MiniBank.concurrency;

public class TransactionConsumer implements Runnable {
    private final TransactionBuffer buffer;
    private final int transactionCount;
    public TransactionConsumer(
            TransactionBuffer buffer,
            int transactionCount) {
        this.buffer = buffer;
        this.transactionCount = transactionCount;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < transactionCount; i++) {
                Runnable transaction = buffer.get();
                System.out.println("Processing transaction by "+ Thread.currentThread().getName());
                transaction.run();
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}