package org.loadtest4j.test_utils;

import org.loadtest4j.driver.DriverResponseTime;

import java.time.Duration;

public class NopResponseTime implements DriverResponseTime {

    @Override
    public Duration getPercentile(double percentile) {
        return Duration.ZERO;
    }
}