package org.loadtest4j;

/**
 * The post-processed results of a load test.
 */
public final class Result {
    private final long ok;
    private final long ko;
    private final ResponseTime responseTime;

    public Result(long ok, long ko, ResponseTime responseTime) {
        this.ok = ok;
        this.ko = ko;
        this.responseTime = responseTime;
    }

    public ResponseTime getResponseTime() {
        return responseTime;
    }

    protected long getTotal() {
        return ok + ko;
    }

    /**
     * @return The percent of requests that were OK (represented as a number between 0 and 100)
     */
    public double getPercentOk() {
        // Do not divide by zero
        if (getTotal() == 0) {
            return 0;
        } else {
            return ((double) ok) / ((double) getTotal()) * 100;
        }
    }
}
