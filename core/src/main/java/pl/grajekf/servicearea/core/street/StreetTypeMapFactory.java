package pl.grajekf.servicearea.core.street;

import java.util.Map;

public interface StreetTypeMapFactory {
    Map<Long, StreetInfo.StreetType> getStreetInfoMap();
}
