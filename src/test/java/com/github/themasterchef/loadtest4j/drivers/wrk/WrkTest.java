package com.github.themasterchef.loadtest4j.drivers.wrk;

import com.github.themasterchef.loadtest4j.drivers.Driver;
import com.github.themasterchef.loadtest4j.drivers.DriverTest;
import com.github.themasterchef.loadtest4j.junit.IntegrationTest;
import org.junit.experimental.categories.Category;

import java.time.Duration;

@Category(IntegrationTest.class)
public class WrkTest extends DriverTest {
    @Override
    public Driver sut(String serviceUrl) {
        final String executable = "wrk";
        return new Wrk(1, Duration.ofSeconds(1), executable, 1, serviceUrl);
    }
}
