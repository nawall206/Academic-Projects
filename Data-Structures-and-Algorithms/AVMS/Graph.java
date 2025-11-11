import java.util.*;

public class Graph {
    // A map to store the adjacency list, where the key is the location (vertex),
    // and the value is a list of neighbors (connected locations with their travel data)
    private Map<String, List<Edge>> adjacencyList;

    // Constructor initializes the adjacency list
    public Graph() {
        adjacencyList = new HashMap<>();
    }

    // Inner class to represent an Edge (road between locations)
    static class Edge {
        String destination;  // The destination location (neighbor)
        double distance;     // Distance or travel time between the locations

        public Edge(String destination, double distance) {
            this.destination = destination;
            this.distance = distance;
        }

        @Override
        public String toString() {
            return destination + " (" + distance + " km)";
        }
    }

    // Method to add a location (vertex) to the graph
    public void addVertex(String location) {
        try {
            if (location == null || location.trim().isEmpty()) {
                throw new IllegalArgumentException("Location name cannot be null or empty.");
            }
            if (!adjacencyList.containsKey(location)) {
                adjacencyList.put(location, new ArrayList<>());
                System.out.println("Location " + location + " added to the graph.");
            } else {
                System.out.println("Location " + location + " already exists.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Method to add a road (edge) between two locations with distance/travel time
    public void addEdge(String source, String destination, double distance) {
        try {
            if (source == null || destination == null || distance <= 0) {
                throw new IllegalArgumentException("Invalid input for road addition.");
            }
            if (!adjacencyList.containsKey(source)) {
                throw new NoSuchElementException("Source location " + source + " does not exist.");
            }
            if (!adjacencyList.containsKey(destination)) {
                throw new NoSuchElementException("Destination location " + destination + " does not exist.");
            }

            adjacencyList.get(source).add(new Edge(destination, distance));
            adjacencyList.get(destination).add(new Edge(source, distance)); // Assuming undirected graph
            System.out.println("Road added between " + source + " and " + destination + " with distance " + distance + " km.");
        } catch (IllegalArgumentException | NoSuchElementException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Method to retrieve all neighbors of a location
    public List<Edge> getNeighbors(String location) {
        try {
            if (location == null || location.trim().isEmpty()) {
                throw new IllegalArgumentException("Location name cannot be null or empty.");
            }
            if (!adjacencyList.containsKey(location)) {
                throw new NoSuchElementException("Location " + location + " does not exist.");
            }
            return adjacencyList.get(location);
        } catch (IllegalArgumentException | NoSuchElementException e) {
            System.out.println("Error: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    // Method to display the graph
    public void displayGraph() {
        System.out.println("\n--- Road Network ---");
        for (String location : adjacencyList.keySet()) {
            System.out.print(location + " is connected to: ");
            List<Edge> neighbors = adjacencyList.get(location);
            if (neighbors.isEmpty()) {
                System.out.println("No roads.");
            } else {
                for (Edge edge : neighbors) {
                    System.out.print(edge + "  ");
                }
                System.out.println();
            }
        }
    }

    // Method to check if a path exists between source and destination using BFS
    public boolean isPath(String source, String destination) {
        try {
            if (source == null || destination == null) {
                throw new IllegalArgumentException("Source or Destination cannot be null.");
            }
            if (!adjacencyList.containsKey(source)) {
                throw new NoSuchElementException("Source location " + source + " does not exist.");
            }
            if (!adjacencyList.containsKey(destination)) {
                throw new NoSuchElementException("Destination location " + destination + " does not exist.");
            }
            if (source.equals(destination)) {
                return true; // Path exists if source and destination are the same
            }

            Set<String> visited = new HashSet<>();
            Queue<String> queue = new LinkedList<>();
            queue.add(source);
            visited.add(source);

            while (!queue.isEmpty()) {
                String current = queue.poll();
                List<Edge> neighbors = adjacencyList.get(current);
                for (Edge neighbor : neighbors) {
                    if (!visited.contains(neighbor.destination)) {
                        if (neighbor.destination.equals(destination)) {
                            return true;
                        }
                        queue.add(neighbor.destination);
                        visited.add(neighbor.destination);
                    }
                }
            }
            return false; // No path found
        } catch (IllegalArgumentException | NoSuchElementException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    // Main method for interactive menu
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Graph roadNetwork = new Graph();
        int choice = 0; // Initialize choice with a default value

        do {
            try {
                System.out.println("\n--- Road Network Management System ---");
                System.out.println("1: Add Location");
                System.out.println("2: Add Road (Edge)");
                System.out.println("3: Display Neighbors");
                System.out.println("4: Display Entire Road Network");
                System.out.println("5: Check Path Existence (BFS)");
                System.out.println("6: Exit");
                System.out.print("Enter your choice: ");
                
                while (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a number between 1-6.");
                    System.out.print("Enter your choice: ");
                    scanner.next(); // Clear invalid input
                }
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter Location Name: ");
                        String location = scanner.nextLine();
                        roadNetwork.addVertex(location);
                        break;

                    case 2:
                        System.out.print("Enter Source Location: ");
                        String source = scanner.nextLine();
                        System.out.print("Enter Destination Location: ");
                        String destination = scanner.nextLine();
                        System.out.print("Enter Distance/Travel Time (in km): ");
                        double distance = scanner.nextDouble();
                        scanner.nextLine(); // Consume newline
                        roadNetwork.addEdge(source, destination, distance);
                        break;

                    case 3:
                        System.out.print("Enter Location to see its neighbors: ");
                        String loc = scanner.nextLine();
                        List<Edge> neighbors = roadNetwork.getNeighbors(loc);
                        if (neighbors.isEmpty()) {
                            System.out.println("No neighbors or location doesn't exist.");
                        } else {
                            System.out.println("Neighbors of " + loc + ": ");
                            for (Edge edge : neighbors) {
                                System.out.println(edge);
                            }
                        }
                        break;

                    case 4:
                        roadNetwork.displayGraph();
                        break;

                    case 5:
                        System.out.print("Enter Source Location: ");
                        String start = scanner.nextLine();
                        System.out.print("Enter Destination Location: ");
                        String end = scanner.nextLine();
                        boolean pathExists = roadNetwork.isPath(start, end);
                        System.out.println(pathExists ? "Path exists between " + start + " and " + end : "No path exists.");
                        break;

                    case 6:
                        System.out.println("Exiting... Thank you!");
                        break;

                    default:
                        System.out.println("Invalid choice. Please enter a number between 1-6.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            }
        } while (choice != 6);

        scanner.close();
    }
}
