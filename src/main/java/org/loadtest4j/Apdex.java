package org.loadtest4j;

import org.loadtest4j.driver.DriverApdex;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Duration;

public class Apdex {
    private static final MathContext ROUNDING = MathContext.UNLIMITED;

    private final DriverApdex driverApdex;

    private final long totalRequests;

    Apdex(DriverApdex driverApdex, long totalRequests) {
        this.driverApdex = driverApdex;
        this.totalRequests = totalRequests;
    }

    public double calculate(Duration satisfiedThreshold) {
        return calculate(satisfiedThreshold, satisfiedThreshold.multipliedBy(4));
    }

    public double calculate(Duration satisfiedThreshold, Duration toleratedThreshold) {
        if (!isGreaterThan(toleratedThreshold, satisfiedThreshold)) {
            throw new IllegalArgumentException("toleratedThreshold must be greater than satisfiedThreshold.");
        }

        final long satisfiedRequests = driverApdex.getSamplesBetween(Duration.ZERO, satisfiedThreshold);
        final long toleratedRequests = driverApdex.getSamplesBetween(satisfiedThreshold, toleratedThreshold);

        return apdex(satisfiedRequests, toleratedRequests, totalRequests).doubleValue();
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

    private static <T> boolean isGreaterThan(Comparable<T> a, T b) {
        return a.compareTo(b) > -1;
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