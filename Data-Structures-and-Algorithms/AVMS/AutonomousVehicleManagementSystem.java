import java.util.Scanner;
import java.util.List;

public class AutonomousVehicleManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Graph graph = new Graph();
        VehicleHashTable vehicleHashTable = new VehicleHashTable();
        VehicleManagementSystem vehicleManagementSystem = new VehicleManagementSystem();

        while (true) {
            System.out.println("\n--- Autonomous Vehicle Management System\n" + //
                                "(AVMS) ---");
            System.out.println("1. Road Network ");
            System.out.println("2. Autonomous Vehicle Details");
            System.out.println("3. Attributes of Autonomous vehicle");
            System.out.println("4. Vehicle Sorting and Searching");
            System.out.println("0. Exit");

            if (!scanner.hasNextInt()) { // Validate input
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume invalid input
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> graphMenu(graph, scanner);
                case 2 -> vehicleHashTableMenu(vehicleHashTable, scanner);
                case 3 -> vehicleManagementSystem.run(scanner); // Pass the scanner object to the run method
                case 4 -> vehicleSorterMenu(vehicleManagementSystem, scanner);
                case 0 -> {
                    System.out.println("Exiting the system. Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void graphMenu(Graph graph, Scanner scanner) {
        while (true) {
            System.out.println("\n--- Road Network ---");
            System.out.println("1. Add Location");
            System.out.println("2. Add Road");
            System.out.println("3. Display Neighbors");
            System.out.println("4. Display Entire Road Network");
            System.out.println("5. Check Path Existence");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter location name: ");
                    String location = scanner.nextLine();
                    graph.addVertex(location);
                }
                case 2 -> {
                    System.out.print("Enter source: ");
                    String source = scanner.nextLine();
                    System.out.print("Enter destination: ");
                    String destination = scanner.nextLine();
                    System.out.print("Enter distance (km): ");
                    double distance = scanner.nextDouble();
                    graph.addEdge(source, destination, distance);
                }
                case 3 -> {
                    System.out.print("Enter location: ");
                    String location = scanner.nextLine();
                    System.out.println(graph.getNeighbors(location));
                }
                case 4 -> graph.displayGraph();
                case 5 -> {
                    System.out.print("Enter source location: ");
                    String source = scanner.nextLine();
                    System.out.print("Enter destination location: ");
                    String destination = scanner.nextLine();
                    System.out.println(graph.isPath(source, destination) ? "Path exists." : "No path found.");
                }
                case 0 -> {
                    return; // Exit to main menu
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void vehicleHashTableMenu(VehicleHashTable vehicleHashTable, Scanner scanner) {
        while (true) {
            System.out.println("\n--- Autonomous Vehicle Details ---");
            System.out.println("1. Add Vehicle");
            System.out.println("2. Search Vehicle");
            System.out.println("3. Delete Vehicle");
            System.out.println("4. Display All Vehicles");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Vehicle ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter Model: ");
                    String model = scanner.nextLine();
                    System.out.print("Enter Owner: ");
                    String owner = scanner.nextLine();
                    vehicleHashTable.insert(new VehicleHash(id, model, owner));
                }
                case 2 -> {
                    System.out.print("Enter Vehicle ID to search: ");
                    String id = scanner.nextLine();
                    VehicleHash vehicle = vehicleHashTable.search(id);
                    System.out.println(vehicle != null ? "Found: " + vehicle : "Vehicle not found.");
                }
                case 3 -> {
                    System.out.print("Enter Vehicle ID to delete: ");
                    String id = scanner.nextLine();
                    vehicleHashTable.delete(id);
                }
                case 4 -> vehicleHashTable.displayAll();
                case 0 -> {
                    return; // Exit to main menu
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void vehicleSorterMenu(VehicleManagementSystem vehicleManagementSystem, Scanner scanner) {
        while (true) {
            System.out.println("\n--- Vehicle Sorting and Searching ---");
            System.out.println("1. Sort by Distance (Heap Sort)");
            System.out.println("2. Sort by Battery Level (Quick Sort)");
            System.out.println("3. Find Nearest Vehicle");
            System.out.println("4. Find Vehicle with Highest Battery");
            System.out.println("5. Display All Vehicles");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextInt()) { // Validate input
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume invalid input
                continue;
            }
    
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
    
            // Get the list of vehicles from VehicleManagementSystem
            List<Vehicle> vehicles = vehicleManagementSystem.getVehicles();
            
            switch (choice) {
                case 1 -> {
                    VehicleSorter.heapSort(vehicles);
                    System.out.println("Vehicles sorted by Distance:");
                    VehicleSorter.printVehicles(vehicles);
                }
                case 2 -> {
                    VehicleSorter.quickSort(vehicles, 0, vehicles.size() - 1);
                    System.out.println("Vehicles sorted by Battery Level:");
                    VehicleSorter.printVehicles(vehicles);
                }
                case 3 -> {
                    Vehicle nearest = VehicleSorter.findNearestVehicle(vehicles);
                    System.out.println(nearest != null ? "Nearest Vehicle: " + nearest : "No vehicles available.");
                }
                case 4 -> {
                    Vehicle highestBattery = VehicleSorter.findVehicleWithHighestBattery(vehicles);
                    System.out.println(highestBattery != null ? "Vehicle with Highest Battery: " + highestBattery : "No vehicles available.");
                }
                case 5 -> VehicleSorter.printVehicles(vehicles);
                case 0 -> {
                    return; // Exit to main menu
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}