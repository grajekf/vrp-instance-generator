package pl.grajekf.servicearea.core;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.grajekf.servicearea.core.json.VRPConfig;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class FileConfigProvider implements ConfigProvider {
    private final Gson gson;
    private final String file;
    private final Logger logger;

    public FileConfigProvider(String file) {

        this.file = file;
        this.gson = new Gson();
        this.logger = LoggerFactory.getLogger(FileConfigProvider.class);
    }

    @Override
    public VRPConfig getConfig() {
        try {
            JsonReader reader = new JsonReader(new FileReader(file));
            VRPConfig config =  gson.fromJson(reader, VRPConfig.class);
            return config;

        } catch (FileNotFoundException e) {
            logger.error(String.format("The vrp config file %s does not exist", file));
            return null;
        }
    }

    @Override
    public String getName() {
        return file.substring(file.lastIndexOf("/")+1);
    }
}
