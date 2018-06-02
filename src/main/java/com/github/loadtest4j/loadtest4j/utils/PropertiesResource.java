package com.github.loadtest4j.loadtest4j.utils;

import java.io.IOException;
import java.util.Properties;

public class PropertiesResource {
    private final String resourceName;

    public PropertiesResource(String resourceName) {
        this.resourceName = resourceName;
    }

    public Properties getProperties() {
        final Properties properties = new Properties();
        try {
            properties.load(PropertiesResource.class.getResourceAsStream(resourceName));
        } catch (IOException | NullPointerException e) {
            // Return a new properties instance which we know is clean
            return new Properties();
        }
        return properties;
    }
}
