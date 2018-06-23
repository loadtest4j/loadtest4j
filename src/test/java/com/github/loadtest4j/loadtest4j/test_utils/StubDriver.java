package com.github.loadtest4j.loadtest4j.test_utils;

import com.github.loadtest4j.loadtest4j.driver.Driver;
import com.github.loadtest4j.loadtest4j.driver.DriverRequest;
import com.github.loadtest4j.loadtest4j.driver.DriverResult;

import java.util.ArrayList;
import java.util.List;

public class StubDriver implements Driver {

    private int actualRuns = 0;
    private final List<DriverResult> andReturns = new ArrayList<>();

    @Override
    public DriverResult run(List<DriverRequest> requests) {
        final DriverResult result = andReturns.get(actualRuns);
        actualRuns += 1;
        return result;
    }

    public void expectRun(DriverResult andReturn) {
        andReturns.add(andReturn);
    }
}
