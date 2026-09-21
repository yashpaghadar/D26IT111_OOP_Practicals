package Practical_8;

class OutOfStockException extends Exception {
    int shortfall;
    OutOfStockException(int shortfall) {
        this.shortfall = shortfall;
    }
    int getShortfall() {
        return shortfall;
    }
}

class InvalidQuantityException extends Exception {
    InvalidQuantityException(String message) {
        super(message);
    }
}

class Warehouse {
    // Item name and available quantity
    String[][] stock = {
        {"Laptop", "5"},
        {"Mouse", "10"},
        {"Keyboard", "3"}
    };

    void issue(String item, int qty) throws OutOfStockException, InvalidQuantityException {

        if (qty <= 0) {
            throw new InvalidQuantityException("Quantity must be greater than 0.");
        }

        for (int i = 0; i < stock.length; i++) {
            if (stock[i][0].equals(item)) {
                int available = Integer.parseInt(stock[i][1]);
                if (qty > available) {
                    int shortfall = qty - available;
                    throw new OutOfStockException(shortfall);
                }

                stock[i][1] = String.valueOf(available - qty);
                System.out.println("Issued " + qty + " " + item);
                return;
            }
        }
        System.out.println("Item not found: " + item);
    }
}

public class StockIssue {
    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse();
        String[][] requests = {
            {"Laptop", "2"},
            {"Mouse", "15"},
            {"Keyboard", "0"},
            {"Keyboard", "2"},
            {"Laptop", "10"}
        };

        for (int i = 0; i < requests.length; i++) {
            String item = requests[i][0];
            int qty = Integer.parseInt(requests[i][1]);
            
            try {
                warehouse.issue(item, qty);

            } catch (OutOfStockException e) {
                System.out.println("Out of stock. Shortfall: " + e.getShortfall());

            } catch (InvalidQuantityException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("All requests processed.");
    }
}