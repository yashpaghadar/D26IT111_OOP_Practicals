package Practical_2;

public class A2_CinemaShow {

    private String title;
    private int seatsAvailable;
    private final int capacity;
    private static int totalBooked = 0;

    // Constructor with title and capacity
    public A2_CinemaShow(String title, int capacity) {
        this.title = title;
        this.capacity = capacity;
        this.seatsAvailable = capacity;
    }

    // Constructor with default capacity (Constructor Chaining)
    public A2_CinemaShow(String title) {
        this(title, 100);
    }

    // Book seats
    public boolean book(int n) {
        if (n <= seatsAvailable) {
            seatsAvailable -= n;
            totalBooked += n;
            return true;
        } else {
            return false;
        }
    }

    // Cancel seats
    public void cancel(int n) {
        seatsAvailable += n;
        if (seatsAvailable > capacity) {
            seatsAvailable = capacity;
        }
        if (totalBooked > 0 && totalBooked > n){
            totalBooked -= n;
        }
    }

    // Getter for available seats
    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    // Static getter for total booked seats
    public static int getTotalBooked() {
        return totalBooked;
    }

    // Main method
    public static void main(String[] args) {

        A2_CinemaShow C1 = new A2_CinemaShow("Avengers", 50);
        System.out.println("Cinema 1: "+C1.title);
        System.out.println("Available Seats: " + C1.getSeatsAvailable());
        System.out.println("\nBooking 20 seats: " + C1.book(20));
        System.out.println("Seats Available: " + C1.getSeatsAvailable());
        System.out.println("\nBooking 15 seats: "  + C1.book(15));
        System.out.println("Seats Available: "+ C1.getSeatsAvailable());
        System.out.println("\nBooking 20 seats : " + C1.book(20));
        System.out.println("Seats Available: "+ C1.getSeatsAvailable());
        System.out.println("\nCancel 10 seats. ");
        C1.cancel(10);
        System.out.println("Seats Available: " + C1.getSeatsAvailable());
        System.out.println("\nBooking 20 seats: "+ C1.book(20));
        System.out.println("Seats Available: " + C1.getSeatsAvailable());

        A2_CinemaShow C2 = new A2_CinemaShow("Spiderman");
        System.out.println("------------------------\nCinema 2: "+C2.title);
        System.out.println("Available Seats: " + C2.getSeatsAvailable());
        System.out.println("\nBooking 20 seats: "+ C2.book(20));
        System.out.println("Seats Available: " + C2.getSeatsAvailable());
        System.out.println("\nCancel 10 seats. ");
        C2.cancel(10);
        System.out.println("Seats Available: " + C2.getSeatsAvailable());
        System.out.println("\nTotal Booked Seats form all Cinema: " + A2_CinemaShow.getTotalBooked());

        
    }
}