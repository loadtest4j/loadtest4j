package com.github.loadtest4j.loadtest4j.factory;

import com.github.loadtest4j.loadtest4j.LoadTester;
import com.github.loadtest4j.loadtest4j.LoadTesterException;
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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class DriverAdapterFactoryTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCreateDriver() {
        // Given
        final DriverAdapterFactory factory = DriverAdapterFactory.defaultFactory();

        // When
        final Map<String, String> properties = new ConcurrentHashMap<>();
        properties.put("loadtest4j.driver.bar", "1");
        properties.put("loadtest4j.driver.foo", "2");
        properties.put("loadtest4j.reporter.enabled", "true");
        final LoadTester loadTester = factory.create(properties);

        // Then
        assertThat(loadTester).isNotNull();
    }

    @Test
    public void testCreateDriverWithNoDriverFactories() {
        final ClassFinder finder = new EmptyClassFinder();
        final DriverAdapterFactory factory = new DriverAdapterFactory(finder);

        thrown.expect(LoadTesterException.class);
        thrown.expectMessage("No load test drivers were found on the classpath. Please add one to your project.");

        factory.create(Collections.emptyMap());
    }

    @Test
    public void testCreateDriverWithMoreThanOneDriverFactory() {
        final ClassFinder finder = new DuplicateClassFinder();
        final DriverAdapterFactory factory = new DriverAdapterFactory(finder);

        thrown.expect(LoadTesterException.class);
        thrown.expectMessage("Only 1 load test driver may be on the classpath at a time, but 2 were found.");

        factory.create(Collections.emptyMap());
    }

    @Test
    public void testCreateDriverWithMissingProperties() {
        final DriverAdapterFactory factory = DriverAdapterFactory.defaultFactory();

        thrown.expect(LoadTesterException.class);
        thrown.expectMessage("The following driver properties are missing: [loadtest4j.driver.bar, loadtest4j.driver.foo]. Add them to /loadtest4j.properties or the JVM properties.");

        factory.create(Collections.emptyMap());
    }

    private static class EmptyClassFinder implements ClassFinder {
        @Override
        public <T> Collection<T> findImplementationsOf(Class<T> anInterface) {
            return Collections.emptyList();
        }
    }

    private static class DuplicateClassFinder implements ClassFinder {

        @Override
        public <T> Collection<T> findImplementationsOf(Class<T> anInterface) {
            return Arrays.asList((T) new NopDriverFactory(), (T) new NopDriverFactory());
        }
    }
}
