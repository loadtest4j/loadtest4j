package org.loadtest4j.reporter;

import org.loadtest4j.driver.DriverResponseTime;
import org.loadtest4j.driver.DriverResult;
import org.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadtest4j.test_utils.TestResponseTime;
import org.loadtest4j.test_utils.SpyLogger;
import org.loadtest4j.test_utils.TestDriverResult;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class LoggingReporterTest {

    private static final Duration ACTUAL_DURATION = Duration.ofMillis(Long.MAX_VALUE);
    private static final long KO = Long.MAX_VALUE / 2;
    private static final long OK = Long.MAX_VALUE / 2;
    private static final DriverResponseTime RESPONSE_TIME = new TestResponseTime(Duration.ofMillis(Long.MAX_VALUE));

    @Test
    public void testShow() {
        // Given
        final SpyLogger logger = new SpyLogger();
        final LoggingReporter reporter = new LoggingReporter(logger);
        final DriverResult driverResult = new TestDriverResult(ACTUAL_DURATION, OK, KO, RESPONSE_TIME, "file:///path/to/example/report/index.html");

        // When
        reporter.show(driverResult);

        // Then
        assertThat(logger.getMsg()).isEqualTo(getReport("show"));
    }

    @Test
    public void testShowWithoutReportUrl() {
        // Given
        final SpyLogger logger = new SpyLogger();
        final LoggingReporter reporter = new LoggingReporter(logger);
        final DriverResult driverResult = new TestDriverResult(ACTUAL_DURATION, OK, KO, RESPONSE_TIME);

        // When
        reporter.show(driverResult);

        // Then
        assertThat(logger.getMsg()).isEqualTo(getReport("show-no-report-url"));
    }

    @Test
    public void testGetRequestsPerSecond() {
        assertThat(LoggingReporter.getRequestsPerSecond(10, Duration.ofMillis(2500))).isEqualTo(4);
    }

    @Test
    public void testGetRequestsPerSecondAvoidsDivideByZero() {
        assertThat(LoggingReporter.getRequestsPerSecond(2, Duration.ZERO)).isEqualTo(0);
    }

    private static String streamToString(InputStream is) {
        // From https://stackoverflow.com/a/5445161
        final Scanner s = new Scanner(is, StandardCharsets.UTF_8.name()).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    private static String getReport(String name) {
        return streamToString(LoggingReporterTest.class.getClassLoader().getResourceAsStream("reports/" + name + ".md"));
    }

}
