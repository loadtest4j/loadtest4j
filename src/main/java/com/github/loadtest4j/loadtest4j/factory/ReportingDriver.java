package com.github.loadtest4j.loadtest4j.factory;

import com.github.loadtest4j.loadtest4j.driver.Driver;
import com.github.loadtest4j.loadtest4j.driver.DriverRequest;
import com.github.loadtest4j.loadtest4j.driver.DriverReport;

import java.util.List;

class ReportingDriver implements Driver {

    private final Driver delegate;

    private final Reporter reportStrategy;

    ReportingDriver(Driver delegate, Reporter reportStrategy) {
        this.delegate = delegate;
        this.reportStrategy = reportStrategy;
    }

    @Override
    public DriverReport run(List<DriverRequest> requests) {
        final DriverReport driverReport = delegate.run(requests);

        driverReport.getUrl().ifPresent(reportStrategy::show);

        return driverReport;
    }
}
