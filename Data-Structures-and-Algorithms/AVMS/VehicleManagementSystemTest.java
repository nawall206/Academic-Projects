import java.util.Scanner;
public class VehicleManagementSystemTest {
    public static void main(String[] args) {
        VehicleManagementSystem vms = new VehicleManagementSystem();
        Scanner scanner = new Scanner(System.in);

        // Test 1: Add Vehicle
        System.out.println("--- Test 1: Add Vehicle ---");
        vms.addVehicle(scanner); // Manually calls addVehicle() method to simulate input

        // Test 2: Edit Vehicle
        System.out.println("--- Test 2: Edit Vehicle ---");
        vms.editVehicle(scanner); // Manually calls editVehicle() method to simulate input

        // Test 3: Display All Vehicles
        System.out.println("--- Test 3: Display All Vehicles ---");
        vms.displayAllVehicles(); // displays vehicles added or edited above

        // Test 4: Search for a Vehicle
        System.out.println("--- Test 4: Search for Vehicle by ID ---");
        Vehicle vehicle = vms.searchVehicle(1); // Use a valid ID 
        if (vehicle != null) {
            System.out.println("Found Vehicle: " + vehicle);
        } else {
            System.out.println("Vehicle not found.");
        }

        // Test 5: Search for Non-Existent Vehicle
        System.out.println("--- Test 5: Search for Non-Existent Vehicle ---");
        Vehicle nonExistentVehicle = vms.searchVehicle(999); // Non-existing ID
        if (nonExistentVehicle != null) {
            System.out.println("Found Vehicle: " + nonExistentVehicle);
        } else {
            System.out.println("Vehicle not found.");
        }

        // Test 6: Exit Vehicle Management System
        System.out.println("--- Test 6: Exit Vehicle Management System ---");
        System.out.println("Exiting system...");
        scanner.close();
    }
}
