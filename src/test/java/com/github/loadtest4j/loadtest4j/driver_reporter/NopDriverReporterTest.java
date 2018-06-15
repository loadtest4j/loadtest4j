package com.github.loadtest4j.loadtest4j.driver_reporter;

import com.github.loadtest4j.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(UnitTest.class)
public class NopDriverReporterTest {
    @Test
    public void testShow() {
        final NopDriverReporter reporter = new NopDriverReporter();

        reporter.show("foo");
    }
}
