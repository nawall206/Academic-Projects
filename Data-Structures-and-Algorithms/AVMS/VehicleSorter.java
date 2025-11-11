import java.util.List;

public class VehicleSorter {

    // Heapsort to sort vehicles by Distance_to_Destination in ascending order.
    // This implementation builds a max-heap then extracts the maximum repeatedly.
    public static void heapSort(List<Vehicle> vehicles) {
        int numItems = vehicles.size();
        
        // Build the max-heap (heapify phase)
        for (int i = (numItems / 2) - 1; i >= 0; i--) {
            trickleDown(vehicles, numItems, i);
        }
        
        // Sort the array in-place (extraction phase)
        for (int i = numItems - 1; i > 0; i--) {
            // Swap the root (largest) with the element at index i
            Vehicle temp = vehicles.get(0);
            vehicles.set(0, vehicles.get(i));
            vehicles.set(i, temp);
            
            // Restore the max-heap property on the reduced heap
            trickleDown(vehicles, i, 0);
        }
    }
    
    // Trickle down method (similar to heapify) to maintain the max-heap property.
    private static void trickleDown(List<Vehicle> vehicles, int numItems, int i) {
        int largest = i;  // Assume the root is the largest
        int left = 2 * i + 1;  // Left child index
        int right = 2 * i + 2; // Right child index
        
        // If the left child exists and is greater than the current largest, update largest.
        if (left < numItems && vehicles.get(left).getDistanceToDestination() > vehicles.get(largest).getDistanceToDestination()) {
            largest = left;
        }
        
        // If the right child exists and is greater than the current largest, update largest.
        if (right < numItems && vehicles.get(right).getDistanceToDestination() > vehicles.get(largest).getDistanceToDestination()) {
            largest = right;
        }
        
        // If the largest is not the root, swap and continue trickling down.
        if (largest != i) {
            Vehicle swap = vehicles.get(i);
            vehicles.set(i, vehicles.get(largest));
            vehicles.set(largest, swap);
            
            trickleDown(vehicles, numItems, largest);
        }
    }
    

    // Quicksort to sort vehicles by Battery_Level in descending order
    public static void quickSort(List<Vehicle> vehicles, int low, int high) {
        if (low < high) {
            int pi = partition(vehicles, low, high);
            quickSort(vehicles, low, pi - 1);
            quickSort(vehicles, pi + 1, high);
        }
    }

    private static int partition(List<Vehicle> vehicles, int low, int high) {
        Vehicle pivot = vehicles.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (vehicles.get(j).getBatteryLevel() > pivot.getBatteryLevel()) {
                i++;
                Vehicle temp = vehicles.get(i);
                vehicles.set(i, vehicles.get(j));
                vehicles.set(j, temp);
            }
        }

        Vehicle temp = vehicles.get(i + 1);
        vehicles.set(i + 1, vehicles.get(high));
        vehicles.set(high, temp);
        return i + 1;
    }

    // Find the vehicle closest to its destination
    public static Vehicle findNearestVehicle(List<Vehicle> vehicles) {
        if (vehicles.isEmpty()) return null;

        Vehicle nearest = vehicles.get(0);
        for (Vehicle v : vehicles) {
            if (v.getDistanceToDestination() < nearest.getDistanceToDestination()) {
                nearest = v;
            }
        }
        return nearest;
    }

    // Find the vehicle with the highest battery level
    public static Vehicle findVehicleWithHighestBattery(List<Vehicle> vehicles) {
        if (vehicles.isEmpty()) return null;

        Vehicle highestBattery = vehicles.get(0);
        for (Vehicle v : vehicles) {
            if (v.getBatteryLevel() > highestBattery.getBatteryLevel()) {
                highestBattery = v;
            }
        }
        return highestBattery;
    }

    public static void printVehicles(List<Vehicle> vehicles) {
    if (vehicles.isEmpty()) {
        System.out.println("No vehicles available.");
        return;
    }
    for (Vehicle v : vehicles) {
        System.out.println(v);
    }
}
}
