package Practical_4;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateFiller {
    public static String fillTemplate(String template, String[] names, String[] values) {

        Pattern pattern = Pattern.compile("\\{(\\w+)\\}");
        Matcher matcher = pattern.matcher(template);
        StringBuilder result = new StringBuilder();
        int lastIndex = 0;

        while (matcher.find()) {
            // Append text before placeholder
            result.append(template.substring(lastIndex, matcher.start()));
            String placeholder = matcher.group(1);
            String replacement = "[?]";

            // Search placeholder in names array
            for (int i = 0; i < names.length; i++) {
                if (names[i].equals(placeholder)) {
                    replacement = values[i];
                    break;
                }
            }

            result.append(replacement);
            lastIndex = matcher.end();
        }

        // Append remaining text
        result.append(template.substring(lastIndex));
        return result.toString();
    }
}