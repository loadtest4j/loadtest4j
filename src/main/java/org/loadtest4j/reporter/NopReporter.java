package org.loadtest4j.reporter;

import org.loadtest4j.driver.DriverResult;

public class NopReporter implements Reporter {
    @Override
    public void show(DriverResult driverResult) {
        // No-op
    }
}
