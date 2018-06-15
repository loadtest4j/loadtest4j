package com.github.loadtest4j.loadtest4j.utils;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PropertiesSubset {
    private PropertiesSubset() {}

    public static Map<String, String> getSubsetAndStripPrefix(Map<String, String> properties, String prefix) {
        final String processedPrefix = prefix + ".";

        return getSubsetStream(properties, processedPrefix)
                .map(property -> {
                    final String key = property.replace(processedPrefix, "");
                    final String value = properties.getOrDefault(property, null);
                    return new Property(key, value);
                })
                .collect(Collectors.toMap(Property::getKey, Property::getValue));
    }

    private static Stream<String> getSubsetStream(Map<String, String> properties, String prefix) {
        return properties.keySet()
                .stream()
                .filter(propertyName -> propertyName.startsWith(prefix));
    }
}
