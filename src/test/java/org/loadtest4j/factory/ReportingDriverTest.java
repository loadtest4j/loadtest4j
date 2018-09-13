package org.loadtest4j.factory;

import org.loadtest4j.driver.Driver;
import org.loadtest4j.driver.DriverResult;
import org.loadtest4j.reporter.Reporter;
import org.loadtest4j.junit.UnitTest;
import org.loadtest4j.test_utils.StubDriver;
import org.loadtest4j.test_utils.TestDriverResult;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class ReportingDriverTest {
    @Test
    public void testRun() {
        // Given
        final SpyReporter spyReporter = new SpyReporter();
        final StubDriver stubDriver = new StubDriver();
        final DriverResult driverResult = TestDriverResult.ZERO;
        stubDriver.expectRun(driverResult);
        final Driver driver = new ReportingDriver(stubDriver, spyReporter);

        // When
        driver.run(Collections.emptyList());

        // Then
        assertThat(spyReporter.driverResult).isEqualTo(driverResult);
    }

    private static class SpyReporter implements Reporter {

        private DriverResult driverResult;

        @Override
        public void show(DriverResult driverResult) {
            this.driverResult = driverResult;
        }
    }
}
