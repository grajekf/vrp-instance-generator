package pl.grajekf.servicearea.generator;



import org.apache.commons.math3.distribution.GammaDistribution;

import java.util.ArrayList;
import java.util.List;

public class GammaDemandGenerator implements DemandGenerator {

    private GammaDistribution gammaDistribution;

    public GammaDemandGenerator(double shape, double rate) {
        gammaDistribution = new GammaDistribution(shape, 1 / rate);
    }

    @Override
    public List<Integer> generateDemands(int clients) {
        List<Integer> result = new ArrayList<>();

        for(int i = 0; i < clients; i++) {
            result.add((int)Math.ceil(gammaDistribution.sample()));
        }

        return result;
    }
}
