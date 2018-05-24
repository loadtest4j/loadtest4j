package com.github.loadtest4j.loadtest4j.driver;

import com.github.loadtest4j.loadtest4j.Driver;
import com.github.loadtest4j.loadtest4j.DriverRequest;
import com.github.loadtest4j.loadtest4j.DriverResult;

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
