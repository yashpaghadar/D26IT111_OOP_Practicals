package Practical_2;

public class CinemaShow {

    private String title;
    private int seatsAvailable;
    private final int capacity;
    private static int totalBooked = 0;

    // Constructor with title and capacity
    public CinemaShow(String title, int capacity) {
        this.title = title;
        this.capacity = capacity;
        this.seatsAvailable = capacity;
    }

    // Constructor with default capacity (Constructor Chaining)
    public CinemaShow(String title) {
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

        CinemaShow show = new CinemaShow("Avengers", 50);

        System.out.println("Available Seats: " + show.getSeatsAvailable());

        System.out.println("\nBooking 20 seats: " + show.book(20));
        System.out.println("Seats Available: " + show.getSeatsAvailable());
        System.out.println("\nBooking 15 seats: " + show.book(15));
        System.out.println("Seats Available: " + show.getSeatsAvailable());
        System.out.println("\nBooking 20 seats: " + show.book(20));
        System.out.println("Seats Available: " + show.getSeatsAvailable());

        System.out.println("\nCancel 10 seats");
        show.cancel(10);
        System.out.println("Seats Available: " + show.getSeatsAvailable());

        System.out.println("\nBooking 20 seats: " + show.book(20));
        System.out.println("Seats Available: " + show.getSeatsAvailable());
        System.out.println("\nTotal Booked Seats: " + CinemaShow.getTotalBooked());
    }
}