package org.loadtest4j;

import java.time.Duration;

/**
 * Auxiliary information from the load test is included here to assist debugging. NOTE: diagnostics are NOT Service
 * Level Indicators, so you SHOULD NOT write test assertions against them.
 */
public final class Diagnostics {

    private final Duration duration;

    private final RequestCount requestCount;

    private final double requestsPerSecond;

    public Diagnostics(Duration duration, RequestCount requestCount, double requestsPerSecond) {
        this.duration = duration;
        this.requestCount = requestCount;
        this.requestsPerSecond = requestsPerSecond;
    }

    public Duration getDuration() {
        return duration;
    }

    public RequestCount getRequestCount() {
        return requestCount;
    }

    public double getRequestsPerSecond() {
        return requestsPerSecond;
    }
}
