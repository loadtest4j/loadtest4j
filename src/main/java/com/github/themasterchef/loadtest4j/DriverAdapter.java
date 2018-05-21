package com.github.themasterchef.loadtest4j;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

class DriverAdapter implements LoadTester {
    private final Driver driver;

    DriverAdapter(Driver driver) {
        this.driver = driver;
    }

    @Override
    public Result run(Request... requests) {
        final Collection<DriverRequest> driverRequests = preprocessRequests(requests);

        final DriverResult driverResult = driver.run(driverRequests);

        return postprocessResult(driverResult);
    }

    private static Collection<DriverRequest> preprocessRequests(Request[] requests) {
        return Arrays.stream(requests)
                .map(request -> new DriverRequest(request.getBody(), request.getHeaders(), request.getMethod(), request.getPath()))
                .collect(Collectors.toList());
    }

    private static Result postprocessResult(DriverResult driverResult) {
        return new Result(driverResult.getErrors(), driverResult.getRequests());
    }
}
