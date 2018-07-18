package org.loadtest4j.utils;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.scanner.ScanResult;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FastClassFinder implements ClassFinder {

    @Override
    public <T> Collection<Class<T>> findImplementationsOf(Class<T> anInterface) {
        final ScanResult scanResult = new FastClasspathScanner().scan();

        final List<String> implementorNames = scanResult.getNamesOfClassesImplementing(anInterface.getName());

        final List<Class<?>> implementorRefs = scanResult.classNamesToClassRefs(implementorNames);

        return implementorRefs.stream()
                .map(ref -> (Class<T>) ref)
                .collect(Collectors.toList());
    }
}
