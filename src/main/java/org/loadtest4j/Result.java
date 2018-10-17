package org.loadtest4j;

import java.time.Duration;

/**
 * The post-processed results of a load test. Top-level objects in this class represent common Service Level Indicators,
 * with the exception of {@link Diagnostics} which exists only to aid visual test inspection.
 */
public interface Result {
    /**
     * The Apdex score for the Service Under Test.
     * @param satisfiedThreshold the response time threshold for user satisfaction (the user toleration threshold is
     *                           4*satisfiedThreshold)
     * @return The score, a double between 0.0 (no users satisfied) and 1.0 (all users satisfied)
     */
    double getApdexScore(Duration satisfiedThreshold);

    Diagnostics getDiagnostics();

    /**
     * The request survival rate.
     *
     * @return The percent of requests that were OK (represented as a number between 0 and 100)
     */
    double getPercentOk();

    ResponseTime getResponseTime();
}
