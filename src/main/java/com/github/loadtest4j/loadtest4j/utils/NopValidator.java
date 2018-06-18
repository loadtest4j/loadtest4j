package com.github.loadtest4j.loadtest4j.utils;

public class NopValidator<T> implements Validator<T> {
    @Override
    public boolean validate(Comparable<T> actual) {
        return true;
    }
}
