package pl.grajekf.servicearea.core.street;

import java.util.Objects;

public class StreetSegment {

    private final int fromId;
    private final int toId;

    public StreetSegment(int fromId, int toId) {

        this.fromId = fromId;
        this.toId = toId;
    }

    public int getToId() {
        return toId;
    }

    public int getFromId() {
        return fromId;
    }

    public boolean isReverseOf(StreetSegment other) {
        return toId == other.getFromId() && fromId == other.getToId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StreetSegment that = (StreetSegment) o;
        return Objects.equals(this.fromId, that.fromId) && Objects.equals(this.toId, that.toId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromId, toId);
    }

}
