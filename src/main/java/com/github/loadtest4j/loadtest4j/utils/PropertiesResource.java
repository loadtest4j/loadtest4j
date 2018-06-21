package com.github.loadtest4j.loadtest4j.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesResource {
    private final String resourceName;

    public PropertiesResource(String resourceName) {
        this.resourceName = resourceName;
    }

    public Properties getProperties() {
        final Properties properties = new Properties();
        try (InputStream is = PropertiesResource.class.getResourceAsStream(resourceName)) {
            properties.load(is);
        } catch (IOException | NullPointerException e) {
            // Return a new properties instance which we know is clean
            return new Properties();
        }
        return properties;
    }
}
