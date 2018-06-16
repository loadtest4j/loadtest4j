package com.github.loadtest4j.loadtest4j;

import com.github.loadtest4j.loadtest4j.driver_reporter.DriverReporter;
import com.github.loadtest4j.loadtest4j.driver_reporter.LoggingDriverReporter;
import com.github.loadtest4j.loadtest4j.driver_reporter.NopDriverReporter;

import java.util.Map;

class DriverReporterFactory {
    protected DriverReporter create(Map<String, String> properties) {
        final boolean isEnabled = Boolean.valueOf(properties.getOrDefault("enabled", "false"));
        if (isEnabled) {
            return LoggingDriverReporter.stdout();
        } else {
            return new NopDriverReporter();
        }
    }
}
