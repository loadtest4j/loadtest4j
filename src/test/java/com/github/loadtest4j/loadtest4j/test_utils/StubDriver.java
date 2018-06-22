package com.github.loadtest4j.loadtest4j.test_utils;

import com.github.loadtest4j.loadtest4j.driver.Driver;
import com.github.loadtest4j.loadtest4j.driver.DriverRequest;
import com.github.loadtest4j.loadtest4j.driver.DriverReport;

import java.util.ArrayList;
import java.util.List;

public class StubDriver implements Driver {

    private int actualRuns = 0;
    private final List<DriverReport> andReturns = new ArrayList<>();

    @Override
    public DriverReport run(List<DriverRequest> requests) {
        final DriverReport report = andReturns.get(actualRuns);
        actualRuns += 1;
        return report;
    }

    public void expectRun(DriverReport andReturn) {
        andReturns.add(andReturn);
    }
}
