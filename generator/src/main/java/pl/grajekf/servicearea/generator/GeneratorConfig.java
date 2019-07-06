package pl.grajekf.servicearea.generator;

import com.vividsolutions.jts.geom.Envelope;
import pl.grajekf.servicearea.core.Node;

import java.util.List;

public class GeneratorConfig {
    public GeneratorConfig(int vehicleCount, int vehicleCapacity, int dumpOpenTimeSeconds, int dumpCloseTimeSeconds, int vehicleStartTimeSeconds, Node startNode, Envelope bbox, int clientCount, List<String> streets, String district, double gammaShape, double gammaRate) {
        this.vehicleCount = vehicleCount;
        this.vehicleCapacity = vehicleCapacity;
        this.dumpOpenTimeSeconds = dumpOpenTimeSeconds;
        this.dumpCloseTimeSeconds = dumpCloseTimeSeconds;
        this.vehicleStartTimeSeconds = vehicleStartTimeSeconds;
        this.startNode = startNode;
        this.bbox = bbox;
        this.clientCount = clientCount;
        this.streets = streets;
        this.district = district;
        this.gammaShape = gammaShape;
        this.gammaRate = gammaRate;
    }

    public int getVehicleCount() {
        return vehicleCount;
    }

    public int getVehicleCapacity() {
        return vehicleCapacity;
    }

    public int getDumpOpenTimeSeconds() {
        return dumpOpenTimeSeconds;
    }

    public int getDumpCloseTimeSeconds() {
        return dumpCloseTimeSeconds;
    }

    public int getVehicleStartTimeSeconds() {
        return vehicleStartTimeSeconds;
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

    public String getDistrict() {
        return district;
    }

    public double getGammaShape() {
        return gammaShape;
    }

    public double getGammaRate() {
        return gammaRate;
    }

    private int vehicleCount;
    private int vehicleCapacity;
    private int dumpOpenTimeSeconds;
    private int dumpCloseTimeSeconds;
    private int vehicleStartTimeSeconds;
    private Node startNode;
    private Envelope bbox;
    private int clientCount;
    private List<String> streets;
    private final String district;
    private double gammaShape;
    private double gammaRate;
}
