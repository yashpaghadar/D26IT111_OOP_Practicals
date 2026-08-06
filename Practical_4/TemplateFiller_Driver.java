package Practical_4;

public class TemplateFiller_Driver {
    public static void main(String[] args) {
        String template = "Dear {name}, order {id} ships {date}.";
        String[] names = { "name", "id" };
        String[] values = { "Riya", "A07" };
        String output = TemplateFiller.fillTemplate(template, names, values);

        System.out.println("Original Template:");
        System.out.println(template);
        System.out.println();
        System.out.println("Filled Template:");
        System.out.println(output);
    }
}
