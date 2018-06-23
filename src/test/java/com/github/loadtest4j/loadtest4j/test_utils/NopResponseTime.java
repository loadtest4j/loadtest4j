package com.github.loadtest4j.loadtest4j.test_utils;

import com.github.loadtest4j.loadtest4j.ResponseTime;

import java.time.Duration;

public class NopResponseTime implements ResponseTime {
    @Override
    public Duration getPercentile(int percentile) {
        return Duration.ZERO;
    }
}
