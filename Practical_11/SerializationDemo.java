
import java.io.*;

class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    int id;
    String name;
    double marks;
    transient String password;

    Student(int id, String name, double marks, String password) {
        this.id = id;
        this.name = name;
        this.marks = marks;
        this.password = password;
    }

    void display() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Marks: " + marks);
        System.out.println("Password: " + password);
        System.out.println("--------------------");
    }
}

public class SerializationDemo {
    public static void main(String[] args) {
        String fileName = "students.dat";
        Student[] students = {
                new Student(1, "Yash", 92.5, "pass123"),
                new Student(2, "Rahul", 85.0, "pass456"),
                new Student(3, "Priya", 89.5, "pass789")
        };

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(students);
            System.out.println("Student objects saved successfully.\n");
        } catch (IOException e) {
            System.out.println("Error saving objects: " + e.getMessage());
            return;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            Student[] loadedStudents = (Student[]) in.readObject();
            System.out.println("Student objects loaded from file:\n");

            for (Student student : loadedStudents) {
                student.display();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading objects: " + e.getMessage());
        }
    }
}