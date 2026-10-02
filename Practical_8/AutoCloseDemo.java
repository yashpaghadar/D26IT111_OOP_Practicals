package Practical_8;

class MyResource implements AutoCloseable {
    public MyResource() {
        System.out.println("Resource opened.");
    }
    public void work() {
        System.out.println("Working with resource...");
        throw new RuntimeException("Original error occurred!");
    }
    public void close() {
        System.out.println("Resource closed.");
    }
}

public class AutoCloseDemo {
    public static void main(String[] args) {
        try (MyResource resource = new MyResource()) {
            resource.work();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("Program continues.");
    }
}