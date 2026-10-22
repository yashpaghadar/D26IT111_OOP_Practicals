import java.util.*;

public class UniqueVisitors {
    public static void main(String[] args) {
        List<String> logins = Arrays.asList("Yash", "Rahul", "Amit", "Yash", "Priya", "Rahul", "Neha", "Amit", "Karan");
        HashSet<String> uniqueUsers = new HashSet<>(logins);
        LinkedHashSet<String> orderedUsers = new LinkedHashSet<>(logins);
        System.out.println("Total Login Entries: " + logins.size());
        System.out.println("Unique Visitors Count: " + uniqueUsers.size());
        System.out.println("First Three Distinct Visitors:");
        int count = 0;
        for (String user : orderedUsers) {
            System.out.println(user);
            count++;
            if (count == 3) {
                break;
            }
        }
    }
}