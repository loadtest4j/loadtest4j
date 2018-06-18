package com.github.loadtest4j.loadtest4j.driver;

import java.time.Duration;
import java.util.Optional;

/**
 * The low-level report from the load test driver.
 */
public interface DriverReport {
    long getOk();

    long getKo();

    Duration getActualDuration();

    Duration getResponseTime(double percentile);

    Optional<String> getUrl();
}
