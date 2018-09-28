package org.loadtest4j.test_utils;

import org.loadtest4j.driver.DriverResponseTime;
import org.loadtest4j.driver.DriverResult;

import java.time.Duration;

public class TestDriverResult implements DriverResult {

    private final Duration actualDuration;
    private final long ko;
    private final long ok;
    private final DriverResponseTime responseTime;

    public static DriverResult zero() {
        return new TestDriverResult(Duration.ZERO, 0, 0, TestResponseTime.ZERO);
    }

    public TestDriverResult(Duration actualDuration, long ok, long ko, DriverResponseTime responseTime) {
        this.ok = ok;
        this.ko = ko;
        this.actualDuration = actualDuration;
        this.responseTime = responseTime;
    }

    @Override
    public long getOk() {
        return ok;
    }

    @Override
    public long getKo() {
        return ko;
    }

    @Override
    public Duration getActualDuration() {
        return actualDuration;
    }

    @Override
    public DriverResponseTime getResponseTime() {
        return responseTime;
    }
}
