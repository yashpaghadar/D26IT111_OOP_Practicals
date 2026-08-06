package Practical_4;

public class ChatFilter {
    
    // Check if message contains keyword (case-insensitive)
    public static boolean containsKeyword(String message, String keyword) {
        return message.toLowerCase().contains(keyword.toLowerCase());
    }
}
