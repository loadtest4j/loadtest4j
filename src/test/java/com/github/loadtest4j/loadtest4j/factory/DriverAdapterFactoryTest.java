package com.github.loadtest4j.loadtest4j.factory;

import com.github.loadtest4j.loadtest4j.LoadTester;
import com.github.loadtest4j.loadtest4j.LoadTesterException;
import com.github.loadtest4j.loadtest4j.driver.Driver;
import com.github.loadtest4j.loadtest4j.driver.DriverFactory;
import com.github.loadtest4j.loadtest4j.junit.UnitTest;
import com.github.loadtest4j.loadtest4j.test_utils.NopDriver;
import com.github.loadtest4j.loadtest4j.utils.ClassFinder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import java.util.*;
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
        properties.put("loadtest4j.driver", StubDriverFactory.class.getName());
        properties.put("loadtest4j.driver.bar", "1");
        properties.put("loadtest4j.driver.foo", "2");
        properties.put("loadtest4j.reporter.enabled", "true");
        final LoadTester loadTester = factory.create(properties);

        // Then
        assertThat(loadTester).isNotNull();
    }

    @Test
    public void testCreateDriverWithFindError() {
        final ClassFinder<DriverFactory> finder = new EmptyClassFinder<>();
        final DriverAdapterFactory factory = new DriverAdapterFactory(finder);

        thrown.expect(LoadTesterException.class);
        thrown.expectMessage("Invalid load test driver type.");

        factory.create(Collections.singletonMap("loadtest4j.driver", "foo"));
    }

    @Test
    public void testCreateDriverWithNoConfig() {
        final DriverAdapterFactory factory = DriverAdapterFactory.defaultFactory();

        thrown.expect(LoadTesterException.class);
        thrown.expectMessage("The driver config is missing. Add it to /loadtest4j.properties or the JVM properties.");

        factory.create(Collections.emptyMap());
    }

    @Test
    public void testCreateDriverWithMissingProperties() {
        final DriverAdapterFactory factory = DriverAdapterFactory.defaultFactory();

        thrown.expect(LoadTesterException.class);
        thrown.expectMessage("The following driver properties are missing: [loadtest4j.driver.bar, loadtest4j.driver.foo]. Add them to /loadtest4j.properties or the JVM properties.");

        factory.create(Collections.singletonMap("loadtest4j.driver", StubDriverFactory.class.getName()));
    }

    public static class StubDriverFactory implements DriverFactory {
        @Override
        public Set<String> getMandatoryProperties() {
            return new HashSet<>(Arrays.asList("bar", "foo"));
        }

        @Override
        public Driver create(Map<String, String> properties) {
            return new NopDriver();
        }
    }

    private static class EmptyClassFinder<DriverFactory> implements ClassFinder<DriverFactory> {
        @Override
        public Optional<DriverFactory> find(String className) {
            return Optional.empty();
        }
    }
}
