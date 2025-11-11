# Autonomous Vehicle Management System (AVMS)

The Autonomous Vehicle Management System (AVMS) is a Java project built to simulate the management and routing of autonomous vehicles across a connected road network using Data Structures and Algorithms


## Project Overview

The system models vehicles, routes, and cities using fundamental data structures such as Graphs Hash Tables and Priority Queues (Heaps)

It supports efficient vehicle management, pathfinding, and sorting operations — demonstrating the practical use of algorithms taught in **Data Structures & Algorithms.


##  Features

- Graph Representation**
  - Each city or intersection is represented as a node.
  - Edges represent roads with distance or travel time as weights.
  - Implemented using **Adjacency List** for memory efficiency.

- Pathfinding Algorithms
  - Breadth-First Search (BFS for route discovery.
  - Dijkstra’s Algorithm for finding the shortest path between vehicles and destinations.

- Hash Table Implementation
  - Vehicles stored in a custom Hash Table for quick lookup by ID or license.
  - Handles collisions using chaining or open addressing

- Sorting
  - Vehicles sorted by energy level, distance, or travel time using **HeapSort** or **QuickSort**.

- **OOP Design**
  - Classes for `Vehicle`, `Route`, `Graph`, `VehicleManager`, etc.
  - Encapsulation and modular structure for clarity and reusability.


##  Data Structures Used
| Concept | Purpose |
|----------|----------|
| Graph (Adjacency List) | Represents road connections |
| Hash Table | Stores vehicle data efficiently |
| Priority Queue (Heap) | Used in Dijkstra’s Algorithm |
| Queue | For BFS traversal |
| Array / List | For managing vehicle collections |

---

##  Algorithms Implemented
- Breadth-First Search (BFS)
- Dijkstra’s Shortest Path
- HeapSort / QuickSort
- Hashing with Collision Resolution

---

##  Tools & Technologies
- **Language:** Java  
- **IDE:** IntelliJ IDEA / VS Code  
- **Data Files:** `.csv` for vehicle and route data  

---

---

## How to Run
1. Open the project in IntelliJ IDEA or VS Code.  
2. Compile all files:
   ```bash
   javac *.java
java Main

