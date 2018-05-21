package com.github.themasterchef.loadtest4j.drivers.wrk;

import com.github.themasterchef.loadtest4j.Driver;
import com.github.themasterchef.loadtest4j.DriverTest;
import com.github.themasterchef.loadtest4j.LoadTesterException;
import com.github.themasterchef.loadtest4j.junit.IntegrationTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.time.Duration;
import java.util.Collections;

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

        // Expect
        thrown.expect(LoadTesterException.class);
        thrown.expectMessage("No requests were specified for the load test.");

        // When
        driver.run(Collections.emptyList());
    }
}
