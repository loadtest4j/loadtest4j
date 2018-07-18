package org.loadtest4j.test_utils;

import org.loadtest4j.driver.Driver;
import org.loadtest4j.driver.DriverRequest;
import org.loadtest4j.driver.DriverResult;

import java.util.ArrayList;
import java.util.List;

public class SpyDriver implements Driver {
    private List<DriverRequest> actualRequests = new ArrayList<>();

    private final Driver delegate;

    public SpyDriver(Driver delegate) {
        this.delegate = delegate;
    }

    @Override
    public DriverResult run(List<DriverRequest> requests) {
        this.actualRequests = requests;
        return delegate.run(requests);
    }

    public List<DriverRequest> getActualRequests() {
        return actualRequests;
    }
}
