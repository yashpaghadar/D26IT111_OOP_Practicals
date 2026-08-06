package Practical_3;

public class Point_Driver {
    public static void main(String[] args) {

        Point[] points = {
                new Point(1, 2),
                new Point(3, 4),
                new Point(1, 2), // Duplicate
                new Point(5, 6),
                new Point(3, 4) // Duplicate
        };

        int distinctCount = 0;

        System.out.println("Distinct Points:");

        for (int i = 0; i < points.length; i++) {
            boolean found = false;

            for (int j = 0; j < i; j++) {
                if (points[i].equals(points[j])) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println(points[i]); // Calls toString()
                distinctCount++;
            }
        }

        System.out.println("Distinct: " + distinctCount);
    }
}