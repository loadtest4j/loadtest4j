package com.github.loadtest4j.loadtest4j;

import com.github.loadtest4j.loadtest4j.driver_reporter.DriverReporter;
import com.github.loadtest4j.loadtest4j.utils.ClasspathScanner;
import com.github.loadtest4j.loadtest4j.utils.PropertiesSubset;

import java.util.*;
import java.util.stream.Collectors;

class DriverAdapterFactory {
    private static final DriverFactoryScanner DRIVER_FACTORIES = new DriverFactoryClasspathScanner();

    private static final String DRIVER_PROPERTY_NAMESPACE = "loadtest4j.driver";

    private static final String REPORTER_PROPERTY_NAMESPACE = "loadtest4j.reporter";

    private final DriverFactoryScanner driverFactoryScanner;

    DriverAdapterFactory(DriverFactoryScanner driverFactoryScanner) {
        this.driverFactoryScanner = driverFactoryScanner;
    }

    protected static DriverAdapterFactory defaultFactory() {
        return new DriverAdapterFactory(DRIVER_FACTORIES);
    }

    protected LoadTester create(Map<String, String> properties) {
        final Driver driver = createDriver(properties);

        return new DriverAdapter(driver);
    }

    private Driver createDriver(Map<String, String> properties) {
        final DriverFactory driverFactory = getDriverFactory(properties);

        validatePresenceOf(properties, driverFactory.getMandatoryProperties());

        final Map<String, String> driverProperties = getDriverProperties(properties);
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
        validateNotEmpty(driverFactoryScanner);

        validatePresenceOf(properties, Collections.singletonList(DRIVER_PROPERTY_NAMESPACE));

        final String driverType = properties.get(DRIVER_PROPERTY_NAMESPACE);

        return driverFactoryScanner.findFirst(driverType).orElseThrow(() -> new LoadTesterException("Invalid load test driver type."));
    }

    private static void validateNotEmpty(DriverFactoryScanner scanner) {
        if (scanner.isEmpty()) {
            throw new LoadTesterException("No load test drivers were found. Please add one or more drivers to your project.");
        }
    }

    interface DriverFactoryScanner {
        Optional<DriverFactory> findFirst(String className);

        boolean isEmpty();
    }

    static class DriverFactoryClasspathScanner implements DriverFactoryScanner {
        private static Collection<DriverFactory> FACTORIES = load();

        private static Collection<DriverFactory> load() {
            return new ClasspathScanner<>(DriverFactory.class).scan();
        }

        public Optional<DriverFactory> findFirst(String className) {
            return FACTORIES.stream()
                    .filter(factory -> factory.getClass().getName().equals(className))
                    .findFirst();
        }

        public boolean isEmpty() {
            return FACTORIES.isEmpty();
        }
    }
}
