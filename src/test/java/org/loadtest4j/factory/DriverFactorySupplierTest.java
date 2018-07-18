package org.loadtest4j.factory;

import org.loadtest4j.LoadTesterException;
import org.loadtest4j.driver.DriverFactory;
import org.loadtest4j.junit.UnitTest;
import org.loadtest4j.test_utils.NopDriverFactory;
import org.loadtest4j.utils.ClassFinder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class DriverFactorySupplierTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testGet() {
        final DriverFactorySupplier supplier = new DriverFactorySupplier(new SingleClassFinder());

        final DriverFactory factory = supplier.get();

        assertThat(factory).isInstanceOf(NopDriverFactory.class);
    }

    @Test
    public void testGetWithNoDriverFactories() {
        final DriverFactorySupplier supplier = new DriverFactorySupplier(new EmptyClassFinder());

        thrown.expect(LoadTesterException.class);
        thrown.expectMessage("No load test drivers were found on the classpath. Please add one to your project.");

        supplier.get();
    }

    @Test
    public void testGetWithMoreThanOneDriverFactory() {
        final DriverFactorySupplier supplier = new DriverFactorySupplier(new DuplicateClassFinder());

        thrown.expect(LoadTesterException.class);
        thrown.expectMessage("Only 1 load test driver may be on the classpath at a time, but 2 were found.");

        supplier.get();
    }

    @Test
    public void testFindWithInstantiationErrors() {
        final DriverFactorySupplier supplier = new DriverFactorySupplier(new BrokenClassFinder());

        thrown.expect(LoadTesterException.class);
        thrown.expectMessage("Could not instantiate the load test driver " + CImpl.class.getName());

        supplier.get();
    }

    private static class BrokenClassFinder implements ClassFinder {
        @Override
        public <T> Collection<Class<T>> findImplementationsOf(Class<T> anInterface) {
            return Collections.singletonList((Class<T>) CImpl.class);
        }
    }

    private static class EmptyClassFinder implements ClassFinder {
        @Override
        public <T> Collection<Class<T>> findImplementationsOf(Class<T> anInterface) {
            return Collections.emptyList();
        }
    }

    private static class SingleClassFinder implements ClassFinder {
        @Override
        public <T> Collection<Class<T>> findImplementationsOf(Class<T> anInterface) {
            return Collections.singletonList((Class<T>) NopDriverFactory.class);
        }
    }

    private static class DuplicateClassFinder implements ClassFinder {
        @Override
        public <T> Collection<Class<T>> findImplementationsOf(Class<T> anInterface) {
            return Arrays.asList((Class<T>) NopDriverFactory.class, (Class<T>) NopDriverFactory.class);
        }
    }

    private interface C {

    }

    private static class CImpl implements C {
        // 1. Attempting instantiation with a private constructor causes an InstantiationException.
        // 2. We need to trigger an InstantiationException before it works out that 'C' is not a 'DriverFactory'.
        private CImpl() {}
    }
}
