import java.lang.annotation.*;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Run {}

class SampleTests {
    @Run
    public void testAddition() {
        int result = 10 + 20;
        if (result != 30) {
            throw new AssertionError("Addition test failed.");
        }
        System.out.println("Addition test passed.");
    }
    @Run
    public void testString() {
        String value = "Java";
        if (!value.equals("Java")) {
            throw new AssertionError("String test failed.");
        }
        System.out.println("String test passed.");
    }
    public void normalMethod() {
        System.out.println("This method should not run.");
    }
}

public class MiniTestRunner {
    public static void main(String[] args) {
        SampleTests tests = new SampleTests();
        int count = 0;
        for (Method method : tests.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Run.class)) {
                if (method.getParameterCount() != 0) {
                    System.out.println("Skipping method with parameters: " + method.getName());
                    continue;
                }
                try {
                    method.invoke(tests);
                    count++;
                } catch (ReflectiveOperationException | RuntimeException e) {
                    System.out.println("Test failed: " + method.getName());
                }
            }
        }
        System.out.println("Total tests executed: " + count);
    }
}