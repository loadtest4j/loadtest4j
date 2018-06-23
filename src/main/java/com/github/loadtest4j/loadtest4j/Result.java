package com.github.loadtest4j.loadtest4j;

import java.time.Duration;

/**
 * The post-processed results of a load test.
 */
public final class Result {
    private final long ok;
    private final long ko;
    private final Duration actualDuration;
    private final ResponseTime responseTime;

    public Result(long ok, long ko, Duration actualDuration, ResponseTime responseTime) {
        this.ok = ok;
        this.ko = ko;
        this.actualDuration = actualDuration;
        this.responseTime = responseTime;
    }

    public Duration getActualDuration() {
        return actualDuration;
    }

    public long getKo() {
        return ko;
    }

    public long getOk() {
        return ok;
    }

    public ResponseTime getResponseTime() {
        return responseTime;
    }

    public long getTotal() {
        return getOk() + getKo();
    }

    /**
     * @return The percent of requests that were KO (represented as a number between 0 and 100)
     */
    public double getPercentKo() {
        // Do not divide by zero
        if (getTotal() == 0) {
            return 0;
        } else {
            return ((double) getKo()) / ((double) getTotal()) * 100;
        }
    }

    /**
     * @return The average requests per second (total / duration)
     */
    public double getRequestsPerSecond() {
        final double durationSeconds = ((double) getActualDuration().toMillis()) / 1000;

        // Do not divide by zero
        if (durationSeconds == 0) {
            return 0;
        } else {
            return getTotal() / durationSeconds;
        }
    }
}
