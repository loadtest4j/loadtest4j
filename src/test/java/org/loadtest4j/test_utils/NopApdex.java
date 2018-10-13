package org.loadtest4j.test_utils;

import org.loadtest4j.driver.DriverApdex;

import java.time.Duration;

public class NopApdex implements DriverApdex {

    @Override
    public long getOkRequestsBetween(Duration min, Duration max) {
        return 0;
    }
}
