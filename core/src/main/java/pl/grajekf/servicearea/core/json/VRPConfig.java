package pl.grajekf.servicearea.core.json;

import pl.grajekf.servicearea.core.Node;


public class VRPConfig {
    public VRPConfig() {

    }

    public VRPConfig(Node[] nodes,
                     Long startNode,
                     int vehicleCapacity,
                     int vehicleCount,
                     NodeWithDemandData[] plan) {
        this.nodes = nodes;
        this.startNode = startNode;
        this.vehicleCapacity = vehicleCapacity;
        this.vehicleCount = vehicleCount;
        this.plan = plan;
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

    public NodeWithDemandData[] getPlan() {
        return plan;
    }


    private Node[] nodes;
    private Long startNode;
    private int vehicleCapacity;
    private int vehicleCount;
    private NodeWithDemandData[] plan;

}
