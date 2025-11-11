import java.util.ArrayList;
import java.util.List;

public class VehicleSorterTest {
    public static void main(String[] args) {
        // Create test vehicles
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(new Vehicle(1, "Location A", "Destination A", 50.0, 80.0));
        vehicles.add(new Vehicle(2, "Location B", "Destination B", 30.0, 60.0));
        vehicles.add(new Vehicle(3, "Location C", "Destination C", 70.0, 90.0));

        // Test 1: Heap Sort by Distance to Destination (Ascending)
        System.out.println("--- Test 1: Heap Sort by Distance to Destination ---");
        VehicleSorter.heapSort(vehicles);
        VehicleSorter.printVehicles(vehicles);  // Print sorted vehicles by distance

        // Test 2: Quick Sort by Battery Level (Descending)
        System.out.println("--- Test 2: Quick Sort by Battery Level ---");
        VehicleSorter.quickSort(vehicles, 0, vehicles.size() - 1);
        VehicleSorter.printVehicles(vehicles);  // Print sorted vehicles by battery level

        // Test 3: Find the Nearest Vehicle
        System.out.println("--- Test 3: Find Nearest Vehicle ---");
        Vehicle nearestVehicle = VehicleSorter.findNearestVehicle(vehicles);
        if (nearestVehicle != null) {
            System.out.println("Nearest Vehicle: " + nearestVehicle);
        } else {
            System.out.println("No vehicles available.");
        }

        // Test 4: Find the Vehicle with the Highest Battery Level
        System.out.println("--- Test 4: Find Vehicle with Highest Battery Level ---");
        Vehicle highestBatteryVehicle = VehicleSorter.findVehicleWithHighestBattery(vehicles);
        if (highestBatteryVehicle != null) {
            System.out.println("Vehicle with Highest Battery: " + highestBatteryVehicle);
        } else {
            System.out.println("No vehicles available.");
        }
    }
}
