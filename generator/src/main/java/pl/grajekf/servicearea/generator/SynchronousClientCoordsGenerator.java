package pl.grajekf.servicearea.generator;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import org.apache.commons.math3.distribution.UniformRealDistribution;
import pl.grajekf.servicearea.generator.model.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

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


        List<Double> exptectedCustomerCounts = addresses.stream().map(street -> {
            return clientCount * street.getLength() / streetLengthSum;
        }).collect(Collectors.toList());

        int currentCustomerCount = 0;

        List<Integer> realCustomerCounts = exptectedCustomerCounts.stream()
                .map(count -> count.intValue())
                .collect(Collectors.toList());

        List<StreetWithPriority> indicesWithPriorities = new ArrayList<>();

        for(int i = 0; i < realCustomerCounts.size(); i++) {
            currentCustomerCount += realCustomerCounts.get(i);
            indicesWithPriorities.add(new StreetWithPriority(i, exptectedCustomerCounts.get(i) - realCustomerCounts.get(i)));
        }

        PriorityQueue<StreetWithPriority> streetPriorityQueue = new PriorityQueue<>(indicesWithPriorities);

        while (currentCustomerCount < clientCount) {
            StreetWithPriority street = streetPriorityQueue.poll();
            realCustomerCounts.set(street.getStreetIndex(), realCustomerCounts.get(street.getStreetIndex()) + 1);

            street.decreasePriority();
            streetPriorityQueue.add(street);
            currentCustomerCount++;
        }



        for(int i = 0; i < addresses.size(); i++) {
            int count = realCustomerCounts.get(i);
            Geometry street = addresses.get(i);

            List<Location> locations = generateLocationsForStreet(street, count);
            result.addAll(locations);
        }

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

    private class StreetWithPriority implements Comparable<StreetWithPriority>
    {
        private final int streetIndex;
        private double priority;

        public StreetWithPriority(int streetIndex, double priority)
        {

            this.streetIndex = streetIndex;
            this.priority = priority;
        }

        public int getStreetIndex() {
            return streetIndex;
        }

        public double getPriority() {
            return priority;
        }

        public void decreasePriority() {
            priority -= 1.0;
        }

        @Override
        public int compareTo(StreetWithPriority streetWithPriority) {
            return -Double.compare(this.priority, streetWithPriority.priority);
        }
    }
}
