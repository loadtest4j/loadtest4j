package com.github.loadtest4j.loadtest4j;

import java.time.Duration;
import java.util.Optional;

/**
 * The low-level results from the load test driver.
 */
public interface DriverResult {
    long getOk();

    long getKo();

    Duration getActualDuration();

    Optional<String> getReportUrl();
}
