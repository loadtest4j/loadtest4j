package com.github.loadtest4j.loadtest4j.test_utils;

import com.github.loadtest4j.loadtest4j.DriverResult;

import java.util.Optional;

public class TestDriverResult implements DriverResult {

    private final long ok;
    private final long ko;
    private final Optional<String> reportUrl;

    public TestDriverResult(long ok, long ko) {
        this.ok = ok;
        this.ko = ko;
        this.reportUrl = Optional.empty();
    }

    public TestDriverResult(long ok, long ko, String reportUrl) {
        this.ok = ok;
        this.ko = ko;
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
    public Optional<String> getReportUrl() {
        return reportUrl;
    }
}
