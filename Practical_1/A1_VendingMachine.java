import java.util.Scanner;

public class A1_VendingMachine {
    // declare coins
    enum Coins {
        ONE, TWO, FIVE, TEN
    }

    public static void main(String[] args) {
        int price = 15, count = 0;

        System.out.println("Snack Price :" + price + "\nPlease Insert Coins (ONE, TWO, FIVE, TEN):\n");

        // create object of Scannner class for take user input.
        Scanner sc = new Scanner(System.in);

        while (count < price) {
            // take coins value.
            System.out.print("Coins: ");
            String input = sc.next().toUpperCase();

            Coins coin;
            try {
                coin = Coins.valueOf(input);
            } catch (Exception e) {
                System.out.println("Please enter a Valid Coin.\n!! Try Again !!\n");
                continue;
            }

            int value = 0;
            switch (coin) {
                case ONE:
                    value = 1;
                    break;
                case TWO:
                    value = 2;
                    break;
                case FIVE:
                    value = 5;
                    break;
                case TEN:
                    value = 10;
                    break;

            }

            count += value;
            System.out.println("Inserted so far: " + count);
        }

        System.out.println("Paid. Change :" + (count - price));
    }

}
