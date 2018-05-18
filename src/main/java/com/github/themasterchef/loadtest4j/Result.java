package com.github.themasterchef.loadtest4j;

/**
 * The result of a load test.
 */
public interface Result {
    long getErrors();

    long getRequests();

    default double getErrorRate() {
        // Do not divide by zero
        if (getRequests() == 0) {
            return 0;
        } else {
            return ((double) getErrors()) / ((double) getRequests());
        }
    }
}
