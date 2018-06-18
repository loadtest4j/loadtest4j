package com.github.loadtest4j.loadtest4j.test_utils;

import com.github.loadtest4j.loadtest4j.driver.DriverReport;

import java.time.Duration;
import java.util.Optional;

public class TestDriverReport implements DriverReport {

    private final long ok;
    private final long ko;
    private final Optional<String> url;

    public TestDriverReport(long ok, long ko) {
        this.ok = ok;
        this.ko = ko;
        this.url = Optional.empty();
    }

    public TestDriverReport(long ok, long ko, String url) {
        this.ok = ok;
        this.ko = ko;
        this.url = Optional.of(url);
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
    public Duration getResponseTime(double percentile) {
        return Duration.ZERO;
    }

    @Override
    public Optional<String> getUrl() {
        return url;
    }
}
