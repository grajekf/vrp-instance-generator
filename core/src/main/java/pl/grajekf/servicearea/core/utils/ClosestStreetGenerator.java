package pl.grajekf.servicearea.core.utils;

import com.graphhopper.routing.util.EdgeFilter;
import com.graphhopper.storage.index.QueryResult;
import com.graphhopper.util.EdgeIteratorState;
import pl.grajekf.servicearea.core.street.StreetInfo;
import pl.grajekf.servicearea.core.graphhopper.GraphHopperWithOsmId;
import pl.grajekf.servicearea.core.street.StreetSegment;

import java.util.Map;

public class ClosestStreetGenerator {

    private Map<Long, StreetInfo.StreetType> streetTypeMap;
    private GraphHopperWithOsmId hopper;

    public ClosestStreetGenerator(GraphHopperWithOsmId hopper, Map<Long, StreetInfo.StreetType> streetTypeMap) {
        this.streetTypeMap = streetTypeMap;
        this.hopper = hopper;
    }

    public StreetInfo getClosestStreet(double latitude, double longitude) {
        QueryResult qr = hopper.getLocationIndex().findClosest(latitude, longitude, EdgeFilter.ALL_EDGES);
        EdgeIteratorState edge = qr.getClosestEdge();
        int ghId = edge.getEdge();
        long osmId = hopper.getOSMWay(ghId);
        StreetSegment segment = new StreetSegment(edge.getBaseNode(), edge.getAdjNode());
        return new StreetInfo(osmId, edge.getName(), streetTypeMap.get(osmId), edge.fetchWayGeometry(3), segment);
    }
}
