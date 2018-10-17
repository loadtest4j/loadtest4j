package org.loadtest4j.factory;

import org.loadtest4j.ResponseTime;

import java.time.Duration;
import java.util.function.Function;

class ResponseTimeAdapter implements ResponseTime {

    private final Function<Double, Duration> delegate;

    ResponseTimeAdapter(Function<Double, Duration> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Duration getPercentile(double percentile) {
        return delegate.apply(percentile);
    }

    @Override
    public Duration getMax() {
        return getPercentile(100);
    }
}
