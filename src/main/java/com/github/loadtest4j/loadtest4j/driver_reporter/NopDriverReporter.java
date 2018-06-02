package com.github.loadtest4j.loadtest4j.driver_reporter;

public class NopDriverReporter implements DriverReporter {
    @Override
    public void show(String reportUrl) {
        // No-op
    }
}
