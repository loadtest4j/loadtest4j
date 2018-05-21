package com.github.themasterchef.loadtest4j;

/**
 * The load test runner.
 */
public interface LoadTester {
    Result run(Request... requests);
}
