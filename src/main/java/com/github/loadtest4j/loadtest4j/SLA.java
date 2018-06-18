package com.github.loadtest4j.loadtest4j;

import java.time.Duration;

public interface SLA {
    void percentKo(double threshold);

    void requestsPerSecond(double threshold);

    void responseTime(double percentile, Duration threshold);
}
