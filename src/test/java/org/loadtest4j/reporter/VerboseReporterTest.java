package org.loadtest4j.reporter;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadtest4j.driver.DriverResponseTime;
import org.loadtest4j.driver.DriverResult;
import org.loadtest4j.junit.UnitTest;
import org.loadtest4j.test_utils.PrintStreamSpy;

import java.time.Duration;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class VerboseReporterTest {
    @Test
    public void testShow() {
        // Given
        final PrintStreamSpy printStream = new PrintStreamSpy();
        final VerboseReporter reporter = new VerboseReporter(printStream);

        // When
        reporter.show(new HugeDriverResult());

        // Then
        assertThat(printStream.getLines()).containsExactly(
                "================================================================================",
                "---- Global Information --------------------------------------------------------",
                "> duration                                                   9223372036854775807",
                "> mean requests/sec                                                  1000.000000",
                "---- Request Count -------------------------------------------------------------",
                "> total                                                      9223372036854775806",
                "> ok                                                         4611686018427387903",
                "> ko                                                         4611686018427387903",
                "---- Response Time Distribution ------------------------------------------------",
                "> min                                                        9223372036854775807",
                "> 50th percentile                                            9223372036854775807",
                "> 75th percentile                                            9223372036854775807",
                "> 95th percentile                                            9223372036854775807",
                "> 99th percentile                                            9223372036854775807",
                "> max                                                        9223372036854775807",
                "================================================================================"
        );
    }

    @Test
    public void testGetRequestsPerSecond() {
        assertThat(VerboseReporter.getRequestsPerSecond(10, Duration.ofMillis(2500))).isEqualTo(4);
    }

    @Test
    public void testGetRequestsPerSecondAvoidsDivideByZero() {
        assertThat(VerboseReporter.getRequestsPerSecond(2, Duration.ZERO)).isEqualTo(0);
    }

    private static class HugeDriverResult implements DriverResult {

        @Override
        public long getOk() {
            return Long.MAX_VALUE / 2;
        }

        @Override
        public long getKo() {
            return Long.MAX_VALUE / 2;
        }

        @Override
        public Duration getActualDuration() {
            return Duration.ofMillis(Long.MAX_VALUE);
        }

        @Override
        public DriverResponseTime getResponseTime() {
            return new HugeResponseTime();
        }

        @Override
        public Optional<String> getReportUrl() {
            return Optional.empty();
        }
    }

    private static class HugeResponseTime implements DriverResponseTime {

        @Override
        public Duration getPercentile(int percentile) {
            return Duration.ofMillis(Long.MAX_VALUE);
        }
    }
}
