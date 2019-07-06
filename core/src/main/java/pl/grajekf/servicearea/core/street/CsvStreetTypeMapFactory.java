package pl.grajekf.servicearea.core.street;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class CsvStreetTypeMapFactory implements StreetTypeMapFactory {

    public CsvStreetTypeMapFactory(Reader input) {
        this.input = input;
    }

    private Reader input;
    private HashMap<Long, StreetInfo.StreetType> resultCache;

    @Override
    public Map<Long, StreetInfo.StreetType> getStreetInfoMap() {
        if(resultCache != null) {
            return resultCache;
        }
        HashMap<Long, StreetInfo.StreetType> result = new HashMap<>();
        try {
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(input);
            for (CSVRecord record : records) {
                Long id = Long.parseLong(record.get("id"));
                try {
                    StreetInfo.StreetType streetType = StreetInfo.StreetType.valueOf(record.get("highway").toUpperCase());
                    result.put(id, streetType);
                } catch (IllegalArgumentException e) {

                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        resultCache = result;
        return result;
    }
}
