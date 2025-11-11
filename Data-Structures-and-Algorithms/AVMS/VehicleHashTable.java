import java.util.LinkedList;
import java.util.Scanner;

class VehicleHash {
    String vehicleID;
    String model;
    String owner;

    public VehicleHash(String vehicleID, String model, String owner) {
        this.vehicleID = vehicleID;
        this.model = model;
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "VehicleID: " + vehicleID + ", Model: " + model + ", Owner: " + owner;
    }
}

public class VehicleHashTable {
    private static final int SIZE = 10; // Hash table size
    
    @SuppressWarnings("unchecked")
    private LinkedList<VehicleHash>[] table = new LinkedList[SIZE]; // Array of linked lists for separate chaining

    public VehicleHashTable() {
        for (int i = 0; i < SIZE; i++) {
            table[i] = new LinkedList<>();
        }
    }

    // Hash function based on vehicle ID
    private int hashFunction(String vehicleID) {
        return Math.abs(vehicleID.hashCode() % SIZE);
    }

    // Insert a vehicle
    // Insert a vehicle
    public void insert(VehicleHash vehicle) {
        int index = hashFunction(vehicle.vehicleID);

    // Print index for debugging
        System.out.println("Vehicle " + vehicle.vehicleID + " is stored at index " + index);

    // Check if the vehicle already exists
        for (VehicleHash v : table[index]) {
            if (v.vehicleID.equals(vehicle.vehicleID)) {
                System.out.println("Error: Vehicle with ID " + vehicle.vehicleID + " already exists.");
                return;
            }
        }

        table[index].add(vehicle);
        System.out.println("Vehicle added successfully: " + vehicle);
}


    // Search for a vehicle by ID
    public VehicleHash search(String vehicleID) {
        int index = hashFunction(vehicleID);
        for (VehicleHash v : table[index]) {
            if (v.vehicleID.equals(vehicleID)) {
                return v;
            }
        }
        return null; // Not found
    }

    // Delete a vehicle by ID
    public boolean delete(String vehicleID) {
        int index = hashFunction(vehicleID);
        for (VehicleHash v : table[index]) {
            if (v.vehicleID.equals(vehicleID)) {
                table[index].remove(v);
                System.out.println(" Vehicle deleted successfully: " + vehicleID);
                return true;
            }
        }
        System.out.println(" Error: Vehicle not found.");
        return false;
    }

    // Display all vehicles in the hash table
    public void displayAll() {
        System.out.println("\n--- Vehicle Hash Table ---");
        for (int i = 0; i < SIZE; i++) {
            System.out.print("Index " + i + ": ");
            if (table[i].isEmpty()) {
                System.out.println("Empty");
            } else {
                for (VehicleHash v : table[i]) {
                    System.out.print(v + " -> ");
                }
                System.out.println("null");
            }
        }
    }

    // Interactive menu for user input
    public static void main(String[] args) {
        VehicleHashTable vehicleTable = new VehicleHashTable();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n Vehicle Management System ");
            System.out.println("1: Add Vehicle");
            System.out.println("2: Search Vehicle");
            System.out.println("3: Delete Vehicle");
            System.out.println("4: Display All Vehicles");
            System.out.println("5: Exit");
            System.out.print("Enter your choice: ");
            
            while (!scanner.hasNextInt()) {
                System.out.println(" Invalid input. Please enter a number between 1-5.");
                System.out.print("Enter your choice: ");
                scanner.next(); // Clear invalid input
            }
            
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Vehicle ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter Model: ");
                    String model = scanner.nextLine();
                    System.out.print("Enter Owner: ");
                    String owner = scanner.nextLine();
                    vehicleTable.insert(new VehicleHash(id, model, owner));
                    break;

                case 2:
                    System.out.print("Enter Vehicle ID to search: ");
                    String searchID = scanner.nextLine();
                    VehicleHash found = vehicleTable.search(searchID);
                    System.out.println(found != null ? "Found: " + found : "Vehicle not found");
                    break;

                case 3:
                    System.out.print("Enter Vehicle ID to delete: ");
                    String deleteID = scanner.nextLine();
                    vehicleTable.delete(deleteID);
                    break;

                case 4:
                    vehicleTable.displayAll();
                    break;

                case 5:
                    System.out.println("Exiting... Thank you! ");
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a number between 1-5.");
            }
        } while (choice != 5);

        scanner.close();
    }
}

