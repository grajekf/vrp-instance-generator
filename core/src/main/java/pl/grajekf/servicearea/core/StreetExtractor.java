package pl.grajekf.servicearea.core;

import com.graphhopper.util.shapes.Polygon;
import com.graphhopper.util.shapes.Shape;
import com.vividsolutions.jts.geom.Geometry;
import pl.grajekf.servicearea.core.street.StreetInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public abstract class StreetExtractor {
    public abstract List<StreetInfo> getStreetsInPolygon(Shape shape, Collection<StreetInfo.StreetType> streetTypes);
    public List<StreetInfo> getStreetsInPolygon(Shape shape) {
        return getStreetsInPolygon(shape, new ArrayList<>(Arrays.asList(StreetInfo.StreetType.values())));
    }

    public List<StreetInfo> getStreetsInPolygon(Geometry geometry, Collection<StreetInfo.StreetType> streetTypes) {
        Shape shape = shapeFromGeometry(geometry);

        return getStreetsInPolygon(shape, streetTypes);
    }

    public List<StreetInfo> getStreetsInPolygon(Geometry geometry) {
        Shape shape = shapeFromGeometry(geometry);

        return getStreetsInPolygon(shape, new ArrayList<>(Arrays.asList(StreetInfo.StreetType.values())));
    }

    private Shape shapeFromGeometry(Geometry geometry) {
        double[] lats = new double[geometry.getNumPoints()];
        double[] lons = new double[geometry.getNumPoints()];
        for (int i = 0; i < geometry.getNumPoints(); i++) {
            lats[i] = geometry.getCoordinates()[i].y;
            lons[i] = geometry.getCoordinates()[i].x;
        }
        return  new Polygon(lats, lons);
    }

}
