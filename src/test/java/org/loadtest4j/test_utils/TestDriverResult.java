package org.loadtest4j.test_utils;

import org.loadtest4j.driver.DriverResponseTime;
import org.loadtest4j.driver.DriverResult;

import java.time.Duration;
import java.util.Optional;

public class TestDriverResult implements DriverResult {

    private final Duration actualDuration;
    private final long ko;
    private final long ok;
    private final DriverResponseTime responseTime;
    private final Optional<String> reportUrl;

    public static final DriverResult ZERO = new TestDriverResult(Duration.ZERO, 0, 0, FixedResponseTime.ZERO);

    public TestDriverResult(Duration actualDuration, long ok, long ko, DriverResponseTime responseTime) {
        this.ok = ok;
        this.ko = ko;
        this.actualDuration = actualDuration;
        this.responseTime = responseTime;
        this.reportUrl = Optional.empty();
    }

    public TestDriverResult(Duration actualDuration, long ok, long ko, DriverResponseTime responseTime, String reportUrl) {
        this.ok = ok;
        this.ko = ko;
        this.actualDuration = actualDuration;
        this.responseTime = responseTime;
        this.reportUrl = Optional.of(reportUrl);
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

    @Override
    public Optional<String> getReportUrl() {
        return reportUrl;
    }
}
