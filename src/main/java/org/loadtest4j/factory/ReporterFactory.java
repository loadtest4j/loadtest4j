package org.loadtest4j.factory;

import org.loadtest4j.reporter.Reporter;
import org.loadtest4j.reporter.LoggingReporter;
import org.loadtest4j.reporter.NopReporter;

import java.util.Map;

class ReporterFactory {
    protected Reporter create(Map<String, String> properties) {
        final boolean isEnabled = Boolean.valueOf(properties.getOrDefault("enabled", "false"));
        if (isEnabled) {
            return stdout();
        } else {
            return new NopReporter();
        }
    }

    private static LoggingReporter stdout() {
        return new LoggingReporter(System.out::println);
    }
}
