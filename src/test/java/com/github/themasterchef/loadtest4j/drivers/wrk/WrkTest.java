package com.github.themasterchef.loadtest4j.drivers.wrk;

import com.github.themasterchef.loadtest4j.LoadTester;
import com.github.themasterchef.loadtest4j.LoadTesterTest;
import com.github.themasterchef.loadtest4j.junit.IntegrationTest;
import org.junit.experimental.categories.Category;

import java.time.Duration;

@Category(IntegrationTest.class)
public class WrkTest extends LoadTesterTest {
    @Override
    public LoadTester sut(String serviceUrl) {
        final String executable = "wrk";
        return new Wrk(1, Duration.ofSeconds(1), executable, 1, serviceUrl);
    }
}
