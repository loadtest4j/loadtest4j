package com.github.loadtest4j.loadtest4j;

import com.github.loadtest4j.loadtest4j.driver_reporter.DriverReporter;
import com.github.loadtest4j.loadtest4j.junit.UnitTest;
import com.github.loadtest4j.loadtest4j.test_utils.NopDriver;
import com.github.loadtest4j.loadtest4j.test_utils.StubDriver;
import com.github.loadtest4j.loadtest4j.test_utils.TestDriverResult;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@Category(UnitTest.class)
public class ReportingDriverTest {
    @Test
    public void testRunWithReport() {
        // Given
        final SpyDriverReporter spyReporter = new SpyDriverReporter();
        final String reportUrl = "https://example.com";
        final StubDriver stubDriver = new StubDriver();
        stubDriver.expectRun(new TestDriverResult(0, 0, reportUrl));
        final Driver driver = new ReportingDriver(stubDriver, spyReporter);

        // When
        driver.run(Collections.emptyList());

        // Then
        assertEquals(reportUrl, spyReporter.getReportedUrl());
    }

    @Test
    public void testRunWithoutReport() {
        // Given
        final SpyDriverReporter spyReporter = new SpyDriverReporter();
        final Driver driver = new ReportingDriver(new NopDriver(), spyReporter);

        // When
        driver.run(Collections.emptyList());

        // Then
        assertNull(spyReporter.getReportedUrl());
    }

    private static class SpyDriverReporter implements DriverReporter {

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
