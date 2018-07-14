package com.github.loadtest4j.loadtest4j.utils;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.scanner.ScanResult;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FastClassFinder implements ClassFinder {

    @Override
    public <T> Collection<T> findImplementationsOf(Class<T> anInterface) {
        final ScanResult scanResult = new FastClasspathScanner().scan();

        final List<String> implementorNames = scanResult.getNamesOfClassesImplementing(anInterface.getName());

        final List<Class<?>> implementorRefs = scanResult.classNamesToClassRefs(implementorNames);

        return implementorRefs.stream()
                .flatMap(ref -> {
                    try {
                        return Stream.of((T) ref.newInstance());
                    } catch (InstantiationException | IllegalAccessException e) {
                        return Stream.empty();
                    }
                })
                .collect(Collectors.toList());
    }
}
