package com.github.loadtest4j.loadtest4j.utils;

public interface Validator<T> {
    boolean validate(Comparable<T> actual);
}
