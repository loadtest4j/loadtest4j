package org.loadtest4j.factory;

import org.loadtest4j.driver.Driver;
import org.loadtest4j.driver.DriverRequest;
import org.loadtest4j.driver.DriverResult;
import org.loadtest4j.reporter.Reporter;

import java.util.List;

class ReportingDriver implements Driver {

    private final Driver delegate;

    private final Reporter reporter;

    ReportingDriver(Driver delegate, Reporter reporter) {
        this.delegate = delegate;
        this.reporter = reporter;
    }

    @Override
    public DriverResult run(List<DriverRequest> requests) {
        final DriverResult driverResult = delegate.run(requests);

        reporter.show(driverResult);

        return driverResult;
    }
}
