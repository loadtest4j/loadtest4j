package com.github.themasterchef.loadtest4j.util;

import java.util.*;
import java.util.stream.Collectors;

public class Validator {
    private Validator() {}

    public static void validatePresenceOf(Map map, String... keys) {
        final Set<String> missingKeys = Arrays.stream(keys)
                .filter(key -> !map.containsKey(key))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        if (!missingKeys.isEmpty()) throw new MissingPropertiesException(missingKeys);
    }

    public static class MissingPropertiesException extends RuntimeException {
        private final Collection<String> missingProperties;

        MissingPropertiesException(Collection<String> missingProperties) {
            this.missingProperties = missingProperties;
        }

        public Collection<String> getMissingProperties() {
            return missingProperties;
        }
    }
}
