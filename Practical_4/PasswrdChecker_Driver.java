package Practical_4;

public class PasswrdChecker_Driver {

    public static void main(String[] args) {

        String[] passwords = {
                "abc",
                "Password",
                "Abcd1234",
                "Abcd1234!"
        };

        for (String pw : passwords) {
            System.out.println("Password: " + pw);
            System.out.println("Length >= 8 : " + PasswordChecker.hasLength(pw));
            System.out.println("Uppercase   : " + PasswordChecker.hasUppercase(pw));
            System.out.println("Digit       : " + PasswordChecker.hasDigit(pw));
            System.out.println("Special Char: " + PasswordChecker.hasSpecial(pw));
            System.out.println("Strength    : " + PasswordChecker.strength(pw));
            System.out.println("--------------------------");
        }
    }
}
