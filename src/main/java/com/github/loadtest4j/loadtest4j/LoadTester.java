package com.github.loadtest4j.loadtest4j;

/**
 * The load test runner.
 */
public interface LoadTester {
    Result run(Request... requests);
}
