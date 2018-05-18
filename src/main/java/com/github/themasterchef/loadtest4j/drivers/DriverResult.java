package com.github.themasterchef.loadtest4j.drivers;

/**
 * The low-level results from the load test driver.
 */
public interface DriverResult {
    long getErrors();

    long getRequests();
}
