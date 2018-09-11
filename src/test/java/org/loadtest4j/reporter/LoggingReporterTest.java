package org.loadtest4j.reporter;

import org.loadtest4j.driver.DriverResponseTime;
import org.loadtest4j.driver.DriverResult;
import org.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadtest4j.test_utils.PrintStreamSpy;

import java.time.Duration;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class LoggingReporterTest {
    @Test
    public void testShow() {
        // Given
        final PrintStreamSpy printStream = new PrintStreamSpy();
        final LoggingReporter reporter = new LoggingReporter(printStream);

        // When
        reporter.show(new StubDriverResult("https://example.com"));

        // Then
        assertThat(printStream.getMsg()).isEqualTo("Load test driver report URL: https://example.com");
    }

    @Test
    public void testShowWithoutReportUrl() {
        // Given
        final PrintStreamSpy printStream = new PrintStreamSpy();
        final LoggingReporter reporter = new LoggingReporter(printStream);

        // When
        reporter.show(new StubDriverResult());

        // Then
        assertThat(printStream.getMsg()).isNull();
    }

    private static class StubDriverResult implements DriverResult {

        private final Optional<String> reportUrl;

        StubDriverResult() {
            this.reportUrl = Optional.empty();
        }

        StubDriverResult(String reportUrl) {
            this.reportUrl = Optional.of(reportUrl);
        }

        @Override
        public long getOk() {
            throw new UnsupportedOperationException();
        }

        @Override
        public long getKo() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Duration getActualDuration() {
            throw new UnsupportedOperationException();
        }

        @Override
        public DriverResponseTime getResponseTime() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Optional<String> getReportUrl() {
            return reportUrl;
        }
    }

}
