package com.github.loadtest4j.loadtest4j.utils;

import com.github.loadtest4j.loadtest4j.junit.UnitTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Category(UnitTest.class)
public class ReflectiveClassFinderTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ClassFinder<A> sut() {
        return new ReflectiveClassFinder<>(A.class);
    }

    @Test
    public void testFind() {
        final ClassFinder<A> finder = sut();

        final Optional<A> found = finder.find(AImpl.class.getName());

        assertTrue(found.isPresent());
        assertTrue(found.get() instanceof AImpl);
    }

    @Test
    public void testFindWithWrongClass() {
        final ClassFinder<A> finder = sut();

        thrown.expect(ClassCastException.class);

        finder.find("java.lang.String");
    }

    @Test
    public void testFindWithNonExistentClass() {
        final ClassFinder<A> finder = sut();

        final Optional<A> found = finder.find("foo");

        assertEquals(Optional.empty(), found);
    }

    public interface A {
        void foo();
    }

    public static class AImpl implements A {

        @Override
        public void foo() {
            // No-op
        }
    }
}
