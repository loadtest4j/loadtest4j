package org.loadtest4j;

/**
 * The post-processed results of a load test. Top-level objects in this class represent common Service Level Indicators,
 * with the exception of {@link Diagnostics} which exists only to aid visual test inspection.
 */
public final class Result {
    private final Diagnostics diagnostics;
    private final double percentOk;
    private final ResponseTime responseTime;

    public Result(Diagnostics diagnostics, double percentOk, ResponseTime responseTime) {
        this.diagnostics = diagnostics;
        this.percentOk = percentOk;
        this.responseTime = responseTime;
    }

    public Diagnostics getDiagnostics() {
        return diagnostics;
    }

    /**
     * @return The percent of requests that were OK (represented as a number between 0 and 100)
     */
    public double getPercentOk() {
        return percentOk;
    }

    public ResponseTime getResponseTime() {
        return responseTime;
    }
}
