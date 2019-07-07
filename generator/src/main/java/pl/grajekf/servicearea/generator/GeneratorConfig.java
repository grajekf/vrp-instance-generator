package pl.grajekf.servicearea.generator;

import com.vividsolutions.jts.geom.Envelope;
import pl.grajekf.servicearea.core.Node;

import java.util.List;

public class GeneratorConfig {
    public GeneratorConfig(int vehicleCount, int vehicleCapacity, int dumpOpenTimeSeconds, int dumpCloseTimeSeconds, int vehicleStartTimeSeconds, Node startNode, Envelope bbox, int clientCount, List<String> streets, String city, double gammaShape, double gammaRate) {
        this.vehicleCount = vehicleCount;
        this.vehicleCapacity = vehicleCapacity;
        this.startNode = startNode;
        this.bbox = bbox;
        this.clientCount = clientCount;
        this.streets = streets;
        this.city = city;
        this.gammaShape = gammaShape;
        this.gammaRate = gammaRate;
    }

    public int getVehicleCount() {
        return vehicleCount;
    }

    public int getVehicleCapacity() {
        return vehicleCapacity;
    }

    public Node getStartNode() {
        return startNode;
    }

    public Envelope getBbox() {
        return bbox;
    }

    public int getClientCount() {
        return clientCount;
    }

    public List<String> getStreets() {
        return streets;
    }

    public String getCity() {
        return city;
    }

    public double getGammaShape() {
        return gammaShape;
    }

    public double getGammaRate() {
        return gammaRate;
    }

    private int vehicleCount;
    private int vehicleCapacity;
    private Node startNode;
    private Envelope bbox;
    private int clientCount;
    private List<String> streets;
    private final String city;
    private double gammaShape;
    private double gammaRate;
}
