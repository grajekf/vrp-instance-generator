package pl.grajekf.servicearea.generator;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.graphhopper.util.CmdArgs;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import fr.dudie.nominatim.client.JsonNominatimClient;
import fr.dudie.nominatim.client.NominatimClient;
import fr.dudie.nominatim.client.request.NominatimSearchRequest;
import fr.dudie.nominatim.client.request.paramhelper.PolygonFormat;
import fr.dudie.nominatim.model.Address;
import org.apache.commons.cli.*;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.grajekf.servicearea.core.Node;
import pl.grajekf.servicearea.core.StreetExtractor;
import pl.grajekf.servicearea.core.graphhopper.GraphHopperStreetExtractor;
import pl.grajekf.servicearea.core.graphhopper.GraphHopperWithOsmId;
import pl.grajekf.servicearea.core.json.NodeWithDemandData;
import pl.grajekf.servicearea.core.json.VRPConfig;
import pl.grajekf.servicearea.core.street.CsvStreetTypeMapFactory;
import pl.grajekf.servicearea.core.street.StreetInfo;
import pl.grajekf.servicearea.core.street.StreetTypeMapFactory;
import pl.grajekf.servicearea.generator.model.Location;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GeneratorMain {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(GeneratorMain.class);

        Option configFile = Option.builder("c").argName("configFile").hasArg().build();
        Option outputFile = Option.builder("o").argName("outputFile").hasArg().build();
        Option instances = Option.builder("i").argName("instances").hasArg().build();
        Option csvStreetMap = Option.builder("s").argName("streetMap").hasArg().build();
        Options options = new Options();
        options.addOption(configFile);
        options.addOption(outputFile);
        options.addOption(instances);
        options.addOption(csvStreetMap);
        CommandLineParser parser = new DefaultParser();

        try {
            // parse the command line arguments
            CommandLine line = parser.parse( options, args );
            Gson gson = new GsonBuilder().create();

            GraphHopperWithOsmId hopper = new GraphHopperWithOsmId();
            hopper.init(CmdArgs.read(args));
            hopper.importOrLoad();

            PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
            // Increase max total connection to 200
            cm.setMaxTotal(200);
            // Increase default max connection per route to 20
            cm.setDefaultMaxPerRoute(20);

            NominatimClient client = new JsonNominatimClient("http://localhost:7070", HttpClients.custom().setConnectionManager(cm).build(), "t@t.com");

            String configFilePath = line.getOptionValue("c");
            JsonReader reader = new JsonReader(new FileReader(configFilePath));
            GeneratorConfig generatorConfig = gson.fromJson(reader, GeneratorConfig.class);

            Reader csvInput = new FileReader(line.getOptionValue("s"));
            StreetTypeMapFactory streetTypeMapFactory = new CsvStreetTypeMapFactory(csvInput);

            List<String> streets = generatorConfig.getStreets();
            String district = generatorConfig.getDistrict();
            Envelope bbox = generatorConfig.getBbox();
            int vehicleCount = generatorConfig.getVehicleCount();
            int vehicleCapacity = generatorConfig.getVehicleCapacity();
            Node startNode = generatorConfig.getStartNode();
            int clientCount = generatorConfig.getClientCount();
            int dumpOpenTimeSeconds = generatorConfig.getDumpOpenTimeSeconds();
            int dumpCloseTimeSeconds = generatorConfig.getDumpCloseTimeSeconds();
            int vehicleStartTimeSeconds = generatorConfig.getVehicleStartTimeSeconds();
            int instanceCount = Integer.parseInt(line.getOptionValue("i"));
            String outputFilePath = line.getOptionValue("o");

            List<Geometry> streetInfos;

            if(district != null) {
                StreetExtractor streetExtractor = new GraphHopperStreetExtractor(hopper, streetTypeMapFactory.getStreetInfoMap());
                NominatimSearchRequest req = new NominatimSearchRequest();
                req.setQuery("Warszawa " + district);
                req.setPolygonFormat(PolygonFormat.GEO_JSON);

                Address districtInfo = client.search(req).get(0);
                Geometry geom = districtInfo.getGeojson();

                List<StreetInfo.StreetType> interestingStreetTypes = Arrays.asList(
                        StreetInfo.StreetType.PRIMARY,
                        StreetInfo.StreetType.PRIMARY_LINK,
                        StreetInfo.StreetType.SECONDARY,
                        StreetInfo.StreetType.SECONDARY_LINK,
                        StreetInfo.StreetType.TERTIARY,
                        StreetInfo.StreetType.TERTIARY_LINK,
                        StreetInfo.StreetType.LIVING_STREET,
                        StreetInfo.StreetType.LIVING_STREET,
                        StreetInfo.StreetType.RESIDENTIAL,
                        StreetInfo.StreetType.SERVICE
                );

                streetInfos = streetExtractor.getStreetsInPolygon(geom, interestingStreetTypes).stream().map(
                        s -> {
                            return s.getPoints().toLineString(false);
                        }
                ).collect(Collectors.toList());


            } else {
                streetInfos = streets.parallelStream().map((street) -> {
                    try {
                        NominatimSearchRequest req = new NominatimSearchRequest();
                        req.setQuery("Warszawa " + street);
                        req.setPolygonFormat(PolygonFormat.GEO_JSON);
                        return client.search(req);
                    } catch (IOException e) {
                        return new ArrayList<Address>();
                    }
                }).flatMap(Collection::stream).map(a -> a.getGeojson()).filter(a -> bbox.contains(a.getCoordinate())).collect(Collectors.toList());

            }



            DemandGenerator demandGenerator = new GammaDemandGenerator(1.9, 0.033);
            ClientCoordsGenerator clientCoordsGenerator = new SynchronousClientCoordsGenerator();

            IntStream.range(0, instanceCount).parallel().forEach(i -> {
                List<Location> locations = clientCoordsGenerator.generateClientCoords(clientCount, streetInfos);
                List<Integer> demands = demandGenerator.generateDemands(locations.size());

                List<Node> nodes = IntStream.range(0, locations.size()).mapToObj(index -> {
                    Location l = locations.get(index);
                    return new Node((long)index + 1, l.getLatitude(), l.getLongitude());
                }).collect(Collectors.toList());

                List<NodeWithDemandData> nodeDemands = IntStream.range(0, locations.size()).mapToObj(index -> {
                    return new NodeWithDemandData(nodes.get(index).getId(), demands.get(index));
                }).collect(Collectors.toList());

                nodes.add(0, startNode);

                VRPConfig config = new VRPConfig(nodes.toArray(new Node[0]),
                        startNode.getId(),
                        vehicleCapacity,
                        vehicleCount,
                        dumpOpenTimeSeconds,
                        dumpCloseTimeSeconds,
                        vehicleStartTimeSeconds,
                        nodeDemands.toArray(new NodeWithDemandData[0]),
                        outputFilePath + i + ".cm");
                try {
                    FileWriter writer = new FileWriter(outputFilePath + i + ".vrp");
                    gson.toJson(config, writer);
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error("Could not create file %s", outputFilePath + i + ".vrp");
                }


            });
        }
        catch( ParseException exp ) {
            // oops, something went wrong
            logger.error( "Parsing failed.  Reason: " + exp.getMessage() );
        }

        catch (IOException e) {
            e.printStackTrace();
            logger.error("Could not read config file");
        }


    }

}
