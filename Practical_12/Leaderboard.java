import java.util.*;

public class Leaderboard {
    public static void main(String[] args) {
        Map<String, Integer> scores = new HashMap<>();
        scores.put("Yash", 95);
        scores.put("Rahul", 88);
        scores.put("Amit", 95);
        scores.put("Priya", 92);
        scores.put("Neha", 88);
        scores.put("Karan", 78);
        int n = 3;
        List<Map.Entry<String, Integer>> players = new ArrayList<>(scores.entrySet());
        players.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> a, Map.Entry<String, Integer> b) {
                int result = b.getValue().compareTo(a.getValue());
                return result != 0 ? result : a.getKey().compareTo(b.getKey());
            }
        });
        System.out.println("Top " + n + " Players:");
        System.out.println("-------------------------");
        for (int i = 0; i < Math.min(n, players.size()); i++) {
            Map.Entry<String, Integer> player = players.get(i);
            System.out.println((i + 1) + ". " + player.getKey() + " - " + player.getValue());
        }
    }
}