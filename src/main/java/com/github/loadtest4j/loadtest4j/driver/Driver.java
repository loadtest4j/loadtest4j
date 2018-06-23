package com.github.loadtest4j.loadtest4j.driver;

import java.util.List;

/**
 * The driver that runs a load test under the covers.
 */
public interface Driver {
    DriverResult run(List<DriverRequest> requests);
}
