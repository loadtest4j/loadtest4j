package com.github.loadtest4j.loadtest4j;

import java.time.Duration;

public interface ResponseTime {
    /**
     * Get the nth percentile response time seen in the load test.
     *
     * The following common stats can also be derived from percentiles:
     *
     * Max = 100th percentile.
     *
     * Median = 50th percentile.
     *
     * @param percentile The nth percentile of interest.
     * @return The nth percentile response time.
     *
     */
    Duration getPercentile(int percentile);
}
