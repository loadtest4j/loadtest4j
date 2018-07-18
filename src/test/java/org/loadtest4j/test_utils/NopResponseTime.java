package org.loadtest4j.test_utils;

import org.loadtest4j.driver.DriverResponseTime;

import java.time.Duration;

public class NopResponseTime implements DriverResponseTime {
    @Override
    public Duration getPercentile(int percentile) {
        return Duration.ZERO;
    }
}
