public class GraphTest {
    public static void main(String[] args) {
        Graph graph = new Graph();

        System.out.println("=== Test Case 1: Add Locations ===");
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("A"); // Should say already exists

        System.out.println("\n=== Test Case 2: Add Roads ===");
        graph.addEdge("A", "B", 10.5); // Valid
        graph.addEdge("A", "C", 5.0);  // Valid
        graph.addEdge("B", "C", 2.5);  // Valid
        graph.addEdge("A", "D", 4.0);  // D doesn't exist

        System.out.println("\n=== Test Case 3: Neighbors of A ===");
        for (Graph.Edge edge : graph.getNeighbors("A")) {
            System.out.println(edge);
        }

        System.out.println("\n=== Test Case 4: Display Graph ===");
        graph.displayGraph();

        System.out.println("\n=== Test Case 5: Path Existence ===");
        System.out.println("Path from A to C: " + graph.isPath("A", "C")); // true
        System.out.println("Path from B to A: " + graph.isPath("B", "A")); // true
        System.out.println("Path from A to D: " + graph.isPath("A", "D")); // false

        System.out.println("\n=== Test Case 6: Error Cases ===");
        graph.addVertex(""); // Invalid
        graph.addEdge("A", "B", -3); // Invalid distance
        graph.getNeighbors("Z"); // Z doesn't exist
    }
}
