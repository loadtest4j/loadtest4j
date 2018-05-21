package com.github.loadtest4j.loadtest4j.drivers.wrk;

import com.github.loadtest4j.loadtest4j.Driver;
import com.github.loadtest4j.loadtest4j.DriverTest;
import com.github.loadtest4j.loadtest4j.LoadTesterException;
import com.github.loadtest4j.loadtest4j.junit.IntegrationTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.time.Duration;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@Category(IntegrationTest.class)
public class WrkTest extends DriverTest {
    @Override
    public Driver sut(String serviceUrl) {
        final String executable = "wrk";
        return new Wrk(1, Duration.ofSeconds(1), executable, 1, serviceUrl);
    }

    @Test
    public void testRunWithNoRequests() {
        // Given
        final Driver driver = sut();

        // When
        try {
            driver.run(Collections.emptyList());

            fail("This should not work.");
        } catch (LoadTesterException e) {
            // Then
            assertEquals("No requests were specified for the load test.", e.getMessage());
        }
    }
}
