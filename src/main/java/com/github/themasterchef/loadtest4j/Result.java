package com.github.themasterchef.loadtest4j;

/**
 * The post-processed results of a load test.
 */
public final class Result {
    private final long errors;
    private final long requests;

    Result(long errors, long requests) {
        this.errors = errors;
        this.requests = requests;
    }

    public long getErrors() {
        return errors;
    }

    public long getRequests() {
        return requests;
    }

    public double getErrorRate() {
        // Do not divide by zero
        if (getRequests() == 0) {
            return 0;
        } else {
            return ((double) getErrors()) / ((double) getRequests());
        }
    }
}
