package com.github.loadtest4j.loadtest4j;

import java.time.Duration;

import com.github.loadtest4j.loadtest4j.driver.DriverResponseTime;

public final class ResponseTime {
    
    private final DriverResponseTime delegate;
    
    public ResponseTime(DriverResponseTime delegate) {
        this.delegate = delegate;
    }
    
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
    public Duration getPercentile(int percentile) {
        return delegate.getPercentile(percentile);
    }
    
    /**
     * @return The 100th percentile response time.
     */
    public Duration getMax() {
        return delegate.getPercentile(100);
    }
    
    /**
     * @return The 50th percentile response time.
     */
    public Duration getMedian() {
        return delegate.getPercentile(50);
    }
}
