package org.loadtest4j.reporter;

import org.loadtest4j.driver.DriverResult;

public interface Reporter {
    void show(DriverResult driverResult);
}
