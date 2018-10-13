package org.loadtest4j.driver;

import java.time.Duration;

public interface DriverApdex {
    long getOkRequestsBetween(Duration min, Duration max);
}