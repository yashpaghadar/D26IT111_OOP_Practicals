import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface NotBlank {}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface MaxLength {
    int value();
}

class SignupForm {
    @NotBlank
    private String username;
    @NotBlank
    @MaxLength(30)
    private String email;
    @NotBlank
    @MaxLength(12)
    private String password;
    public SignupForm(String username,String email,String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}

class FormChecker {
    public static List<String> validate(Object object) throws IllegalAccessException {
        List<String> errors = new ArrayList<>();
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(object);
            if (field.isAnnotationPresent(NotBlank.class)) {
                if (value == null || value.toString().trim().isEmpty()) {
                    errors.add(field.getName() + " must not be blank.");
                }
            }
            MaxLength maxLength = field.getAnnotation(MaxLength.class);
            if (maxLength != null && value != null && value.toString().length() > maxLength.value()) {
                errors.add(field.getName() + " must not exceed " + maxLength.value() + " characters.");
            }
        }
        return errors;
    }
}

public class FormValidator {
    public static void main(String[] args) throws IllegalAccessException {
        SignupForm form = new SignupForm("Yash", "", "thispasswordislong");
        List<String> errors = FormChecker.validate(form);
        if (errors.isEmpty()) {
            System.out.println("Form is valid.");
        } else {
            System.out.println("Validation Errors:");
            for (String error : errors) {
                System.out.println("- " + error);
            }
        }
    }
}