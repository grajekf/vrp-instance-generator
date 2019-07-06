package pl.grajekf.servicearea.core;

import pl.grajekf.servicearea.core.street.StreetInfo;

import java.util.Objects;

public class NodeWithDemand extends Node {

    public NodeWithDemand(Node inner, Integer demand) {
        super(inner.getId(), inner.getLatitude(), inner.getLongitude());
        this.demand = demand;
        this.inner = inner;
        setStreet(inner.getStreet());
    }

    @Override
    public double getLatitude() {
        return inner.getLatitude();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        NodeWithDemand that = (NodeWithDemand) o;

        return Double.compare(that.demand, demand) == 0 &&
                Objects.equals(inner, that.inner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), demand, inner);
    }

    @Override
    public double getLongitude() {
        return inner.getLongitude();
    }

    @Override
    public StreetInfo getStreet() {
        return inner.getStreet();
    }

    @Override
    public void setStreet(StreetInfo street) {
        super.setStreet(street);
        inner.setStreet(street);
    }

    @Override
    public Long getId() {
        return inner.getId();
    }

    @Override
    public NodeWithDemand copy() {
        return new NodeWithDemand(inner.copy(), demand);
    }

    public Integer getDemand() {
        return demand;
    }

    public Node getInner() {
        return inner;
    }

    private Integer demand;
    private Node inner;

}
