package org.loadtest4j.factory;

import org.loadtest4j.LoadTesterException;
import org.loadtest4j.driver.DriverFactory;
import org.loadtest4j.utils.ClassFinder;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

class DriverFactorySupplier implements Supplier<DriverFactory> {
    private final ClassFinder classFinder;

    DriverFactorySupplier(ClassFinder classFinder) {
        this.classFinder = classFinder;
    }

    public DriverFactory get() {
        final Collection<Class<DriverFactory>> found = classFinder.findImplementationsOf(DriverFactory.class);

        final int numFound = found.size();
        if (numFound > 1) throw new LoadTesterException(String.format("Only 1 load test driver may be on the classpath at a time, but %d were found.", numFound));

        final Optional<Class<DriverFactory>> maybeFirst = found.stream().findFirst();

        final Class<DriverFactory> first = maybeFirst.orElseThrow(() -> new LoadTesterException("No load test drivers were found on the classpath. Please add one to your project."));

        try {
            return first.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new LoadTesterException("Could not instantiate the load test driver " + first.getName());
        }
    }
}
