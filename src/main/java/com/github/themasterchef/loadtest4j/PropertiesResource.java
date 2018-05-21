package com.github.themasterchef.loadtest4j;

import java.io.IOException;
import java.util.Properties;

class PropertiesResource {

    private final String resourceName;

    PropertiesResource(String resourceName) {
        this.resourceName = resourceName;
    }

    protected Properties getProperties() {
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
