package com.github.loadtest4j.loadtest4j.utils;

public class GreaterThanOrEqualToValidator<T> implements Validator<T> {
    private final T threshold;

    public GreaterThanOrEqualToValidator(T threshold) {
        this.threshold = threshold;
    }

    public boolean validate(Comparable<T> actual) {
        return actual.compareTo(threshold) >= 0;
    }
}
