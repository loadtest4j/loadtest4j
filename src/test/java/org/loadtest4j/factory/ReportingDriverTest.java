package org.loadtest4j.factory;

import org.loadtest4j.driver.Driver;
import org.loadtest4j.reporter.Reporter;
import org.loadtest4j.junit.UnitTest;
import org.loadtest4j.test_utils.NopDriver;
import org.loadtest4j.test_utils.StubDriver;
import org.loadtest4j.test_utils.TestDriverResult;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@Category(UnitTest.class)
public class ReportingDriverTest {
    @Test
    public void testRunWithReport() {
        // Given
        final SpyReporter spyReporter = new SpyReporter();
        final String reportUrl = "https://example.com";
        final StubDriver stubDriver = new StubDriver();
        stubDriver.expectRun(TestDriverResult.zero(reportUrl));
        final Driver driver = new ReportingDriver(stubDriver, spyReporter);

        // When
        driver.run(Collections.emptyList());

        // Then
        assertThat(spyReporter.getReportedUrl()).isEqualTo(reportUrl);
    }

    @Test
    public void testRunWithoutReport() {
        // Given
        final SpyReporter spyReporter = new SpyReporter();
        final Driver driver = new ReportingDriver(new NopDriver(), spyReporter);

        // When
        driver.run(Collections.emptyList());

        // Then
        assertThat(spyReporter.getReportedUrl()).isNull();
    }

    private static class SpyReporter implements Reporter {

        private String reportedUrl;

        @Override
        public void show(String reportUrl) {
            this.reportedUrl = reportUrl;
        }

        private String getReportedUrl() {
            return reportedUrl;
        }
    }
}
