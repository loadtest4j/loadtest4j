package com.github.themasterchef.loadtest4j;

import com.github.themasterchef.loadtest4j.drivers.DriverFactory;
import com.github.themasterchef.loadtest4j.util.PropertiesResource;
import com.github.themasterchef.loadtest4j.util.PropertiesSubset;
import com.github.themasterchef.loadtest4j.util.Validator;

import java.util.*;
import java.util.stream.StreamSupport;

public final class LoadTesterFactory {

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
        return new LoadTesterFactory(DRIVER_FACTORIES, properties).createLoadTester();
    }

    private static final Iterable<DriverFactory> DRIVER_FACTORIES = ServiceLoader.load(DriverFactory.class);

    private static final String DRIVER_PROPERTY_NAMESPACE = "loadtest4j.driver";

    private final Iterable<DriverFactory> driverFactories;
    private final Properties properties;

    LoadTesterFactory(Iterable<DriverFactory> driverFactories, Properties properties) {
        this.driverFactories = driverFactories;
        this.properties = properties;
    }

    LoadTester createLoadTester() {
        try {
            Validator.validatePresenceOf(properties, DRIVER_PROPERTY_NAMESPACE);

            final String driverType = properties.getProperty(DRIVER_PROPERTY_NAMESPACE);
            final DriverFactory driverFactory = getDriverFactory(driverType).orElseThrow(() -> new LoadTesterException("Invalid load test driver type."));

            final Map<String, String> driverProperties = PropertiesSubset.getSubsetAndStripPrefix(properties, DRIVER_PROPERTY_NAMESPACE);

            return driverFactory.create(driverProperties);
        } catch (Validator.MissingPropertiesException e) {
            final String msg = String.format("The following load test driver properties were not found: %s. Please specify them either as JVM properties or in loadtest4j.properties.", e.getMissingProperties());
            throw new LoadTesterException(msg);
        }
    }

    private Optional<DriverFactory> getDriverFactory(String type) {
        return StreamSupport.stream(driverFactories.spliterator(), false)
                .filter(factory -> factory.getType().equals(type))
                .findFirst();
    }
}
