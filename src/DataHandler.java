import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataHandler {
	
    public static List<String> getRestaurants()
    {
    	//return Arrays.asList("R1", "R2", "R3");
    	return Arrays.asList("R1", "R2");
    }
    
    public static List<String> getCustomers()
    {
      //return  Arrays.asList("C1", "C2", "C3");
    	return  Arrays.asList("C1", "C2");
    }
    
    public static Map<String, double[]> getLocations() {
    	
        Map<String, double[]> locations = new HashMap<>();
        
        //Here we can take actual user input from console.
        locations.put("Aman", new double[]{12.9352, 77.6245}); // Starting Point
        locations.put("R1", new double[]{11.1000, 76.1100});
        locations.put("R2", new double[]{12.1000, 76.1100});
        //locations.put("R3", new double[]{11.1200, 7.1100});
        locations.put("C1", new double[]{12.1300, 77.1200});
        locations.put("C2", new double[]{12.1400, 77.1300});
       // locations.put("C3", new double[]{12.1600, 77.1400});
        
        return locations;
    }

    public static Map<String, Integer> getPreparationTimes() {
        Map<String, Integer> prepTime = new HashMap<>();
        
        prepTime.put("R1", 12);
        prepTime.put("R2", 5);
       // prepTime.put("R3", 15);
        
        return prepTime;
    }


}
