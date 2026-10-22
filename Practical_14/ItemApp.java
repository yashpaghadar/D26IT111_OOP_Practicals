import java.util.Scanner;
public class ItemApp {
    public static void main(String[] args) {
        ItemService service = new ItemService();
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        do {
            System.out.println("\n===== ITEM MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Item");
            System.out.println("2. View Items");
            System.out.println("3. Count Items");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            if (!sc.hasNextInt()) {
                System.out.println("Invalid input. Enter a number.");
                sc.next();
                continue;
            }
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter item ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter item name: ");
                    String name = sc.nextLine();
                    service.addItem(new Item(id, name));
                    service.performAction("ADD");
                    System.out.println("Item added successfully.");
                    break;
                case 2:
                    service.performAction("LIST");
                    if (service.getAllItems().isEmpty()) {
                        System.out.println("No items found.");
                    } else {
                        for (Item item : service.getAllItems()) {
                            System.out.println(item);
                        }
                    }
                    break;
                case 3:
                    System.out.println("Total Items: " + service.getItemCount());
                    break;
                case 4:
                    System.out.println("Exiting application.");
                    break;
                default:
                    System.out.println("Invalid menu choice.");
            }
        } while (choice != 4);
    }
}