interface Switchable {
    void on();
    void off();

    // Default method
    default void toggle() {
        System.out.println("Toggling device...");
        off();
        on();
    }
}

class Fan implements Switchable {
    public void on() {
        System.out.println("Fan is ON");
    }
    public void off() {
        System.out.println("Fan is OFF");
    }
}

class Light implements Switchable {
    public void on() {
        System.out.println("Light is ON");
    }
    public void off() {
        System.out.println("Light is OFF");
    }
}

// Functional interface
interface SwitchPolicy {
    boolean maySwitchOn(Switchable device, int hour);
}


public class RemoteControl {
    public static void main(String[] args) {
        // Array of Switchable devices
        Switchable[] devices = {new Fan(), new Light()};

        // Toggle each device
        System.out.println("=== Toggling Devices ===");
        for (Switchable device : devices) {
            device.toggle();
        }

        // Functional interface using Anonymous Class
        SwitchPolicy Anonymous = new SwitchPolicy() {
            public boolean maySwitchOn(Switchable device, int hour) {
                return hour >= 6 && hour <= 22;
            }
        };

        // Functional interface using Lambda
        SwitchPolicy Lambda = (device, hour) -> hour >= 6 && hour <= 22;

        // Test policies
        System.out.println("\n=== Switch Policy ===");
        int hour = 10;
        System.out.println("Anonymous Class: " + Anonymous.maySwitchOn(devices[0], hour));
        System.out.println("Lambda: " + Lambda.maySwitchOn(devices[1], hour));
    }
}