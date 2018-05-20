package com.github.themasterchef.loadtest4j;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * The load test runner.
 *
 * The runner delegates to a {@link Driver} for the technical implementation of the load test.
 */
public final class LoadTester {
    private final Driver driver;

    LoadTester(Driver driver) {
        this.driver = driver;
    }

    public Result run(Request... requests) {
        validateNotEmpty(requests);

        final Collection<DriverRequest> driverRequests = preprocessRequests(requests);

        final DriverResult driverResult = driver.run(driverRequests);

        return postprocessResult(driverResult);
    }

    private static <T> void validateNotEmpty(T[] requests) {
        if (requests.length < 1) {
            throw new LoadTesterException("No requests were specified for the load test.");
        }
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
