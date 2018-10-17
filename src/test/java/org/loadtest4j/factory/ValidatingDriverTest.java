package org.loadtest4j.factory;

import org.loadtest4j.LoadTesterException;
import org.loadtest4j.driver.Driver;
import org.loadtest4j.driver.DriverResult;
import org.loadtest4j.junit.UnitTest;
import org.loadtest4j.test_utils.StubDriver;
import org.loadtest4j.test_utils.NopDriverResult;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import java.time.Duration;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class ValidatingDriverTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testRun() {
        // Given
        final StubDriver stubDriver = new StubDriver();
        final Driver sut = new ValidatingDriver(stubDriver);
        // And
        final DriverResult expectedResult = new NopDriverResult();
        stubDriver.expectRun(expectedResult);

        // When
        final DriverResult actualResult = sut.run(Collections.emptyList());

        // Then
        assertThat(expectedResult).isEqualTo(actualResult);
    }

    @Test
    public void testRunWithNegativeOkValue() {
        // Given
        final StubDriver stubDriver = new StubDriver();
        final Driver sut = new ValidatingDriver(stubDriver);
        // And
        stubDriver.expectRun(new DriverResult() {
            @Override
            public Duration getActualDuration() {
                return Duration.ZERO;
            }

            @Override
            public long getKo() {
                return 0;
            }

            @Override
            public long getOk() {
                return -1;
            }

            @Override
            public long getOkRequestsBetween(Duration min, Duration max) {
                return 0;
            }

            @Override
            public Duration getResponseTimePercentile(double percentile) {
                return Duration.ZERO;
            }
        });

        // Expect
        thrown.expect(LoadTesterException.class);
        thrown.expectMessage("The load test driver returned a negative number of OK requests.");

        // When
        sut.run(Collections.emptyList());
    }

    @Test
    public void testRunWithNegativeKoValue() {
        // Given
        final StubDriver stubDriver = new StubDriver();
        final Driver sut = new ValidatingDriver(stubDriver);
        // And
        stubDriver.expectRun(new DriverResult() {
            @Override
            public Duration getActualDuration() {
                return Duration.ZERO;
            }

            @Override
            public long getKo() {
                return -1;
            }

            @Override
            public long getOk() {
                return 0;
            }

            @Override
            public long getOkRequestsBetween(Duration min, Duration max) {
                return 0;
            }

            @Override
            public Duration getResponseTimePercentile(double percentile) {
                return Duration.ZERO;
            }
        });

        // Expect
        thrown.expect(LoadTesterException.class);
        thrown.expectMessage("The load test driver returned a negative number of KO requests.");

        // When
        sut.run(Collections.emptyList());
    }
}
