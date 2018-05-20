package com.github.themasterchef.loadtest4j;

import java.util.Collection;

/**
 * The driver that runs a load test under the covers.
 */
public interface Driver {
    DriverResult run(Collection<DriverRequest> requests);
}
