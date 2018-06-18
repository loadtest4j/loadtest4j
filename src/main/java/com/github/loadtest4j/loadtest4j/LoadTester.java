package com.github.loadtest4j.loadtest4j;

import java.util.List;
import java.util.function.Consumer;

/**
 * The load test runner.
 */
public interface LoadTester {
    Result run(List<Request> requests, Consumer<SLA> sla);
}
