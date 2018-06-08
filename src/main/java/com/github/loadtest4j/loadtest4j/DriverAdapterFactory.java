package com.github.loadtest4j.loadtest4j;

import com.github.loadtest4j.loadtest4j.driver_reporter.DriverReporter;
import com.github.loadtest4j.loadtest4j.utils.ClassFinder;
import com.github.loadtest4j.loadtest4j.utils.PropertiesSubset;
import com.github.loadtest4j.loadtest4j.utils.ReflectiveClassFinder;

import java.util.*;
import java.util.stream.Collectors;

class DriverAdapterFactory {
    private static final ClassFinder<DriverFactory> FINDER = new ReflectiveClassFinder<>(DriverFactory.class);

    private static final String DRIVER_PROPERTY_NAMESPACE = "loadtest4j.driver";

    private static final String REPORTER_PROPERTY_NAMESPACE = "loadtest4j.reporter";

    private final ClassFinder<DriverFactory> classFinder;

    DriverAdapterFactory(ClassFinder<DriverFactory> classFinder) {
        this.classFinder = classFinder;
    }

    protected static DriverAdapterFactory defaultFactory() {
        return new DriverAdapterFactory(FINDER);
    }

    protected LoadTester create(Map<String, String> properties) {
        final Driver driver = createDriver(properties);

        return new DriverAdapter(driver);
    }

    private Driver createDriver(Map<String, String> properties) {
        final DriverFactory driverFactory = getDriverFactory(properties);

        final Map<String, String> driverProperties = getDriverProperties(properties);
        validatePresenceOf(driverProperties, driverFactory.getMandatoryProperties());
        final Driver driver = driverFactory.create(driverProperties);

        final Driver validatingDriver = new ValidatingDriver(driver);

        final Map<String, String> reporterProperties = getReporterProperties(properties);
        final DriverReporter reporter = createReporter(reporterProperties);
        return new ReportingDriver(validatingDriver, reporter);
    }

    private DriverReporter createReporter(Map<String, String> reporterProperties) {
        return new DriverReporterFactory().create(reporterProperties);
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
                .collect(Collectors.toCollection(LinkedHashSet::new));

        if (!missingKeys.isEmpty()) {
            final String msg = String.format("The following load test driver properties were not found: %s. Please specify them either as JVM properties or in loadtest4j.properties.", missingKeys);
            throw new LoadTesterException(msg);
        }
    }

    private DriverFactory getDriverFactory(Map<String, String> properties) {
        validatePresenceOf(properties, Collections.singletonList(DRIVER_PROPERTY_NAMESPACE));

        final String driverType = properties.get(DRIVER_PROPERTY_NAMESPACE);

        return classFinder.find(driverType).orElseThrow(() -> new LoadTesterException("Invalid load test driver type."));
    }

}
