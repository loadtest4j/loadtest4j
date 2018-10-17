package org.loadtest4j;

import java.time.Duration;

public interface ResponseTime {
    
    /**
     * Get the nth percentile response time seen in the load test.
     *
     * @param percentile The nth percentile of interest.
     * @return The nth percentile response time.
     *
     */
    Duration getPercentile(double percentile);
    
    /**
     * @return The maximum response time.
     */
    Duration getMax();
}
