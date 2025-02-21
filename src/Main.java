import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
    	
    	List<String> restaurants = DataHandler.getRestaurants();
        List<String> customers = DataHandler.getCustomers();
        
        // Initialize locations, preparation times, and stops
        Map<String, double[]> locations = DataHandler.getLocations();
        Map<String, Integer> prepTime = DataHandler.getPreparationTimes();
        
        // Find the best route
        String bestRoute = PathOptimizer.findBestRoute(locations, prepTime, restaurants, customers);
    
        System.out.println("Best Route is: " + bestRoute);
    }
}
