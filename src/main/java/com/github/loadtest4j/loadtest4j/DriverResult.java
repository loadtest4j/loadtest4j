package com.github.loadtest4j.loadtest4j;

/**
 * The low-level results from the load test driver.
 */
public final class DriverResult {
    private final long ok;
    private final long ko;

    public DriverResult(long ok, long ko) {
        this.ok = ok;
        this.ko = ko;
    }

    public long getOk() {
        return ok;
    }

    public long getKo() {
        return ko;
    }
}
