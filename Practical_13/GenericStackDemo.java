class GenericStack<T> {
    private Object[] elements;
    private int top;
    public GenericStack(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive.");
        }
        elements = new Object[capacity];
        top = -1;
    }
    public void push(T value) {
        if (top == elements.length - 1) {
            throw new IllegalStateException("Stack is full.");
        }
        elements[++top] = value;
    }
    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty.");
        }
        T value = (T) elements[top];
        elements[top--] = null;
        return value;
    }
    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty.");
        }
        return (T) elements[top];
    }
    public boolean isEmpty() {
        return top == -1;
    }
    public int size() {
        return top + 1;
    }
}
public class GenericStackDemo {
    public static void main(String[] args) {
        GenericStack<Integer> numbers = new GenericStack<>(5);
        numbers.push(10);
        numbers.push(20);
        numbers.push(30);

        System.out.println("Integer Stack Top: " + numbers.peek());
        System.out.println("Popped Integer: " + numbers.pop());
        System.out.println("Integer Stack Size: " + numbers.size());

        GenericStack<String> names = new GenericStack<>(5);
        names.push("Java");
        names.push("Python");
        names.push("PHP");

        System.out.println("String Stack Top: " + names.peek());
        System.out.println("Popped String: " + names.pop());
        System.out.println("String Stack Size: " + names.size());

        GenericStack<Integer> emptyStack = new GenericStack<>(3);
        try {
            emptyStack.pop();
        } catch (IllegalStateException e) {
            System.out.println("Empty Stack: " + e.getMessage());
        }
    }
}