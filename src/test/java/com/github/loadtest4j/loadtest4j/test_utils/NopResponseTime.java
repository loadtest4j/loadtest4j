package com.github.loadtest4j.loadtest4j.test_utils;

import com.github.loadtest4j.loadtest4j.driver.DriverResponseTime;

import java.time.Duration;

public class NopResponseTime implements DriverResponseTime {
    @Override
    public Duration getPercentile(int percentile) {
        return Duration.ZERO;
    }
}
