package pl.grajekf.servicearea.core.json;

public class NodeWithDemandData {
    public NodeWithDemandData() {

    }
    public NodeWithDemandData(Long node, Integer demand) {
        this.node = node;
        this.demand = demand;
    }

    public Long getNode() {
        return node;
    }

    public Integer getDemand() {
        return demand;
    }

    private Long node;
    private Integer demand;
}
