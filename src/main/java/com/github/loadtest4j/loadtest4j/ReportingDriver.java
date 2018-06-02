package com.github.loadtest4j.loadtest4j;

import com.github.loadtest4j.loadtest4j.driver_reporter.DriverReporter;

import java.util.List;

class ReportingDriver implements Driver {

    private final Driver delegate;

    private final DriverReporter reportStrategy;

    ReportingDriver(Driver delegate, DriverReporter reportStrategy) {
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
