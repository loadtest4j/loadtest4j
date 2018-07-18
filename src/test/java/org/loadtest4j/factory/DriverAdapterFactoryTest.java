package org.loadtest4j.factory;

import org.loadtest4j.LoadTester;
import org.loadtest4j.LoadTesterException;
import org.loadtest4j.junit.IntegrationTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;

@Category(IntegrationTest.class)
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
    public void testCreateDriverWithMissingProperties() {
        final DriverAdapterFactory factory = DriverAdapterFactory.defaultFactory();

        thrown.expect(LoadTesterException.class);
        thrown.expectMessage("The following driver properties are missing: [loadtest4j.driver.bar, loadtest4j.driver.foo]. Add them to /loadtest4j.properties or the JVM properties.");

        factory.create(Collections.emptyMap());
    }
}
