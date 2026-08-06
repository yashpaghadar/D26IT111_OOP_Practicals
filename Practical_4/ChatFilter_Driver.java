package Practical_4;
import java.util.Scanner;

public class ChatFilter_Driver {
    public static void main(String[] args) {

        String[] logs = {
                "10:05 alice Hello there",
                "10:10 bob Good morning",
                "10:15 charlie hello everyone",
                "InvalidLine"
        };

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter keyword: ");
        String keyword = sc.nextLine();
        int count = 0;
        StringBuilder report = new StringBuilder();

        for (String line : logs) {
            // Split into time, user, message
            String[] parts = line.split(" ", 3);
            // Skip malformed line
            if (parts.length < 3) {
                continue;
            }

            String time = parts[0];
            String user = parts[1];
            String message = parts[2];
            if (ChatFilter.containsKeyword(message, keyword)) {
                count++;
                report.append(time).append(" ").append(user).append(": ").append(message).append("\n");
            }
        }

        System.out.println("\nMatches: " + count);
        System.out.println("Matching Messages:");
        System.out.print(report);
        sc.close();
    }   
}
