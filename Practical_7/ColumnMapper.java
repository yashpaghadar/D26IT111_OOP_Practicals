import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Column {
    String name();
}

class Student {
    @Column(name = "student_name")
    private String name;
    @Column(name = "student_age")
    private int age;
    @Column(name = "student_city")
    private String city;
    @Override
    public String toString() {
        return "Student{name='" + name + "', age=" + age + ", city='" + city + "'}";
    }
}

class ObjectMapper {
    public static List<String> populate(Object object, String[] headers, String[] data) throws ReflectiveOperationException {
        if (headers.length != data.length) {
            throw new IllegalArgumentException("Header and data lengths must match.");
        }
        List<String> errors = new ArrayList<>();
        Map<String, Integer> columnIndexes = new HashMap<>();
        for (int i = 0; i < headers.length; i++) {
            columnIndexes.put(headers[i], i);
        }
        for (Field field : object.getClass().getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            if (column == null) {
                continue;
            }
            Integer index = columnIndexes.get(column.name());
            if (index == null) {
                errors.add("Missing column: " + column.name());
                continue;
            }
            field.setAccessible(true);
            String value = data[index];
            if (field.getType() == String.class) {
                field.set(object, value);
            } else if (field.getType() == int.class) {
                field.setInt(object, Integer.parseInt(value));
            } else {
                errors.add("Unsupported field type: " + field.getName());
            }
        }
        return errors;
    }
}

public class ColumnMapper {
    public static void main(String[] args) throws ReflectiveOperationException {
        String[] headers = {"student_name", "student_age"};
        String[] data = {"Yash", "20"};
        Student student = new Student();
        List<String> errors = ObjectMapper.populate(student, headers, data);
        System.out.println(student);
        if (errors.isEmpty()) {
            System.out.println("Mapping completed successfully.");
        } else {
            for (String error : errors) {
                System.out.println("Warning: " + error);
            }
        }
    }
}