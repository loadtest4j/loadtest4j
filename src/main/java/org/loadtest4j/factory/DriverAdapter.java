package org.loadtest4j.factory;

import org.loadtest4j.*;
import org.loadtest4j.driver.*;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

class DriverAdapter implements LoadTester {
    private final Driver driver;

    DriverAdapter(Driver driver) {
        this.driver = driver;
    }

    @Override
    public Result run(List<Request> requests) {
        final List<DriverRequest> driverRequests = preprocessRequests(requests);

        final DriverResult driverResult = driver.run(driverRequests);

        return postprocessResult(driverResult);
    }

    private static List<DriverRequest> preprocessRequests(List<Request> requests) {
        return requests.stream()
                .map(request -> new DriverRequest(request.getBody(), request.getHeaders(), request.getMethod(), request.getPath(), request.getQueryParams()))
                .collect(Collectors.toList());
    }

    static Result postprocessResult(DriverResult driverResult) {
        final Apdex apdex = getApdex(driverResult);
        final Diagnostics diagnostics = getDiagnostics(driverResult);
        final double percentOk = getPercentOk(driverResult);
        final ResponseTime responseTime = getResponseTime(driverResult);
        return new Result(apdex, diagnostics, percentOk, responseTime);
    }

    private static Apdex getApdex(DriverResult driverResult) {
        final DriverApdex driverApdex = driverResult.getApdex();
        final long totalRequests = driverResult.getOk() + driverResult.getKo();
        return new ApdexAdapter(driverApdex, totalRequests);
    }

    private static Diagnostics getDiagnostics(DriverResult driverResult) {
        final Duration duration = driverResult.getActualDuration();
        final RequestCount requestCount = getRequestCount(driverResult);
        final double requestsPerSecond = getRequestsPerSecond(driverResult);
        return new Diagnostics(duration, requestCount, requestsPerSecond);
    }

    private static RequestCount getRequestCount(DriverResult driverResult) {
        final long ok = driverResult.getOk();
        final long ko = driverResult.getKo();
        final long total =  ok + ko;
        return new RequestCount(ok, ko, total);
    }

    private static double getRequestsPerSecond(DriverResult driverResult) {
        final Duration duration = driverResult.getActualDuration();
        final long requests = driverResult.getOk() + driverResult.getKo();
        return getRequestsPerSecond(requests, duration);
    }

    private static double getPercentOk(DriverResult driverResult) {
        final long ok = driverResult.getOk();
        final long total = driverResult.getOk() + driverResult.getKo();
        return getPercentOk(ok, total);
    }

    private static ResponseTime getResponseTime(DriverResult driverResult) {
        return new ResponseTimeAdapter(driverResult.getResponseTime());
    }

    static double getRequestsPerSecond(long requests, Duration duration) {
        final double durationSeconds = ((double) duration.toMillis()) / 1000;

        // Do not divide by zero
        if (durationSeconds == 0) {
            return 0;
        } else {
            return requests / durationSeconds;
        }
    }

    static double getPercentOk(long ok, long total) {
        // Do not divide by zero
        if (total == 0) {
            return 0;
        } else {
            return ((double) ok) / ((double) total) * 100;
        }
    }

    private static class ApdexAdapter extends Apdex {

        private final DriverApdex delegate;
        private final long totalRequests;

        private ApdexAdapter(DriverApdex delegate, long totalRequests) {
            this.delegate = delegate;
            this.totalRequests = totalRequests;
        }

        @Override
        protected long getTotalRequests() {
            return totalRequests;
        }

        @Override
        protected long getOkRequestsBetween(Duration min, Duration max) {
            return delegate.getOkRequestsBetween(min, max);
        }
    }

    private static class ResponseTimeAdapter extends ResponseTime {

        private final DriverResponseTime delegate;

        private ResponseTimeAdapter(DriverResponseTime delegate) {
            this.delegate = delegate;
        }

        @Override
        public Duration getPercentile(double percentile) {
            return delegate.getPercentile(percentile);
        }
    }
}
