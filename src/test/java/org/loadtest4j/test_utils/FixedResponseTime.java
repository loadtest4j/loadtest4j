package org.loadtest4j.test_utils;

import org.loadtest4j.driver.DriverResponseTime;

import java.time.Duration;

public class FixedResponseTime implements DriverResponseTime {

    private final Duration duration;

    public FixedResponseTime(Duration duration) {
        this.duration = duration;
    }

    public static final DriverResponseTime ZERO = new FixedResponseTime(Duration.ZERO);

    @Override
    public Duration getPercentile(int percentile) {
        return duration;
    }
}
