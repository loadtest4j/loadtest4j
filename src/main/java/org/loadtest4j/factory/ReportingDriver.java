package org.loadtest4j.factory;

import org.loadtest4j.driver.Driver;
import org.loadtest4j.driver.DriverRequest;
import org.loadtest4j.driver.DriverResult;
import org.loadtest4j.reporter.Reporter;

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
