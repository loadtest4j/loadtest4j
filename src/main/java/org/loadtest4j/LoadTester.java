package org.loadtest4j;

import java.util.List;

/**
 * The load test runner.
 */
public interface LoadTester {
    Result run(List<Request> requests);
}
