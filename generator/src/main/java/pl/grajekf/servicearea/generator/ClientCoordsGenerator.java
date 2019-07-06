package pl.grajekf.servicearea.generator;

import com.vividsolutions.jts.geom.Geometry;
import pl.grajekf.servicearea.generator.model.Location;

import java.util.List;

public interface ClientCoordsGenerator {
    List<Location> generateClientCoords(int clientCount, List<Geometry> addresses);
}
