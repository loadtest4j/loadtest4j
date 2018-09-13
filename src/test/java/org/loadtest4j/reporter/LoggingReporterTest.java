package org.loadtest4j.reporter;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadtest4j.junit.UnitTest;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class LoggingReporterTest {
    @Test
    public void testShow() {
        // Given
        final SpyLogger logger = new SpyLogger();
        final LoggingReporter reporter = new LoggingReporter(logger);

        // When
        reporter.show("https://example.com");

        // Then
        assertThat(logger.lines).containsExactly("Driver report URL: https://example.com");
    }

    private static class SpyLogger implements Consumer<String> {

        private final List<String> lines = new ArrayList<>();

        @Override
        public void accept(String msg) {
            this.lines.add(msg);
        }
    }
}
