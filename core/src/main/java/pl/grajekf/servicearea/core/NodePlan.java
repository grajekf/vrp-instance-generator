package pl.grajekf.servicearea.core;


import org.apache.commons.io.FilenameUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NodePlan {

    public NodePlan() {
        this.dayPlan = new ArrayList<>();
    }
    public NodePlan(String name,
                    Node startNode,
                    int vehicleCapacity,
                    int vehicleCount,
                    double dumpOpenTimeSeconds,
                    double dumpCloseTimeSeconds,
                    double vehicleStartTimeSeconds) {
        this.name = name;
        this.vehicleStartTimeSeconds = vehicleStartTimeSeconds;
        this.dayPlan = new ArrayList<>();
        this.startNode = startNode;
        this.vehicleCapacity = vehicleCapacity;
        this.vehicleCount = vehicleCount;
        this.dumpOpenTimeSeconds = dumpOpenTimeSeconds;
        this.dumpCloseTimeSeconds = dumpCloseTimeSeconds;
    }

    public NodePlan(List<NodeWithDemand> dayPlan) {
        this.dayPlan = dayPlan;
    }

    public void addNode(NodeWithDemand node) {
        dayPlan.add(node);
    }

    public void addNodes(List<NodeWithDemand> nodes) {
        for(NodeWithDemand n: nodes) {
            this.addNode(n);
        }
    }

    public void setNodes(List<NodeWithDemand> nodes) {
        this.dayPlan = nodes;
    }

    public void multiplyCapacity(double factor) {
        this.vehicleCapacity *= factor;
    }

    public List<Node> getNodes() {
        return Stream.concat(dayPlan.stream().map(NodeWithDemand::getInner), Stream.of(startNode)).collect(Collectors.toList());
    }

    public List<NodeWithDemand> getDayPlan() {
        return dayPlan;
    }

    public Node getStartNode() {
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

    public String getName() {
        return name;
    }

    public String getSolutionName() {
        return FilenameUtils.removeExtension(getName()) + ".sol";
    }

    private List<NodeWithDemand> dayPlan;
    private Node startNode;
    private int vehicleCapacity;
    private int vehicleCount;
    private double dumpOpenTimeSeconds;
    private double dumpCloseTimeSeconds;
    private String name;
    private double vehicleStartTimeSeconds;


}
