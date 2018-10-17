package org.loadtest4j.driver;

import java.time.Duration;

/**
 * The low-level results from the load test driver.
 */
public interface DriverResult {
    Duration getActualDuration();

    long getKo();

    long getOk();

    long getOkRequestsBetween(Duration min, Duration max);

    Duration getResponseTimePercentile(double percentile);
}
