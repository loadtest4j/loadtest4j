package com.github.themasterchef.loadtest4j;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public final class LoadTesterFactory {

    private static final Iterable<DriverFactory> DRIVER_FACTORIES = ServiceLoader.load(DriverFactory.class);

    private static final String DRIVER_PROPERTY_NAMESPACE = "loadtest4j.driver";

    private final Iterable<DriverFactory> driverFactories;

    private final Properties properties;

    /**
     * The primary entry point to the library. Clients should request {@link LoadTester} instances from here.
     *
     * Driver configuration uses the following order of precedence (1 = highest priority):
     *
     * 1. The system properties.
     * 2. The loadtest4j.properties file in the jar resources folder.
     *
     * @return A configured {@link LoadTester}
     */
    public static LoadTester getLoadTester() {
        final Properties properties = new Properties();
        properties.putAll(new PropertiesResource("/loadtest4j.properties").getProperties());
        properties.putAll(System.getProperties());

        final LoadTesterFactory factory = new LoadTesterFactory(DRIVER_FACTORIES, properties);
        return factory.createLoadTester();
    }

    LoadTesterFactory(Iterable<DriverFactory> driverFactories, Properties properties) {
        this.driverFactories = driverFactories;
        this.properties = properties;
    }

    protected LoadTester createLoadTester() {
        final DriverFactory driverFactory = getDriverFactory();

        final Map<String, String> driverProperties = getDriverProperties();

        validatePresenceOf(driverProperties, driverFactory.getMandatoryProperties());

        final Driver driver = driverFactory.create(driverProperties);

        return new DriverAdapter(driver);
    }

    private DriverFactory getDriverFactory() {
        validatePresenceOf(properties, Collections.singletonList(DRIVER_PROPERTY_NAMESPACE));

        final String driverType = properties.getProperty(DRIVER_PROPERTY_NAMESPACE);

        return getDriverFactory(driverType).orElseThrow(() -> new LoadTesterException("Invalid load test driver type."));
    }

    private Map<String, String> getDriverProperties() {
        return PropertiesSubset.getSubsetAndStripPrefix(properties, DRIVER_PROPERTY_NAMESPACE);
    }

    private Optional<DriverFactory> getDriverFactory(String className) {
        return StreamSupport.stream(driverFactories.spliterator(), false)
                .filter(factory -> factory.getClass().getName().equals(className))
                .findFirst();
    }

    private static void validatePresenceOf(Map map, Collection<String> keys) {
        final Set<String> missingKeys = keys.stream()
                .filter(key -> !map.containsKey(key))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        if (!missingKeys.isEmpty()) {
            final String msg = String.format("The following load test driver properties were not found: %s. Please specify them either as JVM properties or in loadtest4j.properties.", missingKeys);
            throw new LoadTesterException(msg);
        }
    }
}
