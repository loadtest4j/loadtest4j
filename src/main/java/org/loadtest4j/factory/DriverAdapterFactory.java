package org.loadtest4j.factory;

import org.loadtest4j.LoadTester;
import org.loadtest4j.LoadTesterException;
import org.loadtest4j.driver.Driver;
import org.loadtest4j.driver.DriverFactory;
import org.loadtest4j.utils.PropertiesSubset;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

class DriverAdapterFactory {
    private static final Supplier<DriverFactory> SUPPLIER = DriverFactorySupplier.standard();

    private static final String DRIVER_PROPERTY_NAMESPACE = "loadtest4j.driver";

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

        return new ValidatingDriver(driver);
    }

    private static Map<String, String> getDriverProperties(Map<String, String> properties) {
        return PropertiesSubset.getSubsetAndStripPrefix(properties, DRIVER_PROPERTY_NAMESPACE);
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
