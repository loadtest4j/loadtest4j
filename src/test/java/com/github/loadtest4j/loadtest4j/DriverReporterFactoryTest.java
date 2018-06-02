package com.github.loadtest4j.loadtest4j;

import com.github.loadtest4j.loadtest4j.driver_reporter.DriverReporter;
import com.github.loadtest4j.loadtest4j.driver_reporter.LoggingDriverReporter;
import com.github.loadtest4j.loadtest4j.driver_reporter.NopDriverReporter;
import com.github.loadtest4j.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Collections;

import static org.junit.Assert.assertTrue;

@Category(UnitTest.class)
public class DriverReporterFactoryTest {

    @Test
    public void testCreate() {
        // Given
        final DriverReporterFactory factory = new DriverReporterFactory();

        // When
        final DriverReporter reporter = factory.create(Collections.singletonMap("enabled", "true"));

        // Then
        assertTrue(reporter instanceof LoggingDriverReporter);
    }

    @Test
    public void testCreateWithNoConfig() {
        // Given
        final DriverReporterFactory factory = new DriverReporterFactory();

        // When
        final DriverReporter reporter = factory.create(Collections.emptyMap());

        // Then
        assertTrue(reporter instanceof NopDriverReporter);
    }

    @Test
    public void testCreateWithInvalidConfig() {
        // Given
        final DriverReporterFactory factory = new DriverReporterFactory();

        // When
        final DriverReporter reporter = factory.create(Collections.singletonMap("enabled", "foo"));

        // Then
        assertTrue(reporter instanceof NopDriverReporter);
    }
}
