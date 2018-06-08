package com.github.loadtest4j.loadtest4j.utils;

import java.util.Optional;

public interface ClassFinder<T> {
    Optional<T> find(String className);
}
