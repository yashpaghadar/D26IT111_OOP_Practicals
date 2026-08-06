package Practical_3;

import java.util.Objects;

public class Point {
    private int x;
    private int y;

    // Constructor
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    // Override toString()
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // Override equals()
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Point other = (Point) obj;
        return x == other.x && y == other.y;
    }

    // Override hashCode()
    public int hashCode() {
        return Objects.hash(x, y);
    }
}