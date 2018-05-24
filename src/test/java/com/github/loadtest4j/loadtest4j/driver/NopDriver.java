package com.github.loadtest4j.loadtest4j.driver;

import com.github.loadtest4j.loadtest4j.Driver;
import com.github.loadtest4j.loadtest4j.DriverRequest;
import com.github.loadtest4j.loadtest4j.DriverResult;

import java.util.List;

public class NopDriver implements Driver {
    @Override
    public DriverResult run(List<DriverRequest> requests) {
        return new DriverResult(0, 0);
    }
}
