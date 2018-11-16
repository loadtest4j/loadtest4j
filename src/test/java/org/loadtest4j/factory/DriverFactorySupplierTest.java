package org.loadtest4j.factory;

import org.loadtest4j.LoadTesterException;
import org.loadtest4j.driver.DriverFactory;
import org.loadtest4j.junit.UnitTest;
import org.loadtest4j.test_utils.NopDriverFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class DriverFactorySupplierTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testGet() {
        final Iterable<DriverFactory> singleFinder = Collections.singletonList(new NopDriverFactory());
        final DriverFactorySupplier supplier = new DriverFactorySupplier(singleFinder);

        final DriverFactory factory = supplier.get();

        assertThat(factory).isInstanceOf(NopDriverFactory.class);
    }

    @Test
    public void testGetWithNoDriverFactories() {
        final Iterable<DriverFactory> emptyFinder = Collections.emptyList();
        final DriverFactorySupplier supplier = new DriverFactorySupplier(emptyFinder);

        thrown.expect(LoadTesterException.class);
        thrown.expectMessage("No load test drivers were found on the classpath. Please add one to your project.");

        supplier.get();
    }

    @Test
    public void testGetWithMoreThanOneDriverFactory() {
        final Iterable<DriverFactory> duplicateFinder = Arrays.asList(new NopDriverFactory(), new NopDriverFactory());
        final DriverFactorySupplier supplier = new DriverFactorySupplier(duplicateFinder);

        thrown.expect(LoadTesterException.class);
        thrown.expectMessage("Only 1 load test driver may be on the classpath at a time, but 2 were found.");

        supplier.get();
    }
}
