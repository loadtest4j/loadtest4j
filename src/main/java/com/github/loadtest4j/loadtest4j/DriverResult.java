package com.github.loadtest4j.loadtest4j;

import java.util.Optional;

/**
 * The low-level results from the load test driver.
 */
public interface DriverResult {
    long getOk();

    long getKo();

    Optional<String> getReportUrl();
}
