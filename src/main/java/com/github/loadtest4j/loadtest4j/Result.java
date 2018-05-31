package com.github.loadtest4j.loadtest4j;

/**
 * The post-processed results of a load test.
 */
public final class Result {
    private final long ok;
    private final long ko;

    Result(long ok, long ko) {
        this.ok = ok;
        this.ko = ko;
    }

    public long getKo() {
        return ko;
    }

    public long getOk() {
        return ok;
    }

    public long getTotal() {
        return getOk() + getKo();
    }

    /**
     * @return The percent of requests that were KO, represented as a number between 0.0 and 1.0
     */
    public double getPercentKo() {
        // Do not divide by zero
        if (getTotal() == 0) {
            return 0;
        } else {
            return ((double) getKo()) / ((double) getTotal());
        }
    }
}
