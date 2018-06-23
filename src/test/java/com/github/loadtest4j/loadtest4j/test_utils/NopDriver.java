package com.github.loadtest4j.loadtest4j.test_utils;

import com.github.loadtest4j.loadtest4j.Driver;
import com.github.loadtest4j.loadtest4j.DriverRequest;
import com.github.loadtest4j.loadtest4j.DriverResult;
import com.github.loadtest4j.loadtest4j.ResponseTime;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

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
        public Duration getActualDuration() {
            return Duration.ZERO;
        }

        @Override
        public ResponseTime getResponseTime() {
            return new NopResponseTime();
        }

        @Override
        public Optional<String> getReportUrl() {
            return Optional.empty();
        }
    }
}
