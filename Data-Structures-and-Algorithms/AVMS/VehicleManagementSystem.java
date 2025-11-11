import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

class Vehicle {
    private int id;
    private String currentLocation;
    private String destination;
    private double distanceToDestination;
    private double batteryLevel;

    // Constructor
    public Vehicle(int id, String currentLocation, String destination, double distanceToDestination, double batteryLevel) {
        this.id = id;
        this.currentLocation = currentLocation;
        this.destination = destination;
        this.distanceToDestination = distanceToDestination;
        this.batteryLevel = batteryLevel;
    }

    // Getter for Distance to Destination
    public double getDistanceToDestination() {
        return distanceToDestination;
    }
    // Getter for Battery Level
    public double getBatteryLevel() {
        return batteryLevel;
    }

    // Other Getters
    public int getId() {
        return id;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public String getDestination() {
        return destination;
    }

    // Setter for Distance to Destination
    public void setDistanceToDestination(double distanceToDestination) {
        this.distanceToDestination = distanceToDestination;
    }

    // Setter for Current Location
    public void setLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    // Setter for Destination
    public void setDestination(String destination) {
        this.destination = destination;
    }

    // Setter for Battery Level
    public void setBatteryLevel(double batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    @Override
    public String toString() {
        return "Vehicle ID: " + id + "\n" +
               "Current Location: " + currentLocation + "\n" +
               "Destination: " + destination + "\n" +
               "Distance to Destination: " + distanceToDestination + " km\n" +
               "Battery Level: " + batteryLevel + "%\n";
    }
}

public class VehicleManagementSystem {
    private final List<Vehicle> vehicles = new ArrayList<>();

    // Helper method to get a valid integer input
    private int getValidInt(String prompt, Scanner scanner) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    // Helper method to get a valid double input
    private double getValidDouble(String prompt, Scanner scanner) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                if (value < 0) {
                    System.out.println("Value cannot be negative. Try again.");
                } else {
                    return value;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    // Method to return the list of vehicles
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    // Add a new vehicle
    public void addVehicle(Scanner scanner) {
        int vehicleID = getValidInt("Enter Vehicle ID: ", scanner);
        System.out.print("Enter Current Location: ");
        String location = scanner.nextLine();
        System.out.print("Enter Destination: ");
        String destination = scanner.nextLine();
        double distance = getValidDouble("Enter Distance to Destination (in km): ", scanner);
        double battery = getValidDouble("Enter Battery Level (%): ", scanner);

        Vehicle newVehicle = new Vehicle(vehicleID, location, destination, distance, battery);
        vehicles.add(newVehicle);
        System.out.println("Vehicle added successfully!");
    }

    // Edit an existing vehicle
    public void editVehicle(Scanner scanner) {
        int vehicleID = getValidInt("Enter Vehicle ID to edit: ", scanner);

        for (Vehicle vehicle : vehicles) {
            if (vehicle.getId() == vehicleID) {
                System.out.print("Enter New Location: ");
                vehicle.setLocation(scanner.nextLine());

                System.out.print("Enter New Destination: ");
                vehicle.setDestination(scanner.nextLine());

                vehicle.setDistanceToDestination(getValidDouble("Enter New Distance to Destination (in km): ", scanner));
                vehicle.setBatteryLevel(getValidDouble("Enter New Battery Level (%): ", scanner));

                System.out.println("Vehicle updated successfully!");
                return;
            }
        }
        System.out.println("Vehicle not found!");
    }

    // Display all vehicles
    public void displayAllVehicles() {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles available.");
        } else {
            for (Vehicle vehicle : vehicles) {
                System.out.println(vehicle);
            }
        }
    }

    // Search for a vehicle by ID
    public Vehicle searchVehicle(int id) {
        for (Vehicle v : vehicles) {
            if (v.getId() == id) {
                return v;
            }
        }
        return null;
    }

    // Run the Vehicle Management System menu
    public void run(Scanner scanner) {
        int choice;
        do {
            System.out.println("\nVehicle Management System");
            System.out.println("1. Add Vehicle");
            System.out.println("2. Edit Vehicle");
            System.out.println("3. Display All Vehicles");
            System.out.println("4. Exit");
            choice = getValidInt("Enter your choice: ", scanner);

            switch (choice) {
                case 1 -> addVehicle(scanner);
                case 2 -> editVehicle(scanner);
                case 3 -> displayAllVehicles();
                case 4 -> System.out.println("Exiting Vehicle Management System...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        VehicleManagementSystem vms = new VehicleManagementSystem();
        vms.run(scanner);
    }
}