# Vehicle Routing Problem instance generator

Generator made for my master thesis. 

Based on https://github.com/bakala12/VrpTestCasesGenerator

## Prerequisites

* Working nominatim server. Using the official one is not recommended. Easiest option is to use Docker: https://github.com/mediagis/nominatim-docker. Nominatim version is irrelevant, but the program was tested with 3.1.

* Graphhopper and OSM data for the region the instances are generated in. More precisely, a GraphHopper config (example one is provided in this repo: `config-example.properties`) and an OSM data file (can be downloaded from http://download.geofabrik.de/) is needed.

* (Optional) Csv file with street type info. Can be generated from OSM pbf file with `get-street-info.py` script. Usage:
    ```
    ./get-street-info.py PATH_TO_PBF_FILE
    ```
    This creates a csv file with the same name as the pbf file ( + .csv suffix) in the current folder. This file is used to generate clients only on road types actually accessible by delivery vehicles.

## Running

First build the project:

```
./gradlew jar
```
This will create the jar file in `generator/build/libs`. You can then execute it:

```
java -jar generator/build/libs/generator-1.0-SNAPSHOT.jar 
config=PATH_TO_GRAPHHOPPER_CONFIG 
datareader.file=PATH_TO_OSM_PBF_FILE
-c PATH_TO_GENERATOR_CONFIG
-o OUTPUT_PATH_WITH_FILE_PREFIX
-i INSTANCE_COUNT
[-s STREET_TYPE_CSV_FILE]
```

Example execution:
```
java -jar generator/build/libs/generator-1.0-SNAPSHOT.jar  config=../graph-hopper/config-example.properties datareader.file=../graph-hopper/mazowieckie-latest.osm.pbf -c configs/mokotow1500.json -o ../example-instances/mokotow1500_ -i 15
```

The first two options are for GraphHopper and they need to be in this order.
The generator config is a json file with this structure:

```
{
  "vehicleCount": -1,
  "vehicleCapacity": 6000,
  "startNode": {
    "id": 0,
    "latitude": 52.19432388731374,
    "longitude": 21.045727729797367
  },
  "bbox": {
    "minx": 20.014972,
    "maxx": 21.986884,
    "miny": 52.053714,
    "maxy": 52.990423
  },
  "clientCount": 1000,
  "city": "Warszawa Wola",
  "streets": [
    "Kasprzaka",
    "Kolejowa",
    "Przyokopowa",
    "Brylowska",
  ],
  "gammaShape": 1.9,
  "gammaRate": 0.033
}
```

| Parameter Name | Type | Description |
| ---------------| -----| ------------|
| vehicleCount | int | Number of vehicles for the vrp, -1 indicates infinite |
| vehicleCapacity | int | Capacity of vehicles |
| startNode | object | id (should probably be always 0) and position of the vehicle depot | 
| bbox | object| rectangle coordinates for bbox, only streets inside this box are considered. Only used when providing street names directly | 
| clientCount | int | Number of clients |
| city | string | Name of the city or district where the clients are generated. Used as a prefix in nominatim query. When no streets are provided the generator will select ALL streets in this city / district.
streets | list | List of street names where clients are generated. |
| gammaShape | float | Shape parameter for gamma distribution used to get random customer demands | 
| gammaRate | float | Rate parameter for gamma distribution used to get random customer demands | 