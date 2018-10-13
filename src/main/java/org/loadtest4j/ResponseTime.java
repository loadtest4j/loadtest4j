package org.loadtest4j;

import java.time.Duration;

public abstract class ResponseTime {
    
    /**
     * Get the nth percentile response time seen in the load test.
     *
     * @param percentile The nth percentile of interest.
     * @return The nth percentile response time.
     *
     */
    public abstract Duration getPercentile(double percentile);
    
    /**
     * @return The maximum response time.
     */
    public Duration getMax() {
        return getPercentile(100);
    }

    /**
     * @return The median response time.
     */
    public Duration getMedian() {
        return getPercentile(50);
    }
}
