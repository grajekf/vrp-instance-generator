package pl.grajekf.servicearea.generator;

import java.util.List;

public interface DemandGenerator {
    List<Integer> generateDemands(int clients);
}

