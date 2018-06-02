package com.github.loadtest4j.loadtest4j.driver_reporter;

import com.github.loadtest4j.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.assertEquals;

@Category(UnitTest.class)
public class LoggingDriverReporterTest {
    @Test
    public void testShow() {
        // Given
        final LoggingDriverReporterSpy reporter = new LoggingDriverReporterSpy();

        // When
        reporter.show("https://example.com");

        // Then
        assertEquals("The driver has generated a custom report. This is available at the following URL: https://example.com", reporter.getPrintedMsg());
    }

    private static class LoggingDriverReporterSpy extends LoggingDriverReporter {

        private String printedMsg;

        @Override
        protected void print(String msg) {
            printedMsg = msg;
        }

        String getPrintedMsg() {
            return printedMsg;
        }
    }
}
