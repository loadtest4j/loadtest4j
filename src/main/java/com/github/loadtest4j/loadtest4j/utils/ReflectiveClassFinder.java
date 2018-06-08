package com.github.loadtest4j.loadtest4j.utils;

import java.util.Optional;

public class ReflectiveClassFinder<T> implements ClassFinder<T> {

    private final Class<T> klass;

    public ReflectiveClassFinder(Class<T> klass) {
        this.klass = klass;
    }

    public Optional<T> find(String className) {
        try {
            final Class<?> factoryClass = Class.forName(className);
            final T factory = klass.cast(factoryClass.newInstance());
            return Optional.of(factory);
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            return Optional.empty();
        }

    }
}
