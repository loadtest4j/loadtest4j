package com.github.loadtest4j.loadtest4j;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class LoadTesterFactory {

    private static final DriverFactoryScanner DRIVER_FACTORIES = new DriverFactoryClasspathScanner();

    private static final String DRIVER_PROPERTY_NAMESPACE = "loadtest4j.driver";

    private final DriverFactoryScanner driverFactoryScanner;

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

    LoadTesterFactory(DriverFactoryScanner driverFactoryScanner, Properties properties) {
        this.driverFactoryScanner = driverFactoryScanner;
        this.properties = properties;
    }

    protected LoadTester createLoadTester() {
        final DriverFactory driverFactory = getDriverFactory();

        final Map<String, String> driverProperties = getDriverProperties();

        validatePresenceOf(driverProperties, driverFactory.getMandatoryProperties());

        final Driver driver = driverFactory.create(driverProperties);

        final Driver validatingDriver = new ValidatingDriver(driver);

        return new DriverAdapter(validatingDriver);
    }

    private DriverFactory getDriverFactory() {
        validateNotEmpty(driverFactoryScanner);

        validatePresenceOf(properties, Collections.singletonList(DRIVER_PROPERTY_NAMESPACE));

        final String driverType = properties.getProperty(DRIVER_PROPERTY_NAMESPACE);

        return driverFactoryScanner.findFirst(driverType).orElseThrow(() -> new LoadTesterException("Invalid load test driver type."));
    }

    private Map<String, String> getDriverProperties() {
        return PropertiesSubset.getSubsetAndStripPrefix(properties, DRIVER_PROPERTY_NAMESPACE);
    }

    private static void validateNotEmpty(DriverFactoryScanner scanner) {
        if (scanner.isEmpty()) {
            throw new LoadTesterException("No load test drivers were found. Please add one or more drivers to your project.");
        }
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

    static class PropertiesResource {
        private final String resourceName;

        PropertiesResource(String resourceName) {
            this.resourceName = resourceName;
        }

        protected Properties getProperties() {
            final Properties properties = new Properties();
            try {
                properties.load(PropertiesResource.class.getResourceAsStream(resourceName));
            } catch (IOException | NullPointerException e) {
                // Return a new properties instance which we know is clean
                return new Properties();
            }
            return properties;
        }
    }

    static class PropertiesSubset {
        private PropertiesSubset() {}

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

    interface DriverFactoryScanner {
        Optional<DriverFactory> findFirst(String className);

        boolean isEmpty();
    }

    static class DriverFactoryClasspathScanner implements DriverFactoryScanner {
        private static Collection<DriverFactory> FACTORIES = load();

        private static Collection<DriverFactory> load() {
            final List<Class<? extends DriverFactory>> driverFactoryClasses = new ArrayList<>();
            new FastClasspathScanner()
                    .matchClassesImplementing(DriverFactory.class, driverFactoryClasses::add)
                    .scan();

            return driverFactoryClasses.stream()
                    .map(c -> {
                        try {
                            return c.newInstance();
                        } catch (InstantiationException | IllegalAccessException e) {
                            throw new LoadTesterException(e);
                        }
                    })
                    .collect(Collectors.toList());
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
