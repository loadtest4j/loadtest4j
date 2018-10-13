package org.loadtest4j.driver;

import java.time.Duration;

/**
 * The low-level results from the load test driver.
 */
public interface DriverResult {
    DriverApdex getApdex();

    Duration getActualDuration();

    long getKo();

    long getOk();

    DriverResponseTime getResponseTime();
}
