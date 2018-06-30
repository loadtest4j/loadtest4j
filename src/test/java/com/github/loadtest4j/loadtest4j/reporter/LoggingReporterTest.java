package com.github.loadtest4j.loadtest4j.reporter;

import com.github.loadtest4j.loadtest4j.junit.UnitTest;
import com.github.loadtest4j.loadtest4j.test_utils.NopOutputStream;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.PrintStream;

import static org.assertj.core.api.Assertions.*;

@Category(UnitTest.class)
public class LoggingReporterTest {
    @Test
    public void testShow() {
        // Given
        final PrintStreamSpy printStream = new PrintStreamSpy();
        final LoggingReporter reporter = new LoggingReporter(printStream);

        // When
        reporter.show("https://example.com");

        // Then
        assertThat(printStream.getMsg()).isEqualTo("Load test driver report URL: https://example.com");
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
