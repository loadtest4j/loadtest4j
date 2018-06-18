package com.github.loadtest4j.loadtest4j.factory;

import com.github.loadtest4j.loadtest4j.LoadTesterException;
import com.github.loadtest4j.loadtest4j.driver.Driver;
import com.github.loadtest4j.loadtest4j.driver.DriverRequest;
import com.github.loadtest4j.loadtest4j.driver.DriverReport;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

class ValidatingDriver implements Driver {

    private final Driver delegate;

    ValidatingDriver(Driver delegate) {
        this.delegate = delegate;
    }

    @Override
    public DriverReport run(List<DriverRequest> requests) {
        final DriverReport driverReport = delegate.run(requests);

        validateResult(driverReport);

        return driverReport;
    }

    private static void validateResult(DriverReport driverReport) {
        if (driverReport.getOk() < 0) {
            throw new LoadTesterException("The load test driver returned a negative number of OK requests.");
        }

        if (driverReport.getKo() < 0) {
            throw new LoadTesterException("The load test driver returned a negative number of KO requests.");
        }

        driverReport.getUrl().ifPresent(url -> {
            try {
                new URL(url);
            } catch (MalformedURLException e) {
                throw new LoadTesterException("The load test driver returned an invalid report URL.");
            }
        });
    }
}
