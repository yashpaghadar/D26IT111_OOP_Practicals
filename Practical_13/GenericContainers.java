class Box<T> {
    private T value;
    public void set(T value) {
        this.value = value;
    }
    public T get() {
        return value;
    }
}
class Pair<A,B> {
    private A first;
    private B second;
    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }
    public A getFirst() {
        return first;
    }
    public B getSecond() {
        return second;
    }
}
public class GenericContainers {
    public static <T> T firstElement(T[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty.");
        }
        return array[0];
    }
    public static void main(String[] args) {
        Box<Integer> intBox = new Box<>();
        intBox.set(100);
        System.out.println("Integer Box: " + intBox.get());

        Box<String> stringBox = new Box<>();
        stringBox.set("Hello Java");
        System.out.println("String Box: " + stringBox.get());

        Pair<String,Integer> student = new Pair<>("Yash", 101);
        System.out.println("Pair First: " + student.getFirst());
        System.out.println("Pair Second: " + student.getSecond());

        Integer[] numbers = {10, 20, 30};
        String[] names = {"Java", "Python", "PHP"};
        System.out.println("First Number: " + firstElement(numbers));
        System.out.println("First Name: " + firstElement(names));
    }
}