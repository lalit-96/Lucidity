public class LocationUtils {
    private static final double EARTH_RADIUS = 5000.0;
    private static final double SPEED = 20.0; // km/h

    // Calculate Haversine distance between two coordinates
    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(lat1) * Math.cos(lat2) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }

    // Convert distance to travel time
    public static double travelTime(double lat1, double lon1, double lat2, double lon2) {
        return (haversine(lat1, lon1, lat2, lon2) / SPEED) * 60; // Convert to minutes
    }
}
