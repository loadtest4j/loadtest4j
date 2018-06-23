package com.github.loadtest4j.loadtest4j;

import java.time.Duration;

public interface ResponseTime {
    /**
     * The nth percentile response time. In other words, n% of HTTP requests finished in less time than this.
     *
     * Max = 100th percentile.
     *
     * Median = 50th percentile.
     *
     * Min = 0th percentile.
     */
    Duration getPercentile(int percentile);
}
