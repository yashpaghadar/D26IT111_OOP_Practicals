import java.util.*;

public class WordFrequency {
    public static void main(String[] args) {
        String paragraph = "Java is easy and Java is powerful. Java is popular.";
        String[] words = paragraph.toLowerCase().replaceAll("[^a-z\\s]", "").split("\\s+");
        HashMap<String, Integer> frequency = new HashMap<>();
        for (String word : words) {
            frequency.put(word, frequency.getOrDefault(word, 0) + 1);
        }
        List<Map.Entry<String, Integer>> list = new ArrayList<>(frequency.entrySet());
        list.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> a, Map.Entry<String, Integer> b) {
                int result = b.getValue().compareTo(a.getValue());
                return result != 0 ? result : a.getKey().compareTo(b.getKey());
            }
        });
        System.out.println("Word Frequencies (Highest First):");
        for (Map.Entry<String, Integer> entry : list) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}