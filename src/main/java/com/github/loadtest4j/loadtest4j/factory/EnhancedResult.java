package com.github.loadtest4j.loadtest4j.factory;

import com.github.loadtest4j.loadtest4j.driver.DriverReport;

import java.time.Duration;

final class EnhancedResult {
    private final long ok;
    private final long ko;
    private final Duration actualDuration;

    EnhancedResult(long ok, long ko, Duration actualDuration) {
        this.ok = ok;
        this.ko = ko;
        this.actualDuration = actualDuration;
    }

    protected Duration getActualDuration() {
        return actualDuration;
    }

    private long getKo() {
        return ko;
    }

    private long getOk() {
        return ok;
    }

    protected long getTotal() {
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

    protected static EnhancedResult postprocessResult(DriverReport driverReport) {
        return new EnhancedResult(driverReport.getOk(), driverReport.getKo(), driverReport.getActualDuration());
    }

}