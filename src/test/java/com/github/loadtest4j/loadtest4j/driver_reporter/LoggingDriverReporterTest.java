package com.github.loadtest4j.loadtest4j.driver_reporter;

import com.github.loadtest4j.loadtest4j.junit.UnitTest;
import com.github.loadtest4j.loadtest4j.test_utils.NopOutputStream;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

@Category(UnitTest.class)
public class LoggingDriverReporterTest {
    @Test
    public void testShow() {
        // Given
        final PrintStreamSpy printStream = new PrintStreamSpy();
        final LoggingDriverReporter reporter = new LoggingDriverReporter(printStream);

        // When
        reporter.show("https://example.com");

        // Then
        assertEquals("Load test driver report URL: https://example.com", printStream.getMsg());
    }

    private static class PrintStreamSpy extends PrintStream {
        private String msg;

        private PrintStreamSpy() {
            super(new NopOutputStream());
        }

        @Override
        public void println(String msg) {
            this.msg = msg;
        }

        private String getMsg() {
            return msg;
        }
    }
}
