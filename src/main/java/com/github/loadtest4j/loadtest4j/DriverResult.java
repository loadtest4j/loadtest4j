package com.github.loadtest4j.loadtest4j;

/**
 * The low-level results from the load test driver.
 */
public final class DriverResult {
    private final long errors;
    private final long requests;

    public DriverResult(long errors, long requests) {
        this.errors = errors;
        this.requests = requests;
    }

    public long getErrors() {
        return errors;
    }

    public long getRequests() {
        return requests;
    }
}
