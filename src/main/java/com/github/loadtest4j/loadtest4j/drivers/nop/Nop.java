package com.github.loadtest4j.loadtest4j.drivers.nop;

import com.github.loadtest4j.loadtest4j.DriverRequest;
import com.github.loadtest4j.loadtest4j.Driver;
import com.github.loadtest4j.loadtest4j.DriverResult;

import java.util.Collection;

class Nop implements Driver {
    @Override
    public DriverResult run(Collection<DriverRequest> requests) {
        return new DriverResult(0, 0);
    }
}
