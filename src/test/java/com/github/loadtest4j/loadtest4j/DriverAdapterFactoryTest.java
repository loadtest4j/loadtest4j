package com.github.loadtest4j.loadtest4j;

import com.github.loadtest4j.loadtest4j.junit.UnitTest;
import com.github.loadtest4j.loadtest4j.test_utils.NopDriver;
import com.github.loadtest4j.loadtest4j.test_utils.NopDriverFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.assertNotNull;

@Category(UnitTest.class)
public class DriverAdapterFactoryTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCreateDriverWithNoDriverFactories() {
        final DriverAdapterFactory.DriverFactoryScanner driverFactories = new MockDriverFactoryScanner();
        final DriverAdapterFactory factory = new DriverAdapterFactory(driverFactories);

        thrown.expect(LoadTesterException.class);
        thrown.expectMessage("No load test drivers were found. Please add one or more drivers to your project.");

        factory.create(Collections.emptyMap());
    }

    @Test
    public void testCreateDriverWithInvalidDriverFactorySpecified() {
        final DriverFactory stubFactory = new StubDriverFactory();
        final DriverAdapterFactory.DriverFactoryScanner driverFactories = new MockDriverFactoryScanner()
                .add(stubFactory);
        final DriverAdapterFactory factory = new DriverAdapterFactory(driverFactories);

        thrown.expect(LoadTesterException.class);
        thrown.expectMessage("Invalid load test driver type.");

        factory.create(Collections.singletonMap("loadtest4j.driver", "foo"));
    }

    @Test
    public void testCreateDriverWithNoConfig() {
        final DriverAdapterFactory.DriverFactoryScanner driverFactories = new MockDriverFactoryScanner()
                .add(new NopDriverFactory());
        final DriverAdapterFactory factory = new DriverAdapterFactory(driverFactories);

        thrown.expect(LoadTesterException.class);
        thrown.expectMessage("The following load test driver properties were not found: [loadtest4j.driver]. Please specify them either as JVM properties or in loadtest4j.properties.");

        factory.create(Collections.emptyMap());
    }

    @Test
    public void testCreateDriverWithMissingProperties() {
        final DriverFactory stubFactory = new StubDriverFactory();
        final DriverAdapterFactory.DriverFactoryScanner driverFactories = new MockDriverFactoryScanner()
                .add(stubFactory);
        final DriverAdapterFactory factory = new DriverAdapterFactory(driverFactories);

        thrown.expect(LoadTesterException.class);
        thrown.expectMessage("The following load test driver properties were not found: [bar, foo]. Please specify them either as JVM properties or in loadtest4j.properties.");

        factory.create(Collections.singletonMap("loadtest4j.driver", stubFactory.getClass().getName()));
    }

    @Test
    public void testCreateDriver() {
        // Given
        final DriverFactory stubFactory = new StubDriverFactory();
        final DriverAdapterFactory.DriverFactoryScanner driverFactories = new MockDriverFactoryScanner()
                .add(stubFactory);
        final DriverAdapterFactory factory = new DriverAdapterFactory(driverFactories);

        // When
        final Map<String, String> properties = new ConcurrentHashMap<>();
        properties.put("loadtest4j.driver", stubFactory.getClass().getName());
        properties.put("loadtest4j.driver.bar", "1");
        properties.put("loadtest4j.driver.foo", "2");
        properties.put("loadtest4j.reporter.enabled", "true");
        final LoadTester loadTester = factory.create(properties);

        // Then
        assertNotNull(loadTester);
    }

    private static class MockDriverFactoryScanner implements DriverAdapterFactory.DriverFactoryScanner {
        private final Collection<DriverFactory> factories = new ArrayList<>();

        private DriverAdapterFactoryTest.MockDriverFactoryScanner add(DriverFactory factory) {
            factories.add(factory);
            return this;
        }

        @Override
        public Optional<DriverFactory> findFirst(String className) {
            return factories.stream()
                    .filter(factory -> factory.getClass().getName().equals(className))
                    .findFirst();
        }

        @Override
        public boolean isEmpty() {
            return factories.isEmpty();
        }
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
}
