package org.loadtest4j.driver;

import java.time.Duration;

public interface DriverApdex {
    long getSamplesBetween(Duration min, Duration max);
}