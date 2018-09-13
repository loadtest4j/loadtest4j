package org.loadtest4j.test_utils;

import org.loadtest4j.driver.DriverResponseTime;

import java.time.Duration;

public class TestResponseTime implements DriverResponseTime {

    private final Duration duration;

    public TestResponseTime(Duration duration) {
        this.duration = duration;
    }

    public static final DriverResponseTime ZERO = new TestResponseTime(Duration.ZERO);

    @Override
    public Duration getPercentile(int percentile) {
        return duration;
    }
}