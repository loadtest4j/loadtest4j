package com.github.loadtest4j.loadtest4j.factory;

import com.github.loadtest4j.loadtest4j.driver.Driver;
import com.github.loadtest4j.loadtest4j.junit.UnitTest;
import com.github.loadtest4j.loadtest4j.test_utils.NopDriver;
import com.github.loadtest4j.loadtest4j.test_utils.StubDriver;
import com.github.loadtest4j.loadtest4j.test_utils.TestDriverReport;
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
        final SpyReporter spyReporter = new SpyReporter();
        final String url = "https://example.com";
        final StubDriver stubDriver = new StubDriver();
        stubDriver.expectRun(new TestDriverReport(0, 0, url));
        final Driver driver = new ReportingDriver(stubDriver, spyReporter);

        // When
        driver.run(Collections.emptyList());

        // Then
        assertEquals(url, spyReporter.getUrl());
    }

    @Test
    public void testRunWithoutReport() {
        // Given
        final SpyReporter spyReporter = new SpyReporter();
        final Driver driver = new ReportingDriver(new NopDriver(), spyReporter);

        // When
        driver.run(Collections.emptyList());

        // Then
        assertNull(spyReporter.getUrl());
    }

    private static class SpyReporter implements Reporter {

        private String url;

        @Override
        public void show(String reportUrl) {
            this.url = reportUrl;
        }

        private String getUrl() {
            return url;
        }
    }
}
