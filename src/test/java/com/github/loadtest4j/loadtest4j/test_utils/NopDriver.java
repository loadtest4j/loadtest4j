package com.github.loadtest4j.loadtest4j.test_utils;

import com.github.loadtest4j.loadtest4j.driver.Driver;
import com.github.loadtest4j.loadtest4j.driver.DriverRequest;
import com.github.loadtest4j.loadtest4j.driver.DriverReport;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public class NopDriver implements Driver {
    @Override
    public DriverReport run(List<DriverRequest> requests) {
        return new NopDriverReport();
    }

    private static class NopDriverReport implements DriverReport {

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
        public Duration getResponseTime(double percentile) {
            return Duration.ZERO;
        }

        @Override
        public Optional<String> getUrl() {
            return Optional.empty();
        }
    }
}
