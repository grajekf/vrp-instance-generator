package pl.grajekf.servicearea.core;

import java.io.Serializable;

public class TransportCost implements Serializable {
    public TransportCost(double distance, double time) {
        this.distance = distance;
        this.time = time;
    }

    public double getDistance() {
        return distance;
    }

    public double getTime() {
        return time;
    }

    private double distance;
    private double time;
}
