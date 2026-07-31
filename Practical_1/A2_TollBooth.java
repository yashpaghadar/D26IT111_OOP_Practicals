import java.util.Scanner;

public class A2_TollBooth {
    record Vehicle(String number, String type) {
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int Total=0, bike = 0, truck = 0, car = 0;
        System.out.println("ENTER VEHICLE (Type 'done' for STOP):");

        while (true) {
            System.out.print("Enter Vehicle Number: ");
            String num = sc.next().toUpperCase();
            if (num.equals("DONE")) {
                break;
            }
            System.out.print("Vehicle Type (bike, car, truck) :");
            String type = sc.next().toLowerCase();

            Vehicle v = new Vehicle(num, type);
            // check vehicle case
            switch (v.type) {
                case "bike":
                    bike++;
                    Total += 20;
                    System.out.println("  " + v.number() + " (" + v.type() + ") pays 20.");
                    break;
                case "car":
                    car++;
                    Total += 50;
                    System.out.println("  " + v.number() + " (" + v.type() + ") pays 50.");
                    break;
                case "truck":
                    truck++;
                    Total += 150;
                    System.out.println("  " + v.number() + " (" + v.type() + ") pays 50.");
                    break;
                default:
                    break;
            }
        }

        System.out.println("\nTotal Toll:" + Total);
        String Frequent;
        if (bike > car && bike > truck) {
            Frequent = "bike";
        } else if (car > bike && car > truck) {
            Frequent = "car";
        } else if (truck > bike && truck > car) {
            Frequent = "truck";
        } else if (bike == car && bike == truck) {          // check all vehicle value same or not.
            Frequent = "All Vehicle Number are Same";
        } else if (bike == car) {                           // ckeck bike and car value same or not.
            Frequent = "cars & bikes both";
        } else if (bike == truck) {                         // ckeck bike and truck value same or not.
            Frequent = "bikes & trucks both";
        } else if (car == truck) {                          // ckeck car and truck value same or not.
            Frequent = "cars & trucks both";
        } else {
            Frequent = "Null";
        }

        if (Total == 0) {
            System.out.println("NOT any Vehicle Pass from TOLL.");
        } else {
            System.out.println("Most Frequent: " + Frequent);
        }
    }
}