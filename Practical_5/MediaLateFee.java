/*
3. (For Advanced Learners – home) Model a few media types that each compute a late fee differently and total the fees for a returned batch. 
Design the hierarchy and rules yourself.
*/


package Practical_5;

abstract class Media {
    String title;
    int id;
    int lateDays;

    Media(String title, int id, int lateDays) {
        this.title = title;
        this.id = id;
        this.lateDays = lateDays;
    }
    abstract double lateFee();
}

class Book extends Media {
    Book(String title, int id, int lateDays) {
        super(title, id, lateDays);
    }

    double lateFee() {
        return lateDays * 5;
    }
}

class DVD extends Media {
    DVD(String title, int id, int lateDays) {
        super(title, id, lateDays);
    }

    double lateFee() {
        return lateDays * 10;
    }
}

class Magazine extends Media {
    Magazine(String title, int id, int lateDays) {
        super(title, id, lateDays);
    }

    double lateFee() {
        return lateDays * 3;
    }
}

public class MediaLateFee {
    public static void main(String[] args) {
        Media[] returnedMedia = {
            new Book("Java Programming", 101, 4),
            new DVD("Interstellar", 102, 3),
            new Magazine("Tech Monthly", 103, 5),
            new Book("Data Structures", 104, 2),
            new DVD("The Matrix", 105, 6)
        };

        double totalFee = 0;
        System.out.println("Returned Media Late Fees:");
        for (Media media : returnedMedia) {
            double fee = media.lateFee();
            System.out.println("Title: " + media.title + " | Late Days: " + media.lateDays + " | Late Fee: " + String.format("%.2f", fee));
            totalFee += fee;
        }

        System.out.println("\nTotal Late Fee = " + String.format("%.2f", totalFee));
    }
}