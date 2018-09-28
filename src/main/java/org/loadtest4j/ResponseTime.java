package org.loadtest4j;

import java.time.Duration;

import org.loadtest4j.driver.DriverResponseTime;

public final class ResponseTime {
    
    private final DriverResponseTime delegate;
    
    public ResponseTime(DriverResponseTime delegate) {
        this.delegate = delegate;
    }
    
    /**
     * Get the nth percentile response time seen in the load test.
     *
     * @param percentile The nth percentile of interest.
     * @return The nth percentile response time.
     *
     */
    public Duration getPercentile(double percentile) {
        return delegate.getPercentile(percentile);
    }
    
    /**
     * @return The maximum response time.
     */
    public Duration getMax() {
        return delegate.getPercentile(100);
    }

    /**
     * @return The median response time.
     */
    public Duration getMedian() {
        return delegate.getPercentile(50);
    }
}
