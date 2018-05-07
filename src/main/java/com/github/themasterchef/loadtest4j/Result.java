package com.github.themasterchef.loadtest4j;

/**
 * The result of a load test.
 */
public interface Result {
    long getErrors();

    long getRequests();
}
