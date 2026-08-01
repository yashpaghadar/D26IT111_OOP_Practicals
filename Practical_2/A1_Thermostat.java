package Practical_2;

public class A1_Thermostat {

    private String location;
    private int temperature;

    private static final int MIN = 16;
    private static final int MAX = 30;

    private static int activeCount = 0;

    // Constructor with location and starting temperature
    public A1_Thermostat(String location, int startTemp) {
        this.location = location;

        if (startTemp >= MIN && startTemp <= MAX) {
            this.temperature = startTemp;
        } else {
            this.temperature = 22;
        }

        activeCount++;
    }

    // Constructor with only location (Constructor Chaining)
    public A1_Thermostat(String location) {
        this(location, 22);
    }

    // Increase temperature
    public void raise() {
        if (temperature < MAX) {
            temperature++;
        } else {
            System.out.println("Already at maximum (30)");
        }
    }

    // Decrease temperature
    public void lower() {
        if (temperature > MIN) {
            temperature--;
        } else {
            System.out.println("Already at minimum (16)");
        }
    }

    // Getter for temperature
    public int getTemperature() {
        return temperature;
    }

    // Static getter for active count
    public static int getActiveCount() {
        return activeCount;
    }

    // Main method
    public static void main(String[] args) {

        A1_Thermostat t1 = new A1_Thermostat("Bedroom");
        A1_Thermostat t2 = new A1_Thermostat("Hall", 25);

        System.out.println("Initial Temperature: " + t1.getTemperature());

        System.out.println("\nRaising Temperature:");
        for (int i = 0; i < 10; i++) {
            t1.raise();
            System.out.println("Temperature: " + t1.getTemperature());
        }

        System.out.println("\nLowering Temperature:");
        for (int i = 0; i < 20; i++) {
            t1.lower();
            System.out.println("Temperature: " + t1.getTemperature());
        }

        System.out.println("\nActive Thermostats: " + A1_Thermostat.getActiveCount());
    }
}