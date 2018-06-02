package com.github.loadtest4j.loadtest4j.utils;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PropertiesSubset {
    private PropertiesSubset() {}

    public static Map<String, String> getSubsetAndStripPrefix(Map<String, String> properties, String prefix) {
        final String processedPrefix = prefix + ".";

        return getSubsetStream(properties, processedPrefix)
                .map(property -> new Property(property.replace(processedPrefix, ""), properties.getOrDefault(property, null)))
                .collect(Collectors.toMap(Property::getKey, Property::getValue));
    }

    private static Stream<String> getSubsetStream(Map<String, String> properties, String prefix) {
        return properties.keySet()
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
