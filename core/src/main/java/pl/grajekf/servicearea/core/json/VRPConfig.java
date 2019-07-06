package pl.grajekf.servicearea.core.json;

import pl.grajekf.servicearea.core.Node;


public class VRPConfig {
    public VRPConfig() {

    }

    public VRPConfig(Node[] nodes,
                     Long startNode,
                     int vehicleCapacity,
                     int vehicleCount,
                     double dumpOpenTimeSeconds,
                     double dumpCloseTimeSeconds,
                     double vehicleStartTimeSeconds,
                     NodeWithDemandData[] plan,
                     String costMatrixFile) {
        this.nodes = nodes;
        this.startNode = startNode;
        this.vehicleCapacity = vehicleCapacity;
        this.vehicleCount = vehicleCount;
        this.dumpOpenTimeSeconds = dumpOpenTimeSeconds;
        this.dumpCloseTimeSeconds = dumpCloseTimeSeconds;
        this.vehicleStartTimeSeconds = vehicleStartTimeSeconds;
        this.plan = plan;
        this.costMatrixFile = costMatrixFile;
    }

    public Node[] getNodes() {
        return nodes;
    }

    public Long getStartNode() {
        return startNode;
    }

    public int getVehicleCapacity() {
        return vehicleCapacity;
    }

    public int getVehicleCount() {
        return vehicleCount;
    }

    public double getDumpOpenTimeSeconds() {
        return dumpOpenTimeSeconds;
    }

    public double getDumpCloseTimeSeconds() {
        return dumpCloseTimeSeconds;
    }

    public double getVehicleStartTimeSeconds() {
        return vehicleStartTimeSeconds;
    }

    public NodeWithDemandData[] getPlan() {
        return plan;
    }

    public String getCostMatrixFile() {
        return costMatrixFile;
    }

    private Node[] nodes;
    private Long startNode;
    private int vehicleCapacity;
    private int vehicleCount;
    private double dumpOpenTimeSeconds;
    private double dumpCloseTimeSeconds;
    private double vehicleStartTimeSeconds;
    private NodeWithDemandData[] plan;
    private String costMatrixFile;

}
