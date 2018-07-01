package com.github.loadtest4j.loadtest4j.factory;

import com.github.loadtest4j.loadtest4j.reporter.Reporter;
import com.github.loadtest4j.loadtest4j.reporter.LoggingReporter;
import com.github.loadtest4j.loadtest4j.reporter.NopReporter;
import com.github.loadtest4j.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class ReporterFactoryTest {

    @Test
    public void testCreate() {
        // Given
        final ReporterFactory factory = new ReporterFactory();

        // When
        final Reporter reporter = factory.create(Collections.singletonMap("enabled", "true"));

        // Then
        assertThat(reporter).isInstanceOf(LoggingReporter.class);
    }

    @Test
    public void testCreateWithNoConfig() {
        // Given
        final ReporterFactory factory = new ReporterFactory();

        // When
        final Reporter reporter = factory.create(Collections.emptyMap());

        // Then
        assertThat(reporter).isInstanceOf(NopReporter.class);
    }

    @Test
    public void testCreateWithInvalidConfig() {
        // Given
        final ReporterFactory factory = new ReporterFactory();

        // When
        final Reporter reporter = factory.create(Collections.singletonMap("enabled", "foo"));

        // Then
        assertThat(reporter).isInstanceOf(NopReporter.class);
    }
}
