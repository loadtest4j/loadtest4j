package com.github.themasterchef.loadtest4j;

import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class PropertiesSubset {

    private PropertiesSubset() { }

    protected static Map<String, String> getSubsetAndStripPrefix(Properties properties, String prefix) {
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
