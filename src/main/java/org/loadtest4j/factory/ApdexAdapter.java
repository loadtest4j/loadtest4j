package org.loadtest4j.factory;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Duration;
import java.util.function.BiFunction;

class ApdexAdapter {

    private static final MathContext ROUNDING = MathContext.UNLIMITED;

    private final BiFunction<Duration, Duration, Long> okRequestsDistribution;
    private final long totalRequests;

    ApdexAdapter(BiFunction<Duration, Duration, Long> okRequestsDistribution, long totalRequests) {
        this.okRequestsDistribution = okRequestsDistribution;
        this.totalRequests = totalRequests;
    }

    double getScore(Duration satisfiedThreshold) {
        final Duration toleratedThreshold = satisfiedThreshold.multipliedBy(4);

        final long satisfiedRequests = getOkRequestsBetween(Duration.ZERO, satisfiedThreshold);
        final long toleratedRequests = getOkRequestsBetween(satisfiedThreshold, toleratedThreshold);

        return apdex(satisfiedRequests, toleratedRequests, totalRequests).doubleValue();
    }

    private long getOkRequestsBetween(Duration min, Duration max) {
        return okRequestsDistribution.apply(min, max);
    }

    static BigDecimal apdex(long satisfiedRequests, long toleratedRequests, long totalRequests) {
        if (satisfiedRequests < 0) {
            throw new IllegalArgumentException("satisfiedRequests must not be negative.");
        }
        if (toleratedRequests < 0) {
            throw new IllegalArgumentException("toleratedRequests must not be negative.");
        }
        if (totalRequests < 0) {
            throw new IllegalArgumentException("totalRequests must not be negative.");
        }
        if (totalRequests < (satisfiedRequests + toleratedRequests)) {
            throw new IllegalArgumentException("totalRequests must be greater than (satisfiedRequests + toleratedRequests).");
        }

        final BigDecimal top = add(satisfiedRequests, half(toleratedRequests));
        return divide(top, totalRequests);
    }

    private static BigDecimal add(long a, BigDecimal b) {
        return decimal(a).add(b);
    }

    private static BigDecimal half(long a) {
        return decimal(a).divide(decimal(2), ROUNDING);
    }

    private static BigDecimal divide(BigDecimal a, long b) {
        return a.divide(decimal(b), ROUNDING);
    }

    private static BigDecimal decimal(long i) {
        return BigDecimal.valueOf(i);
    }
}
