package com.github.loadtest4j.loadtest4j;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

class ValidatingDriver implements Driver {

    private final Driver delegate;

    ValidatingDriver(Driver delegate) {
        this.delegate = delegate;
    }

    @Override
    public DriverResult run(List<DriverRequest> requests) {
        final DriverResult driverResult = delegate.run(requests);

        validateResult(driverResult);

        return driverResult;
    }

    private static void validateResult(DriverResult driverResult) {
        if (driverResult.getOk() < 0) {
            throw new LoadTesterException("The load test driver returned a negative number of OK requests.");
        }

        if (driverResult.getKo() < 0) {
            throw new LoadTesterException("The load test driver returned a negative number of KO requests.");
        }

        driverResult.getReportUrl().ifPresent(url -> {
            try {
                new URL(url);
            } catch (MalformedURLException e) {
                throw new LoadTesterException("The load test driver returned an invalid report URL.");
            }
        });
    }
}
