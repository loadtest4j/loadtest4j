package com.github.themasterchef.loadtest4j;

import com.github.themasterchef.loadtest4j.drivers.Driver;
import com.github.themasterchef.loadtest4j.drivers.DriverResult;

import java.util.concurrent.CompletableFuture;

class StandardLoadTester implements LoadTester {
    private final Driver driver;

    StandardLoadTester(Driver driver) {
        this.driver = driver;
    }

    @Override
    public CompletableFuture<Result> run(Request... requests) {
        validateNotEmpty(requests);

        return driver.run(requests).thenApply(StandardResult::new);
    }

    private static <T> void validateNotEmpty(T[] requests) {
        if (requests.length < 1) {
            throw new LoadTesterException("No requests were specified for the load test.");
        }
    }

    private static class StandardResult implements Result {
        private final DriverResult driverResult;

        private StandardResult(DriverResult driverResult) {
            this.driverResult = driverResult;
        }

        @Override
        public long getErrors() {
            return driverResult.getErrors();
        }

        @Override
        public long getRequests() {
            return driverResult.getRequests();
        }
    }
}
