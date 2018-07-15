package com.github.loadtest4j.loadtest4j.factory;

import com.github.loadtest4j.loadtest4j.LoadTesterException;
import com.github.loadtest4j.loadtest4j.driver.DriverFactory;
import com.github.loadtest4j.loadtest4j.junit.UnitTest;
import com.github.loadtest4j.loadtest4j.test_utils.NopDriverFactory;
import com.github.loadtest4j.loadtest4j.utils.ClassFinder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class DriverFactoryFinderTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testFind() {
        final DriverFactoryFinder finder = new DriverFactoryFinder(new SingleClassFinder());

        final DriverFactory factory = finder.find();

        assertThat(factory).isInstanceOf(NopDriverFactory.class);
    }

    @Test
    public void testFindWithNoDriverFactories() {
        final DriverFactoryFinder finder = new DriverFactoryFinder(new EmptyClassFinder());

        thrown.expect(LoadTesterException.class);
        thrown.expectMessage("No load test drivers were found on the classpath. Please add one to your project.");

        finder.find();
    }

    @Test
    public void testFindWithMoreThanOneDriverFactory() {
        final DriverFactoryFinder finder = new DriverFactoryFinder(new DuplicateClassFinder());

        thrown.expect(LoadTesterException.class);
        thrown.expectMessage("Only 1 load test driver may be on the classpath at a time, but 2 were found.");

        finder.find();
    }

    private static class EmptyClassFinder implements ClassFinder {
        @Override
        public <T> Collection<T> findImplementationsOf(Class<T> anInterface) {
            return Collections.emptyList();
        }
    }

    private static class SingleClassFinder implements ClassFinder {
        @Override
        public <T> Collection<T> findImplementationsOf(Class<T> anInterface) {
            return Arrays.asList((T) new NopDriverFactory());
        }
    }

    private static class DuplicateClassFinder implements ClassFinder {
        @Override
        public <T> Collection<T> findImplementationsOf(Class<T> anInterface) {
            return Arrays.asList((T) new NopDriverFactory(), (T) new NopDriverFactory());
        }
    }
}
