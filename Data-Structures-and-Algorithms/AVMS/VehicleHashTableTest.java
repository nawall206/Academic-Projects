public class VehicleHashTableTest {
    public static void main(String[] args) {
        VehicleHashTable vehicleTable = new VehicleHashTable();

        // Test 1: Insert Vehicles
        System.out.println("\n--- Test 1: Insert Vehicles ---");
        vehicleTable.insert(new VehicleHash("1", "Toyota", "Anna"));
        vehicleTable.insert(new VehicleHash("2", "Honda", "Yasmeen"));
        vehicleTable.insert(new VehicleHash("3", "BNW", "Brook"));

        // Test 2: Insert Duplicate
        System.out.println("\n--- Test 2: Insert Duplicate ---");
        vehicleTable.insert(new VehicleHash("1", "Mazda", "David")); 

        // Test 3: Search Existing Vehicle
        System.out.println("\n--- Test 3: Search Existing Vehicle ---");
        VehicleHash found = vehicleTable.search("2");
        System.out.println(found != null ? "Found: " + found : "Vehicle not found");

        // Test 4: Search Non-Existing Vehicle
        System.out.println("\n--- Test 4: Search Non-Existing Vehicle ---");
        VehicleHash notFound = vehicleTable.search("999");
        System.out.println(notFound != null ? "Found: " + notFound : "Vehicle not found");

        // Test 5: Delete Existing Vehicle
        System.out.println("\n--- Test 5: Delete Existing Vehicle ---");
        boolean deleted = vehicleTable.delete("3");
        System.out.println(deleted ? "Deleted successfully." : "Deletion failed.");

        // Test 6: Delete Non-Existing Vehicle
        System.out.println("\n--- Test 6: Delete Non-Existing Vehicle ---");
        boolean notDeleted = vehicleTable.delete("999");
        System.out.println(notDeleted ? "Deleted successfully." : "Deletion failed.");

        // Test 7: Display All Vehicles
        System.out.println("\n--- Test 7: Display All Vehicles ---");
        vehicleTable.displayAll();
    }
}
