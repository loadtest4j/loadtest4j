package com.github.loadtest4j.loadtest4j;

import java.util.Collection;

/**
 * The driver that runs a load test under the covers.
 */
public interface Driver {
    DriverResult run(Collection<DriverRequest> requests);
}
