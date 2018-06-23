package com.github.loadtest4j.loadtest4j.factory;

import com.github.loadtest4j.loadtest4j.driver.Driver;
import com.github.loadtest4j.loadtest4j.driver.DriverRequest;
import com.github.loadtest4j.loadtest4j.driver.DriverResult;
import com.github.loadtest4j.loadtest4j.reporter.Reporter;

import java.util.List;

class ReportingDriver implements Driver {

    private final Driver delegate;

    private final Reporter reportStrategy;

    ReportingDriver(Driver delegate, Reporter reportStrategy) {
        this.delegate = delegate;
        this.reportStrategy = reportStrategy;
    }

    @Override
    public DriverResult run(List<DriverRequest> requests) {
        final DriverResult driverResult = delegate.run(requests);

        driverResult.getReportUrl().ifPresent(reportStrategy::show);

        return driverResult;
    }
}
