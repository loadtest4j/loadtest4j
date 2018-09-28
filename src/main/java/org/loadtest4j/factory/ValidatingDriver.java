package org.loadtest4j.factory;

import org.loadtest4j.LoadTesterException;
import org.loadtest4j.driver.Driver;
import org.loadtest4j.driver.DriverRequest;
import org.loadtest4j.driver.DriverResult;

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
    }
}
