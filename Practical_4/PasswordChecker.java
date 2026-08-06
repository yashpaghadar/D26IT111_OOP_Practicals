package Practical_4;

public class PasswordChecker {

    // Length >= 8
    public static boolean hasLength(String pw) {
        return pw.length() >= 8;
    }

    // Contains uppercase letter
    public static boolean hasUppercase(String pw) {
        return pw.matches(".*[A-Z].*");
    }

    // Contains digit
    public static boolean hasDigit(String pw) {
        return pw.matches(".*\\d.*");
    }

    // Contains special character
    public static boolean hasSpecial(String pw) {
        return pw.matches(".*[^a-zA-Z0-9].*");
    }

    // Strength method
    public static String strength(String pw) {

        int count = 0;

        if (hasLength(pw))
            count++;

        if (hasUppercase(pw))
            count++;

        if (hasDigit(pw))
            count++;

        if (hasSpecial(pw))
            count++;

        if (count <= 1)
            return "Weak";
        else if (count <= 3)
            return "Medium";
        else
            return "Strong";
    }
}