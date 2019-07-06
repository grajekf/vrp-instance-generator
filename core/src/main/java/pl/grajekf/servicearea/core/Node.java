package pl.grajekf.servicearea.core;

import pl.grajekf.servicearea.core.street.StreetInfo;

import java.util.Objects;

public class Node implements Entity<Long> {

    public Node() {

    }

    public Node(Long id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Node(Long id, double latitude, double longitude, StreetInfo street) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.street = street;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double[] getGeoJson() {
        return new double[] {
                getLongitude(), getLatitude()
        };
    }

    public StreetInfo getStreet() {
        return street;
    }

    public void setStreet(StreetInfo street) {
        this.street = street;
    }

    public Node copy() {
        Node result =  new Node(id, latitude, longitude);
        result.setStreet(street);

        return result;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Double.compare(node.latitude, latitude) == 0 &&
                Double.compare(node.longitude, longitude) == 0 &&
                Objects.equals(id, node.id) &&
                Objects.equals(street, node.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, latitude, longitude, street);
    }

    private Long id;
    private double latitude;
    private double longitude;
    private StreetInfo street;
}
