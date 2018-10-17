package org.loadtest4j.test_utils;

import org.loadtest4j.driver.DriverResult;

import java.time.Duration;

public class NopDriverResult implements DriverResult {

    @Override
    public long getOkRequestsBetween(Duration min, Duration max) {
        return 0;
    }

    @Override
    public Duration getResponseTimePercentile(double percentile) {
        return Duration.ZERO;
    }

    @Override
    public Duration getActualDuration() {
        return Duration.ZERO;
    }

    @Override
    public long getKo() {
        return 0;
    }

    @Override
    public long getOk() {
        return 0;
    }
}
