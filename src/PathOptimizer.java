import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PathOptimizer {
    private static double[][] distance;
    private static Map<String, Integer> stopToIndex;
    
    public static String findBestRoute(Map<String, double[]> locations, Map<String, Integer> prepTime,
                                       List<String> restaurants, List<String> customers) {
        List<String> stops = new ArrayList<>();
        stops.add("Aman"); // Start location
        stops.addAll(restaurants);
        stops.addAll(customers);
        
        int N = stops.size();
        stopToIndex = new HashMap<>();
        for (int i = 0; i < N; i++) stopToIndex.put(stops.get(i), i);

        // Compute pairwise travel times
        distance = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                distance[i][j] = LocationUtils.travelTime(
                    locations.get(stops.get(i))[0], locations.get(stops.get(i))[1],
                    locations.get(stops.get(j))[0], locations.get(stops.get(j))[1]
                );
            }
        }
        /* Process all possible routes, mask is a bitmask representing visiited locations.
           dp[mask][i] stores min time to visit mask ending at i
         */
        double[][] dp = new double[1 << N][N];
        for (double[] row : dp)
        	Arrays.fill(row, Double.MAX_VALUE);
        dp[1][0] = 0; // Start at Aman

        // Track parent for path reconstruction
        int[][] parent = new int[1 << N][N];

        for (int mask = 1; mask < (1 << N); ++mask) {
            for (int last = 0; last < N; last++) {
                if ((mask & (1 << last)) == 0)
                	continue;

                for (int next = 0; next < N; ++next) {
                    if ((mask & (1 << next)) != 0) 
                    	continue; // Already visited
                    if (!isValidDelivery(mask, stops.get(last), stops.get(next), restaurants, customers)) 
                    	continue;

                    int newMask = mask | (1 << next);
                    double arrivalTime = dp[mask][last] + distance[last][next];
                    double waitTime = 0;

                    // If visiting a restaurant, ensure food is ready before pickup
                    if (prepTime.containsKey(stops.get(next))) {
                        waitTime = Math.max(0, prepTime.get(stops.get(next)) - arrivalTime);
                    }

                    double newTime = arrivalTime + waitTime;

                    if (newTime < dp[newMask][next]) {
                        dp[newMask][next] = newTime;
                        parent[newMask][next] = last;
                    }
                }
            }
        }

        // Find the best end point
        double bestTime = Double.MAX_VALUE;
        int lastStop = -1;
        int finalMask = (1 << N) - 1;
        for (int i = 1; i < N; i++) {
            if (dp[finalMask][i] < bestTime) {
                bestTime = dp[finalMask][i];
                lastStop = i;
            }
        }

        // Backtrack to get the optimal route
        List<String> bestRoute = new ArrayList<>();
        int mask = finalMask;
        while (lastStop != 0) {
            bestRoute.add(stops.get(lastStop));
            int temp = parent[mask][lastStop];
            mask ^= (1 << lastStop);
            lastStop = temp;
        }
        bestRoute.add("Aman");
        Collections.reverse(bestRoute);

        return String.join(" -> ", bestRoute) +"\n (Total Time: " + bestTime + " minutes)";
    }

    // Ensures each Restaurant (R) must be visited before its corresponding Cusotmer (C).
    private static boolean isValidDelivery(int mask, String last, String next, List<String> restaurants, List<String> customers) {
        for (int i = 0; i < customers.size(); i++) {
            String pickup = restaurants.get(i);
            String dropoff = customers.get(i);

            if (next.equals(dropoff) && (mask & (1 << stopToIndex.get(pickup))) == 0) {
                return false; // Cannot visit customer without visiting its restaurant
            }
        }
        return true;
    }

}

