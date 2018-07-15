package com.github.loadtest4j.loadtest4j.factory;

import com.github.loadtest4j.loadtest4j.LoadTesterException;
import com.github.loadtest4j.loadtest4j.driver.DriverFactory;
import com.github.loadtest4j.loadtest4j.utils.ClassFinder;

import java.util.Collection;
import java.util.Optional;

class DriverFactoryFinder {
    private final ClassFinder classFinder;

    DriverFactoryFinder(ClassFinder classFinder) {
        this.classFinder = classFinder;
    }

    public DriverFactory find() {
        final Collection<DriverFactory> found = classFinder.findImplementationsOf(DriverFactory.class);

        final int numFound = found.size();
        if (numFound > 1) throw new LoadTesterException(String.format("Only 1 load test driver may be on the classpath at a time, but %d were found.", numFound));

        final Optional<DriverFactory> first = found.stream().findFirst();

        return first.orElseThrow(() -> new LoadTesterException("No load test drivers were found on the classpath. Please add one to your project."));
    }
}
