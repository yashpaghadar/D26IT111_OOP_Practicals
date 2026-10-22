import java.util.ArrayList;
import java.util.List;

public class ItemService {
    private final List<Item> items = new ArrayList<>();
    public void addItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");
        }
        items.add(item);
    }
    public int getItemCount() {
        return items.size();
    }
    public List<Item> getAllItems() {
        return new ArrayList<>(items);
    }
    public void performAction(String action) {
        if (!"ADD".equals(action) && !"LIST".equals(action)) {
            throw new IllegalArgumentException("Invalid action: " + action);
        }
    }
}