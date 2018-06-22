package com.github.loadtest4j.loadtest4j.utils;

public class LessThanOrEqualToValidator<T> implements Validator<T> {

    private final T threshold;

    public LessThanOrEqualToValidator(T threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean validate(Comparable<T> actual) {
        return actual.compareTo(threshold) <= 0;
    }
}
