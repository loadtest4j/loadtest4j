package org.loadtest4j.test_utils;

import org.loadtest4j.driver.DriverApdex;
import org.loadtest4j.driver.DriverResponseTime;
import org.loadtest4j.driver.DriverResult;

import java.time.Duration;

public class TestDriverResult implements DriverResult {

    private final Duration actualDuration;
    private final DriverApdex apdex;
    private final long ko;
    private final long ok;
    private final DriverResponseTime responseTime;

    public static DriverResult zero() {
        return new TestDriverResult(Duration.ZERO, new NopApdex(), 0, 0, new NopResponseTime());
    }

    public TestDriverResult(Duration actualDuration, DriverApdex apdex, long ok, long ko, DriverResponseTime responseTime) {
        this.actualDuration = actualDuration;
        this.apdex = apdex;
        this.ok = ok;
        this.ko = ko;
        this.responseTime = responseTime;
    }

    @Override
    public DriverApdex getApdex() {
        return apdex;
    }

    @Override
    public Duration getActualDuration() {
        return actualDuration;
    }

    @Override
    public long getKo() {
        return ko;
    }

    @Override
    public long getOk() {
        return ok;
    }

    @Override
    public DriverResponseTime getResponseTime() {
        return responseTime;
    }
}
