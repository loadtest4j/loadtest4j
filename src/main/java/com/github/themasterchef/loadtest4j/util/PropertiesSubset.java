package com.github.themasterchef.loadtest4j.util;

import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PropertiesSubset {

    private PropertiesSubset() { }

    public static Map<String, String> getSubset(Properties properties, String prefix) {
        return getSubsetStream(properties, prefix)
                .map(property -> new Property(property, properties.getProperty(property, null)))
                .collect(Collectors.toMap(Property::getKey, Property::getValue));
    }

    public static Map<String, String> getSubsetAndStripPrefix(Properties properties, String prefix) {
        final String processedPrefix = prefix + ".";

        return getSubsetStream(properties, processedPrefix)
                .map(property -> new Property(property.replace(processedPrefix, ""), properties.getProperty(property, null)))
                .collect(Collectors.toMap(Property::getKey, Property::getValue));
    }

    private static Stream<String> getSubsetStream(Properties properties, String prefix) {
        return properties.stringPropertyNames()
                .stream()
                .filter(propertyName -> propertyName.startsWith(prefix));
    }

    private static class Property {

        private final String key;
        private final String value;

        private Property(String key, String value) {
            this.key = key;
            this.value = value;
        }

        private String getKey() {
            return key;
        }

        private String getValue() {
            return value;
        }
    }
}
