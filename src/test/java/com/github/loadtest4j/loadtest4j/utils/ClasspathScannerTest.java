package com.github.loadtest4j.loadtest4j.utils;

import com.github.loadtest4j.loadtest4j.junit.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

@Category(UnitTest.class)
public class ClasspathScannerTest {

    @Test
    public void testScan() {
        // Given
        final ClasspathScanner<OneMatch> scanner = new ClasspathScanner<>(OneMatch.class);

        // When
        final Collection<OneMatch> implementations = scanner.scan();

        // Then
        assertEquals(1, implementations.size());
    }

    @Test
    public void testScanWithNoMatches() {
        // Given
        final ClasspathScanner<NoMatch> scanner = new ClasspathScanner<>(NoMatch.class);

        // When
        final Collection<NoMatch> implementations = scanner.scan();

        // Then
        assertEquals(0, implementations.size());
    }

    @Test(expected = ClasspathScannerException.class)
    public void testScanWithClassesWeCannotInstantiate() {
        // Given
        final ClasspathScanner<CannotInstantiate> scanner = new ClasspathScanner<>(CannotInstantiate.class);

        // When
        scanner.scan();
    }

    interface OneMatch {}
    static class OneMatchImpl implements OneMatch {}

    interface NoMatch {}

    interface CannotInstantiate {}
    static class CannotInstantiateImpl implements CannotInstantiate {
        private CannotInstantiateImpl() {
        }
    }
}
