package org.loadtest4j.factory;

import org.loadtest4j.LoadTesterException;
import org.loadtest4j.driver.DriverFactory;

import java.util.Collection;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

class DriverFactorySupplier implements Supplier<DriverFactory> {

    private final Iterable<DriverFactory> serviceLoader;

    DriverFactorySupplier(Iterable<DriverFactory> serviceLoader) {
        this.serviceLoader = serviceLoader;
    }

    static DriverFactorySupplier standard() {
        return new DriverFactorySupplier(ServiceLoader.load(DriverFactory.class));
    }

    public DriverFactory get() {
        final Collection<DriverFactory> factories = StreamSupport.stream(serviceLoader.spliterator(), false).collect(Collectors.toList());

        final int numFound = factories.size();
        if (numFound > 1) throw new LoadTesterException(String.format("Only 1 load test driver may be on the classpath at a time, but %d were found.", numFound));

        final Optional<DriverFactory> maybeFirst = factories.stream().findFirst();

        return maybeFirst.orElseThrow(() -> new LoadTesterException("No load test drivers were found on the classpath. Please add one to your project."));
    }
}
