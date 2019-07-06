package pl.grajekf.servicearea.generator;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import org.apache.commons.math3.distribution.UniformRealDistribution;
import pl.grajekf.servicearea.generator.model.Location;

import java.util.ArrayList;
import java.util.List;

public class SynchronousClientCoordsGenerator implements ClientCoordsGenerator {

    private final double dist = 0.0005396; //constant that is approximatively 60m in Earth coordinate
    private final UniformRealDistribution distribution;

    public SynchronousClientCoordsGenerator() {
        this.distribution = new UniformRealDistribution(-dist, dist);
    }

    @Override
    public List<Location> generateClientCoords(int clientCount, List<Geometry> addresses) {
        List<Location> result = new ArrayList<>();

        double streetLengthSum = addresses.stream().mapToDouble(a -> a.getLength()).sum();



        addresses.forEach(street -> {
            int count = (int)Math.round(clientCount * street.getLength() / streetLengthSum);
            List<Location> locations = generateLocationsForStreet(street, count);

            result.addAll(locations);
        });

        return result;
    }

    private List<Location> generateLocationsForStreet(Geometry street, int count) {
        List<Location> locations = new ArrayList<>();

        double step = street.getLength() / count;
        double distance = step / 2;

        while(distance < street.getLength()) {
            Location streetPoint = getStreetIntermediatePoint(street, distance);
            double latRand = distribution.sample();
            double lonRand = distribution.sample();

            locations.add(new Location(streetPoint.getLatitude() + latRand, streetPoint.getLongitude() + lonRand));
            distance += step;
        }

        return locations;
    }

    private Location getStreetIntermediatePoint(Geometry street, double distance) {
        if(distance <0 || distance > street.getLength())
            throw new IllegalArgumentException("Wrong distance!");
        int i = 0;
        Coordinate[] coordinates = street.getCoordinates();
        for(; i < street.getNumPoints() - 1; i++) {
            Coordinate from = coordinates[i];
            Coordinate to = coordinates[i + 1];
            double fromToDistance = from.distance(to);
            if(distance > fromToDistance) {
                distance -= fromToDistance;
            } else {
                break;
            }
        }

        Coordinate from = coordinates[i];
        Coordinate to = coordinates[i + 1];

        double coeff = distance / from.distance(to);
        return new Location(from.y + (to.y - from.y) * coeff, from.x + (to.x - from.x) * coeff);
    }
}
