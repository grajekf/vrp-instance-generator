package pl.grajekf.servicearea.core;

import pl.grajekf.servicearea.core.json.VRPConfig;

public interface ConfigProvider {
    VRPConfig getConfig();
    String getName();
}
