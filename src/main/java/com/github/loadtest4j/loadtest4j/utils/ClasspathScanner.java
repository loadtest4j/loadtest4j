package com.github.loadtest4j.loadtest4j.utils;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ClasspathScanner<T> {

    private final Class<T> implementedInterface;

    public ClasspathScanner(Class<T> implementedInterface) {
        this.implementedInterface = implementedInterface;
    }

    public Collection<T> scan() {
        final List<Class<? extends T>> foundClasses = new ArrayList<>();
        new FastClasspathScanner()
                .matchClassesImplementing(implementedInterface, foundClasses::add)
                .scan();

        return foundClasses.stream()
                .map(c -> {
                    try {
                        return c.newInstance();
                    } catch (InstantiationException | IllegalAccessException e) {
                        throw new ClasspathScannerException(e);
                    }
                })
                .collect(Collectors.toList());
    }
}
