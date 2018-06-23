package com.github.loadtest4j.loadtest4j.factory;

import com.github.loadtest4j.loadtest4j.reporter.Reporter;
import com.github.loadtest4j.loadtest4j.reporter.LoggingReporter;
import com.github.loadtest4j.loadtest4j.reporter.NopReporter;

import java.util.Map;

class ReporterFactory {
    protected Reporter create(Map<String, String> properties) {
        final boolean isEnabled = Boolean.valueOf(properties.getOrDefault("enabled", "false"));
        if (isEnabled) {
            return LoggingReporter.stdout();
        } else {
            return new NopReporter();
        }
    }
}
