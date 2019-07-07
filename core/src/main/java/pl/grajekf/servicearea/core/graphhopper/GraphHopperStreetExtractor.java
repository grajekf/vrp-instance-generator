package pl.grajekf.servicearea.core.graphhopper;

import com.graphhopper.routing.util.EdgeFilter;
import com.graphhopper.storage.NodeAccess;
import com.graphhopper.storage.index.QueryResult;
import com.graphhopper.util.DepthFirstSearch;
import com.graphhopper.util.EdgeIteratorState;
import com.graphhopper.util.shapes.BBox;
import com.graphhopper.util.shapes.GHPoint;
import com.graphhopper.util.shapes.Shape;
import pl.grajekf.servicearea.core.StreetExtractor;
import pl.grajekf.servicearea.core.street.StreetInfo;
import pl.grajekf.servicearea.core.street.StreetSegment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class GraphHopperStreetExtractor extends StreetExtractor {

    private final GraphHopperWithOsmId hopper;
    private final Map<Long, StreetInfo.StreetType> streetTypeMap;

    public GraphHopperStreetExtractor(GraphHopperWithOsmId hopper, Map<Long, StreetInfo.StreetType> streetTypeMap) {
        this.hopper = hopper;
        this.streetTypeMap = streetTypeMap;
    }

    @Override
    public List<StreetInfo> getStreetsInPolygon(Shape shape, Collection<StreetInfo.StreetType> streetTypes) {
        GHPoint center = shape.getCenter();
        QueryResult qr = hopper.getLocationIndex().findClosest(center.getLat(), center.getLon(), EdgeFilter.ALL_EDGES);

        if (!qr.isValid())
            throw new IllegalArgumentException("Shape " + shape + " does not cover graph");

        List<StreetInfo> result = new ArrayList<>();

        if (shape.contains(qr.getSnappedPoint().lat, qr.getSnappedPoint().lon)) {
            EdgeIteratorState edge = qr.getClosestEdge();
            StreetInfo newStreet = fromGHEdge(edge);
            if((streetTypes.contains(newStreet.getType())) && edge.isForward(hopper.getEncodingManager().getEncoder("car")) ) {
                result.add(newStreet);
            }
        }


        DepthFirstSearch bfs = new DepthFirstSearch() {
            final NodeAccess na = hopper.getGraphHopperStorage().getBaseGraph().getNodeAccess();
            final Shape localShape = shape;

            @Override
            protected boolean goFurther(int nodeId) {

                return localShape.contains(na.getLatitude(nodeId), na.getLongitude(nodeId));
            }

            @Override
            protected boolean checkAdjacent(EdgeIteratorState edge) {
                int adjNodeId = edge.getAdjNode();

                if (localShape.contains(na.getLatitude(adjNodeId), na.getLongitude(adjNodeId))) {
                    StreetInfo newStreet = fromGHEdge(edge);
                    if((streetTypes.size() == 0 || streetTypes.contains(newStreet.getType())) && edge.isForward(hopper.getEncodingManager().getEncoder("car"))) {
                        result.add(newStreet);
                    }
                    return true;
                }
                return isInsideBBox(adjNodeId);
            }

            private boolean isInsideBBox(int nodeId) {
                BBox bbox = localShape.getBounds();
                double lat = na.getLatitude(nodeId);
                double lon = na.getLongitude(nodeId);
                return lat <= bbox.maxLat && lat >= bbox.minLat && lon <= bbox.maxLon && lon >= bbox.minLon;
            }
        };
        bfs.start(hopper.getGraphHopperStorage().getBaseGraph().createEdgeExplorer(EdgeFilter.ALL_EDGES), qr.getClosestNode());

        return result;
    }

    private StreetInfo fromGHEdge(EdgeIteratorState edge) {
        long id = hopper.getOSMWay(edge.getEdge());
        StreetInfo.StreetType streetType = streetTypeMap.get(id);
        return new StreetInfo(id, edge.getName(), streetType,
                edge.fetchWayGeometry(3), new StreetSegment(edge.getBaseNode(), edge.getAdjNode()));
    }
}
