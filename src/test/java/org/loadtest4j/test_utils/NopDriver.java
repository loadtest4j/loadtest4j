package org.loadtest4j.test_utils;

import org.loadtest4j.driver.*;

import java.time.Duration;
import java.util.List;

public class NopDriver implements Driver {
    @Override
    public DriverResult run(List<DriverRequest> requests) {
        return new NopDriverResult();
    }

    private static class NopDriverResult implements DriverResult {

        @Override
        public long getOk() {
            return 0;
        }

        @Override
        public long getKo() {
            return 0;
        }

        @Override
        public DriverApdex getApdex() {
            return new NopApdex();
        }

        @Override
        public Duration getActualDuration() {
            return Duration.ZERO;
        }

        @Override
        public DriverResponseTime getResponseTime() {
            return new NopResponseTime();
        }
    }
}
