package com.github.loadtest4j.loadtest4j.factory;

import com.github.loadtest4j.loadtest4j.LoadTester;
import com.github.loadtest4j.loadtest4j.LoadTesterException;
import com.github.loadtest4j.loadtest4j.driver.Driver;
import com.github.loadtest4j.loadtest4j.driver.DriverFactory;
import com.github.loadtest4j.loadtest4j.reporter.Reporter;
import com.github.loadtest4j.loadtest4j.utils.FastClassFinder;
import com.github.loadtest4j.loadtest4j.utils.PropertiesSubset;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

class DriverAdapterFactory {
    private static final Supplier<DriverFactory> SUPPLIER = new DriverFactorySupplier(new FastClassFinder());

    private static final String DRIVER_PROPERTY_NAMESPACE = "loadtest4j.driver";

    private static final String REPORTER_PROPERTY_NAMESPACE = "loadtest4j.reporter";

    private final Supplier<DriverFactory> driverFactorySupplier;

    private DriverAdapterFactory(Supplier<DriverFactory> driverFactorySupplier) {
        this.driverFactorySupplier = driverFactorySupplier;
    }

    protected static DriverAdapterFactory defaultFactory() {
        return new DriverAdapterFactory(SUPPLIER);
    }

    protected LoadTester create(Map<String, String> properties) {
        final Driver driver = createDriver(properties);

        return new DriverAdapter(driver);
    }

    private Driver createDriver(Map<String, String> properties) {
        final DriverFactory driverFactory = driverFactorySupplier.get();

        final Map<String, String> driverProperties = getDriverProperties(properties);
        validatePresenceOf(driverProperties, driverFactory.getMandatoryProperties());
        final Driver driver = driverFactory.create(driverProperties);

        final Driver validatingDriver = new ValidatingDriver(driver);

        final Map<String, String> reporterProperties = getReporterProperties(properties);
        final Reporter reporter = createReporter(reporterProperties);
        return new ReportingDriver(validatingDriver, reporter);
    }

    private Reporter createReporter(Map<String, String> reporterProperties) {
        return new ReporterFactory().create(reporterProperties);
    }

    private static Map<String, String> getDriverProperties(Map<String, String> properties) {
        return PropertiesSubset.getSubsetAndStripPrefix(properties, DRIVER_PROPERTY_NAMESPACE);
    }

    private static Map<String, String> getReporterProperties(Map<String, String> properties) {
        return PropertiesSubset.getSubsetAndStripPrefix(properties, REPORTER_PROPERTY_NAMESPACE);
    }

    private static void validatePresenceOf(Map map, Collection<String> keys) {
        final Set<String> missingKeys = keys.stream()
                .filter(key -> !map.containsKey(key))
                .map(key -> String.format("%s.%s", DRIVER_PROPERTY_NAMESPACE, key))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        if (!missingKeys.isEmpty()) {
            final String msg = String.format("The following driver properties are missing: %s. Add them to /loadtest4j.properties or the JVM properties.", missingKeys);
            throw new LoadTesterException(msg);
        }
    }
}
