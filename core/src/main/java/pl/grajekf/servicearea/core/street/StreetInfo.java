package pl.grajekf.servicearea.core.street;

import com.graphhopper.util.PointList;
import com.graphhopper.util.shapes.GHPoint;
import pl.grajekf.servicearea.core.Entity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class StreetInfo implements Entity<Long> {

    public StreetInfo(Long id, String name, StreetType type, PointList points, StreetSegment streetSegment) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.points = points;
        this.streetSegment = streetSegment;
    }


    public StreetInfo copy() {
        return new StreetInfo(id, name, type, points, streetSegment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StreetInfo that = (StreetInfo) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                points.toString().equals(that.points.toString()) &&
                streetSegment.equals(that.streetSegment) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, streetSegment);
    }

    public String getName() {
        return name;
    }

    public StreetType getType() {
        return type;
    }

    public PointList getPoints() {
        return points;
    }

    public List<Double[]> getPointGeoJson() {
        return Arrays.stream(points.toLineString(false)
                .getCoordinates()).map(c -> {
                    return new Double[] {c.x, c.y};
        }).collect(Collectors.toList());
    }

    public StreetSegment getStreetSegment() {
        return streetSegment;
    }

    public GHPoint getStart() {
        return points.toGHPoint(0);
    }

    public GHPoint getEnd() {
        return points.toGHPoint(points.size() - 1);
    }

    public GHPoint getMedianPoint() {
        if(points.size() % 2 == 0) {
            GHPoint firstPoint = points.toGHPoint(points.size() / 2 - 1);
            GHPoint secondPoint = points.toGHPoint(points.size() / 2 );
            return new GHPoint((firstPoint.lat + secondPoint.lat) / 2, (firstPoint.lon + secondPoint.lon) / 2);
        }
        return points.toGHPoint(points.size() / 2);
    }


    @Override
    public Long getId() {
        return id;
    }

    private Long id;
    private String name;
    private StreetType type;
    private PointList points;
    private StreetSegment streetSegment;

    public enum StreetType {
        MOTORWAY,
        TRUNK,
        PRIMARY,
        SECONDARY,
        TERTIARY,
        UNCLASSIFIED,
        RESIDENTIAL,
        SERVICE,
        MOTORWAY_LINK,
        TRUNK_LINK,
        PRIMARY_LINK,
        SECONDARY_LINK,
        TERTIARY_LINK,
        LIVING_STREET,
        TRACK,
        BUS_GUIDEWAY,
        ESCAPE,
        RACEWAY,
        ROAD
    }
}
