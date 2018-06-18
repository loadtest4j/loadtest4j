package com.github.loadtest4j.loadtest4j.factory;

import com.github.loadtest4j.loadtest4j.Result;
import com.github.loadtest4j.loadtest4j.SLA;
import com.github.loadtest4j.loadtest4j.driver.DriverReport;
import com.github.loadtest4j.loadtest4j.utils.GreaterThanOrEqualToValidator;
import com.github.loadtest4j.loadtest4j.utils.LessThanOrEqualToValidator;
import com.github.loadtest4j.loadtest4j.utils.NopValidator;
import com.github.loadtest4j.loadtest4j.utils.Validator;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class ValidatingSLA implements SLA {

    private final Map<Double, Duration> percentiles = new ConcurrentHashMap<>();
    private Validator<Double> percentKo = new NopValidator<>();
    private Validator<Double> rps = new NopValidator<>();

    @Override
    public void percentKo(double threshold) {
        percentKo = new LessThanOrEqualToValidator<>(threshold);
    }

    @Override
    public void requestsPerSecond(double threshold) {
        rps = new GreaterThanOrEqualToValidator<>(threshold);
    }

    @Override
    public void responseTime(double percentile, Duration threshold) {
        percentiles.put(percentile, threshold);
    }

    Result validate(DriverReport report) {
        final EnhancedResult res = EnhancedResult.postprocessResult(report);
        final boolean success = validatePercentKo(res.getPercentKo()) && validateRps(res.getRequestsPerSecond()) && validateResponseTimes(report);
        if (success) {
            return Result.Success;
        } else {
            return Result.Failure;
        }
    }

    private boolean validatePercentKo(double actual) {
        return percentKo.validate(actual);
    }

    private boolean validateRps(double actual) {
        return rps.validate(actual);
    }

    private boolean validateResponseTimes(DriverReport report) {
        // FIXME vulnerable to floating-point errors
        return percentiles.entrySet().stream()
                .map(entry -> {
                    final Double percentile = entry.getKey();
                    final Duration threshold = entry.getValue();

                    final Duration actual = report.getResponseTime(percentile);
                    return new LessThanOrEqualToValidator<>(threshold).validate(actual);
                })
                .reduce(true, (b1, b2) -> b1 && b2);
    }
}
