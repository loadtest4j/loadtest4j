package com.github.loadtest4j.loadtest4j.test_utils;

import com.github.loadtest4j.loadtest4j.driver.DriverResponseTime;
import com.github.loadtest4j.loadtest4j.driver.DriverResult;

import java.time.Duration;
import java.util.Optional;

public class TestDriverResult implements DriverResult {

    private final long ok;
    private final long ko;
    private final DriverResponseTime responseTime;
    private final Optional<String> reportUrl;

    public TestDriverResult(long ok, long ko) {
        this.ok = ok;
        this.ko = ko;
        this.responseTime = new NopResponseTime();
        this.reportUrl = Optional.empty();
    }

    public TestDriverResult(long ok, long ko, String reportUrl) {
        this.ok = ok;
        this.ko = ko;
        this.responseTime = new NopResponseTime();
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
        return Duration.ZERO;
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
