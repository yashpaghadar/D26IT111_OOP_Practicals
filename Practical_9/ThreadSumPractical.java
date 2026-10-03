
import java.util.concurrent.atomic.AtomicLong;

public class ThreadSumPractical {

    // Large array size
    static final int SIZE = 10_000_000;

    // Number of threads
    static final int THREADS = 4;

    // Shared total for race condition demonstration
    static long unsafeTotal = 0;

    // Shared total for synchronized approach
    static long synchronizedTotal = 0;

    // Shared total for AtomicLong approach
    static AtomicLong atomicTotal = new AtomicLong(0);

    // Lock object for synchronization
    static final Object lock = new Object();

    // Worker thread
    static class SumWorker extends Thread {
        int[] numbers;
        int start;
        int end;
        int method;

        SumWorker(int[] numbers, int start, int end, int method) {
            this.numbers = numbers;
            this.start = start;
            this.end = end;
            this.method = method;
        }

        @Override
        public void run() {
            for (int i = start; i < end; i++) {
                if (method == 1) {
                    // Incorrect approach: Race condition
                    unsafeTotal += numbers[i];

                } else if (method == 2) {
                    // Solution 1: synchronized
                    synchronized (lock) {
                        synchronizedTotal += numbers[i];
                    }

                } else if (method == 3) {
                    // Solution 2: AtomicLong
                    atomicTotal.addAndGet(numbers[i]);
                }
            }
        }
    }

    // Execute threads and calculate execution time
    static long calculateSum(int[] numbers, int method) throws InterruptedException {
        SumWorker[] workers = new SumWorker[THREADS];
        int chunkSize = SIZE / THREADS;
        long startTime = System.nanoTime();
        // Create and start threads
        for (int i = 0; i < THREADS; i++) {
            int start = i * chunkSize;
            int end = (i == THREADS - 1) ? SIZE : start + chunkSize;
            workers[i] = new SumWorker(numbers, start, end, method);
            workers[i].start();
        }

        // Wait for all threads
        for (SumWorker worker : workers) {
            worker.join();
        }

        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    public static void main(String[] args) throws InterruptedException {
        int[] numbers = new int[SIZE];
        // Initialize array with numbers from 1 to SIZE
        for (int i = 0; i < SIZE; i++) {
            numbers[i] = 1;
        }

        // Expected sum
        long expectedSum = SIZE;
        System.out.println("===== MULTITHREADING PRACTICAL =====");
        System.out.println("Array Size : " + SIZE);
        System.out.println("Threads    : " + THREADS);
        System.out.println("Expected Sum : " + expectedSum);

        // Method 1: Race condition
        unsafeTotal = 0;
        long unsafeTime = calculateSum(numbers, 1);
        System.out.println("\n--- Without Synchronization ---");
        System.out.println("Calculated Sum : " + unsafeTotal);
        System.out.println("Execution Time : " + unsafeTime / 1_000_000.0 + " ms");

        // Method 2: synchronized
        synchronizedTotal = 0;
        long syncTime = calculateSum(numbers, 2);
        System.out.println("\n--- Using synchronized ---");
        System.out.println("Calculated Sum : " + synchronizedTotal);
        System.out.println("Execution Time : " + syncTime / 1_000_000.0 + " ms");

        // Method 3: AtomicLong
        atomicTotal.set(0);
        long atomicTime = calculateSum(numbers, 3);
        System.out.println("\n--- Using AtomicLong ---");
        System.out.println("Calculated Sum : " + atomicTotal.get());
        System.out.println("Execution Time : " + atomicTime / 1_000_000.0 + " ms");

        System.out.println("\n===== PERFORMANCE COMPARISON =====");
        System.out.println("synchronized : " + syncTime / 1_000_000.0 + " ms");
        System.out.println("AtomicLong   : " + atomicTime / 1_000_000.0 + " ms");
    }
}