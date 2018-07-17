package com.github.loadtest4j.loadtest4j.utils;

import java.util.Collection;

public interface ClassFinder {
    <T> Collection<Class<T>> findImplementationsOf(Class<T> anInterface);
}
